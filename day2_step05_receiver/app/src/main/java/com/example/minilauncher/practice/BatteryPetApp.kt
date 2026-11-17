package com.example.minilauncher.practice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 2일차 오후: 시스템 이벤트로 움직이는 앱 "배터리 펫".
// Run 한 뒤 에뮬레이터의 앱 목록에서 "배터리 펫" 아이콘을 누릅니다.
// 에뮬레이터 옆 ... > Battery 에서 먼저 Charger connection 을 None 으로 바꿉니다 (꽂혀 있으면 계속 밥 먹는 중).
// 그다음 Charge level 과 Charger connection 을 바꾸면 펫의 표정이 바뀝니다.
// 읽는 순서: 1. 상태  2. 방송을 듣는 곳  3. 화면

// ---------------------------------------------------------------- 1. 상태
data class PetState(
    val percent: Int = 0,                       // 배터리 몇 %
    val charging: Boolean = false,              // 충전기를 꽂았는지
    val history: List<String> = emptyList(),    // 있었던 일 (새 것이 위)
)

// 배터리에 따라 펫의 기분이 바뀝니다
fun petFace(state: PetState): String = when {
    state.charging -> "(^o^)"
    state.percent <= 20 -> "(T_T)"
    state.percent <= 80 -> "(^_^)"
    else -> "(-_-) zZ"
}

fun petMood(state: PetState): String = when {
    state.charging -> "냠냠, 밥 먹는 중"
    state.percent <= 20 -> "배고파요"
    state.percent <= 80 -> "기분 좋아요"
    else -> "배불러서 졸려요"
}

fun now(): String = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(Date())

// ---------------------------------------------------------------- 2. 방송을 듣는 곳
// 이 화면(Activity)이 살아 있는 동안 방송을 듣고, 닫히면 그만 듣습니다 (등록과 해제는 항상 짝)
@Composable
fun rememberPet(): PetState {
    val context = LocalContext.current
    var state by remember { mutableStateOf(PetState()) }
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context, intent: Intent) {
                when (intent.action) {
                    // 배터리 값이 바뀔 때마다 옵니다. 등록하자마자 지금 값이 한 번 옵니다
                    Intent.ACTION_BATTERY_CHANGED -> {
                        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
                        val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) != 0
                        state = state.copy(percent = level * 100 / scale, charging = plugged)
                    }
                    // 충전기를 꽂는 순간, 뽑는 순간에 한 번씩 옵니다
                    Intent.ACTION_POWER_CONNECTED ->
                        state = state.copy(history = listOf("${now()} 충전기를 꽂았어요") + state.history)
                    Intent.ACTION_POWER_DISCONNECTED ->
                        state = state.copy(history = listOf("${now()} 충전기를 뽑았어요") + state.history)
                }
            }
        }
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
        onDispose { context.unregisterReceiver(receiver) }
        // 바꿔 보면: addAction(Intent.ACTION_POWER_CONNECTED) 줄을 지우면 충전기를 꽂아도
        // "충전기를 꽂았어요" 기록이 안 생깁니다. 필터에 적은 것만 오기 때문입니다
    }
    return state
}

class BatteryPetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { BatteryPetLayout(rememberPet()) } }
    }
}

// ---------------------------------------------------------------- 3. 화면
// 상태를 받아서 그리기만 합니다
@Composable
fun BatteryPetLayout(state: PetState) {
    val background = when {
        state.charging -> Color(0xFFDFF5E1)        // 충전 중: 연두
        state.percent <= 20 -> Color(0xFFFFE0E0)   // 배고픔: 분홍
        else -> Color(0xFFF1F3F4)
    }
    Column(
        modifier = Modifier.fillMaxSize().background(background).safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("배터리 펫", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(48.dp))
        Text(petFace(state), fontSize = 64.sp)
        Spacer(Modifier.height(16.dp))
        Text(petMood(state), style = MaterialTheme.typography.headlineSmall)
        Text("배터리 ${state.percent}%", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(32.dp))
        Text("있었던 일", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
        if (state.history.isEmpty()) {
            Text("충전기를 꽂거나 뽑아 보세요", modifier = Modifier.fillMaxWidth())
        }
        state.history.take(8).forEach { Text(it, modifier = Modifier.fillMaxWidth()) }
    }
}
