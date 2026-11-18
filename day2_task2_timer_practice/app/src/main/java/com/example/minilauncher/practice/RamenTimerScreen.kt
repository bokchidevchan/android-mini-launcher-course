package com.example.minilauncher.practice

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.minilauncher.ui.theme.LauncherBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

// 2일차 오후 과제 "컵라면 타이머" 앱에서 고치지 않고 그대로 쓰는 부분입니다.
// 남은 시간(RamenTimer), 알림 만들기, 과제에서 부르는 함수 세 개, 화면이 들어 있습니다.

// Service 와 화면이 같이 보는 값입니다. null 은 쉬는 중, 0 은 다 익음, 나머지는 남은 초
object RamenTimer {
    val remaining = MutableStateFlow<Int?>(null)
    const val EXTRA_SECONDS = "seconds"
    const val CHANNEL = "ramen"
    const val NOTIFICATION = 200
}

fun createRamenChannel(context: Context) {
    val channel = NotificationChannel(RamenTimer.CHANNEL, "컵라면 타이머", NotificationManager.IMPORTANCE_DEFAULT)
    context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
}

// left 는 남은 초, total 은 처음에 맞춘 초. left 가 0 이면 "다 익었어요" 알림
fun ramenNotification(context: Context, left: Int, total: Int): Notification {
    val builder = NotificationCompat.Builder(context, RamenTimer.CHANNEL)
        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
        .setContentTitle("컵라면 타이머")
        .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
    if (left > 0) {
        builder.setContentText("${left}초 남았어요").setProgress(total, total - left, false).setOngoing(true).setOnlyAlertOnce(true)
    } else {
        builder.setContentText("다 익었어요! 뚜껑을 여세요")
    }
    return builder.build()
}

// ---------------------------------------------------------------- 과제에서 부르는 함수 세 개

// [1] 에서 부릅니다. 알림 채널을 만들고, 첫 알림과 함께 이 Service 를 Foreground 로 올립니다.
// Foreground Service 는 알림을 꼭 띄워야 해서, 시작하자마자 이 함수를 부릅니다
fun Service.startForegroundWithNotification(total: Int) {
    createRamenChannel(this)
    val first = ramenNotification(this, total, total)
    ServiceCompat.startForeground(this, RamenTimer.NOTIFICATION, first, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
}

// [2] 에서 부릅니다. 남은 시간을 화면(RamenTimer.remaining)과 알림에 보여 줍니다
fun Service.showLeft(left: Int, total: Int) {
    RamenTimer.remaining.value = left
    getSystemService(NotificationManager::class.java).notify(RamenTimer.NOTIFICATION, ramenNotification(this, left, total))
}

// [2] 에서 부릅니다. "다 익었어요" 알림은 남겨 두고 Service 를 끝냅니다
fun Service.finishTimer(total: Int, startId: Int) {
    RamenTimer.remaining.value = 0
    ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_DETACH)   // 알림은 남기고 Foreground 만 풂
    getSystemService(NotificationManager::class.java).notify(RamenTimer.NOTIFICATION, ramenNotification(this, 0, total))
    stopSelf(startId)
}

// ---------------------------------------------------------------- 화면

// service 는 시작할 Service 의 클래스입니다
@Composable
fun RamenTimerScreen(remaining: Int?, service: Class<out Service>) {
    val context = LocalContext.current
    // 알림 권한을 묻는 창. Android 13 부터는 사용자가 허락해야 알림이 보입니다
    val askNotification = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
    // 버튼을 누른 횟수. 누른 뒤 숫자가 안 바뀌면 아직 안 채운 칸을 화면에 알려 줍니다
    var presses by remember { mutableIntStateOf(0) }
    var hint by remember { mutableStateOf<String?>(null) }
    fun start(seconds: Int) {
        if (Build.VERSION.SDK_INT >= 33) askNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
        val intent = Intent(context, service).putExtra(RamenTimer.EXTRA_SECONDS, seconds)
        context.startForegroundService(intent)
        hint = null
        presses++
    }
    // 누르고 2.5초가 지나도 남은 시간이 안 들어오면, 알림이 떠 있는지로 [1] 과 [2] 중 무엇이 비었는지 알려 줍니다
    LaunchedEffect(presses) {
        if (presses == 0) return@LaunchedEffect
        delay(2500)
        if (RamenTimer.remaining.value != null) return@LaunchedEffect
        val notified = context.getSystemService(NotificationManager::class.java)
            .activeNotifications.any { it.id == RamenTimer.NOTIFICATION }
        hint = if (!notified) {
            "[1] 이 비어 있어요. 알림 없이 시작해서 10초쯤 뒤 앱이 꺼집니다.\n" +
                "RamenTimer.kt 의 [1] 에 startForegroundWithNotification(total) 을 적어 주세요"
        } else {
            "[1] 은 됐어요. 알림은 떴는데 숫자가 그대로예요.\n" +
                "RamenTimer.kt 의 [2] 에서 1초마다 showLeft 를 부르고, 다 세면 finishTimer 를 불러 주세요"
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("컵라면 타이머", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        val text = when (remaining) {
            null -> "물을 붓고 시작"
            0 -> "다 익었어요!"
            else -> "${remaining}초"
        }
        Text(text, fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            val idle = remaining == null || remaining == 0     // 세는 중에는 버튼을 막습니다
            Button(onClick = { start(10) }, enabled = idle) { Text("10초 (맛보기)") }
            FilledTonalButton(onClick = { start(180) }, enabled = idle) { Text("3분") }
        }
        Spacer(Modifier.height(16.dp))
        Text("시작한 뒤 홈으로 나가 화면 위쪽을 끌어내려 보세요")
        hint?.let {
            Spacer(Modifier.height(24.dp))
            Text(
                it,
                color = Color(0xFFB3261E),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.background(Color(0xFFFCEEEE)).padding(12.dp),
            )
        }
    }
}
