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

// 2일차 오후 과제 "컵라면 타이머" 의 뼈대입니다.
// [1] [2] [3] 자리를 채웁니다. 무엇을 쓰는지는 README.md 에 있습니다. 완성본은 day2_task2_timer_answer 폴더입니다.
// startForegroundWithNotification, showLeft, finishTimer 는 RamenTimerScreen.kt 에 만들어져 있습니다.
// 위의 import 가운데 회색인 것은 채우고 나면 쓰입니다.
// 주의: [1] 을 채우기 전에는 버튼을 누르지 않습니다. 알림 없이 Service 를 시작하면 조금 뒤에 앱이 꺼집니다.

class RamenTimerService : Service() {
    // 이 Service 에서 시작하는 코루틴의 묶음입니다. [3] 에서 한 번에 멈춥니다
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onBind(intent: Intent?): IBinder? = null

    // 버튼을 누르면 불립니다. total 은 셀 시간(초), startId 는 이번 시작 요청의 번호(끝낼 때 씀)입니다
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val total = intent?.getIntExtra(RamenTimer.EXTRA_SECONDS, 10) ?: 10

        // [1] 여기를 채웁니다: 알림을 띄우고 Foreground Service 로 올리기

        // [2] 여기를 채웁니다: 코루틴에서 1초마다 하나씩 세고, 다 세면 끝내기

        return START_NOT_STICKY
    }

    // [3] Service 가 끝날 때 세던 코루틴도 멈춥니다
    override fun onDestroy() {
        // [3] 여기를 채웁니다
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
