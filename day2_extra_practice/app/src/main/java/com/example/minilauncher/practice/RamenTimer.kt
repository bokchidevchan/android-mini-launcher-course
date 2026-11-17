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
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// 2일차 오후 직접 만들기: 화면을 나가도 계속 도는 앱 "컵라면 타이머".
// Run 한 뒤 에뮬레이터의 앱 목록에서 "컵라면 타이머" 아이콘을 누릅니다.
// 시작을 누르고 홈으로 나가도 알림에서 남은 시간이 계속 줄어듭니다. 화면이 아니라 Service 가 세고 있기 때문입니다.
// 읽는 순서: 1. 남은 시간  2. 알림  3. Service  4. 화면
// 직접 만드는 파일은 같은 폴더의 RamenTimer_My.kt 입니다. 거기의 [1] [2] [3] [4] 가 이 파일의 같은 번호 자리입니다.

// ---------------------------------------------------------------- 1. 남은 시간
// Service 와 화면이 같이 보는 값입니다. null 은 쉬는 중, 0 은 다 익음, 나머지는 남은 초
object RamenTimer {
    val remaining = MutableStateFlow<Int?>(null)
    const val EXTRA_SECONDS = "seconds"
    const val CHANNEL = "ramen"
    const val NOTIFICATION = 200
}

// ---------------------------------------------------------------- 2. 알림
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

// ---------------------------------------------------------------- 3. Service
class RamenTimerService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null      // 화면이 직접 붙지 않으므로 null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val total = intent?.getIntExtra(RamenTimer.EXTRA_SECONDS, 10) ?: 10
        // [1] 알림과 함께 Foreground 로 올리기
        createRamenChannel(this)
        // "화면이 없어도 일하겠습니다"라고 알림과 함께 알립니다. Service 가 시작되고 정해진 시간(요즘 버전은 30초쯤) 안에 startForeground 를 불러야 합니다
        ServiceCompat.startForeground(
            this, RamenTimer.NOTIFICATION, ramenNotification(this, total, total), ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
        )
        // [2] 코루틴에서 1초마다 하나씩 줄이기
        job?.cancel()                                          // 이미 세고 있었다면 처음부터 다시
        job = scope.launch {
            val manager = getSystemService(NotificationManager::class.java)
            for (left in total downTo 1) {
                RamenTimer.remaining.value = left
                manager.notify(RamenTimer.NOTIFICATION, ramenNotification(this@RamenTimerService, left, total))
                delay(1000)
            }
            // [3] 다 세면 알림을 바꾸고 스스로 멈추기
            RamenTimer.remaining.value = 0
            // 알림은 남겨 두고 Foreground 상태만 풉니다. 그다음 "다 익었어요"로 바꾸고 스스로 멈춥니다
            ServiceCompat.stopForeground(this@RamenTimerService, ServiceCompat.STOP_FOREGROUND_DETACH)
            manager.notify(RamenTimer.NOTIFICATION, ramenNotification(this@RamenTimerService, 0, total))
            stopSelf(startId)      // 그사이 새로 시작한 타이머가 있으면 멈추지 않습니다
        }
        return START_NOT_STICKY
        // 바꿔 보기: delay(1000) 을 delay(200) 으로 바꾸면 라면이 얼마나 빨리 익을까요?
    }

    override fun onDestroy() {
        // [4] Service 가 끝날 때 코루틴도 멈추기
        scope.cancel()                                         // Service 가 끝나면 세던 코루틴도 멈춥니다
        super.onDestroy()
    }
}

class RamenTimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val remaining by RamenTimer.remaining.collectAsStateWithLifecycle()
                RamenTimerScreen(remaining, RamenTimerService::class.java)
            }
        }
    }
}

// ---------------------------------------------------------------- 4. 화면
// service 는 시작할 Service 의 클래스입니다. 연습 파일은 여기에 자기 Service 를 넘깁니다
@Composable
fun RamenTimerScreen(remaining: Int?, service: Class<out Service>) {
    val context = LocalContext.current
    // 알림 권한을 묻는 창. Android 13 부터는 사용자가 허락해야 알림이 보입니다
    val askNotification = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
    fun start(seconds: Int) {
        if (Build.VERSION.SDK_INT >= 33) askNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
        val intent = Intent(context, service).putExtra(RamenTimer.EXTRA_SECONDS, seconds)
        context.startForegroundService(intent)
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
            Button(onClick = { start(10) }) { Text("10초 (맛보기)") }
            FilledTonalButton(onClick = { start(180) }) { Text("3분") }
        }
        Spacer(Modifier.height(16.dp))
        Text("시작한 뒤 홈으로 나가 화면 위쪽을 끌어내려 보세요")
    }
}
