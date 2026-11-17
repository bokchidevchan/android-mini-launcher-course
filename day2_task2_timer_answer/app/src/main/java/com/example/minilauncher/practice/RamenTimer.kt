package com.example.minilauncher.practice

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 2일차 오후 과제 "컵라면 타이머" 의 완성본입니다.
// 버튼을 누르면 이 Service 가 시작되고 1초마다 남은 시간을 줄입니다. 홈으로 나가도 계속 셉니다.
// [1] [2] [3] 이 뼈대 프로젝트에서 채우는 자리입니다.
// startForegroundWithNotification, showLeft, finishTimer 는 RamenTimerScreen.kt 에 만들어져 있습니다.

class RamenTimerService : Service() {
    // 이 Service 에서 시작하는 코루틴의 묶음입니다. [3] 에서 한 번에 멈춥니다
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onBind(intent: Intent?): IBinder? = null

    // 버튼을 누르면 불립니다. total 은 셀 시간(초)입니다
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val total = intent?.getIntExtra(RamenTimer.EXTRA_SECONDS, 10) ?: 10

        // [1] 알림을 띄우고 Foreground Service 로 올립니다
        startForegroundWithNotification(total)

        // [2] 코루틴에서 1초마다 하나씩 세고, 다 세면 끝냅니다
        scope.launch {
            for (left in total downTo 1) {
                showLeft(left, total)       // 화면과 알림에 남은 시간
                delay(1000)                 // 1초 쉼
            }
            finishTimer(total, startId)     // "다 익었어요" 알림을 남기고 Service 를 끝냄
        }

        return START_NOT_STICKY
    }

    // [3] Service 가 끝날 때 세던 코루틴도 멈춥니다
    override fun onDestroy() {
        scope.cancel()
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
