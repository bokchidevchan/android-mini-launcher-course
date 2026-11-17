package com.example.minilauncher.practice

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.app.ServiceCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 2일차 오후 과제: 컵라면 타이머 만들기 (뼈대).
// Run 하면 컵라면 타이머 화면이 바로 뜹니다.
// 화면과 알림은 같은 폴더의 RamenTimerScreen.kt 에 만들어져 있습니다. 이 파일의 [1] [2] [3] 만 채웁니다.
// 하나 채울 때마다 Run 해서 "확인"에 적힌 대로 되는지 봅니다.
// 채우는 법: 번호 아래에 정답 코드가 주석(// 로 시작하는 줄)으로 적혀 있습니다. 그 줄들 맨 앞의 // 를 지우면
//           실행되는 코드가 됩니다. Android Studio 에서 코드 줄만 고른 뒤("확인" 줄은 빼고) Ctrl + / (Mac 은 Cmd + /) 를
//           누르면 한 번에 지워집니다.
// 완성본은 day2_task2_timer_answer 폴더에 있습니다. 먼저 만들어 보고 엽니다.
// 위의 import 가운데 회색으로 보이는 것은 아직 안 써서 그런 것이니 그대로 둡니다.
// 주의: [1] 을 채우기 전에는 "10초 (맛보기)" 버튼을 누르지 않습니다. 알림 없이 Service 를 시작하면
//       조금 뒤에 시스템이 앱을 끕니다.

class RamenTimerService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    // 화면이 startForegroundService 로 이 Service 를 시작하면 여기가 불립니다
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val total = intent?.getIntExtra(RamenTimer.EXTRA_SECONDS, 10) ?: 10

        // [1] 알림 채널을 만들고, 알림과 함께 Foreground 로 올립니다
        //
        //     createRamenChannel(this)
        //     val first = ramenNotification(this, total, total)
        //     val type = ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        //     ServiceCompat.startForeground(this, RamenTimer.NOTIFICATION, first, type)
        //
        //     확인: "10초 (맛보기)"를 누르고 화면 위쪽을 끌어내리면 "10초 남았어요" 알림이 있습니다.
        //     시간은 아직 안 줄어듭니다. 알림 권한 창이 뜨면 허용을 누릅니다

        // [2] 코루틴에서 1초마다 하나씩 줄이고, 다 세면 알림을 바꾸고 스스로 멈춥니다
        //
        //     job?.cancel()
        //     job = scope.launch {
        //         val manager = getSystemService(NotificationManager::class.java)
        //         for (left in total downTo 1) {
        //             RamenTimer.remaining.value = left
        //             val notification = ramenNotification(this@RamenTimerService, left, total)
        //             manager.notify(RamenTimer.NOTIFICATION, notification)
        //             delay(1000)
        //         }
        //         RamenTimer.remaining.value = 0
        //         ServiceCompat.stopForeground(this@RamenTimerService, ServiceCompat.STOP_FOREGROUND_DETACH)
        //         val done = ramenNotification(this@RamenTimerService, 0, total)
        //         manager.notify(RamenTimer.NOTIFICATION, done)
        //         stopSelf(startId)
        //     }
        //
        //     확인: 화면의 숫자가 1초마다 줄어듭니다. 홈으로 나가도 알림의 숫자가 계속 줄고,
        //     다 세면 "다 익었어요"로 바뀝니다

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        // [3] Service 가 끝날 때 세던 코루틴도 같이 멈춥니다. 아래 한 줄의 // 를 지웁니다
        //
        //     scope.cancel()
        //
        //     확인: 눈에 보이는 차이는 없습니다. 이 줄이 없으면 Service 가 사라진 뒤에도 코루틴이 남을 수 있습니다
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
