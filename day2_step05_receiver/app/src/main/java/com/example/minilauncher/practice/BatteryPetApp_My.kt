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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 직접 만들어 보는 파일입니다. 완성본은 같은 폴더의 BatteryPetApp.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "배터리 펫 연습" 아이콘을 누릅니다.
// 화면(BatteryPetLayout)과 상태(PetState), now() 는 BatteryPetApp.kt 의 것을 그대로 씁니다.
// 아래 DisposableEffect 안만 채우면 완성본과 똑같이 움직입니다. 필요한 import 는 위에 다 있습니다.

@Composable
fun rememberMyPet(): PetState {
    val context = LocalContext.current
    var state by remember { mutableStateOf(PetState()) }
    DisposableEffect(Unit) {
        // 1. 방송을 받는 receiver 를 만듭니다
        //      val receiver = object : BroadcastReceiver() {
        //          override fun onReceive(c: Context, intent: Intent) { ... }
        //      }
        //    onReceive 안에서 intent.action 을 when 으로 나눕니다
        //      Intent.ACTION_BATTERY_CHANGED: level, scale, plugged 를 꺼내 state = state.copy(percent = ..., charging = ...)
        //        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        //        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
        //        val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) != 0     // 0 이 아니면 꽂혀 있음
        //        퍼센트는 level * 100 / scale
        //      Intent.ACTION_POWER_CONNECTED: state = state.copy(history = listOf("${now()} 충전기를 꽂았어요") + state.history)
        //      Intent.ACTION_POWER_DISCONNECTED: 위와 같고 글자만 "충전기를 뽑았어요"
        // 2. 듣고 싶은 방송 세 가지를 IntentFilter 에 addAction 으로 담습니다
        // 3. ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED) 로 등록합니다
        // 4. 아래 onDispose 안에서 context.unregisterReceiver(receiver) 로 해제합니다
        onDispose {
            // 여기에 해제를 씁니다
        }
    }
    return state
}

class MyBatteryPetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { BatteryPetLayout(rememberMyPet()) } }
    }
}
