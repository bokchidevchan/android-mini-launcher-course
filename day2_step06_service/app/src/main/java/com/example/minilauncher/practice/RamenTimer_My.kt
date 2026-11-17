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

// 2일차 오후 직접 만들기: 컵라면 타이머. 완성본은 같은 폴더의 RamenTimer.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "컵라면 타이머 직접" 아이콘을 누릅니다.
// 화면(RamenTimerScreen), 알림(createRamenChannel, ramenNotification), 남은 시간(RamenTimer)은 RamenTimer.kt 의 것을 그대로 씁니다.
// [1] 부터 [4] 까지 차례로 채웁니다. 하나 채울 때마다 Run 해서 "확인"에 적힌 대로 되는지 봅니다.
// 채울 코드는 번호 아래 주석에 있습니다. 주석 기호(//)를 떼고 쓰면 됩니다. 필요한 import 는 위에 다 있습니다.
// 단, "옮겨 씁니다"라고 적힌 번호는 주석을 그 자리에서 풀지 않고, 적힌 자리로 옮겨서 씁니다.
// 위의 import 가운데 회색으로 보이는 것은 아직 안 써서 그런 것이니 그대로 둡니다.
// 주의: [1] 을 채우기 전에 "10초 (맛보기)"를 누르면 아무 일도 없다가 30초쯤 뒤 앱이 꺼집니다
//       (Logcat: ForegroundServiceDidNotStartInTimeException). Foreground Service 는 시작한 뒤 정해진 시간 안에
//       알림을 띄워야 한다는 규칙을 안 지켰기 때문입니다. [1] 을 채운 뒤에 누릅니다.

class MyRamenTimerService : Service() {
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
        //     확인: "10초 (맛보기)"를 누르고 화면 위쪽을 끌어내리면 "10초 남았어요" 알림이 있습니다. 시간은 아직 안 줄어듭니다.
        //     알림 권한 창이 뜨면 허용을 누릅니다. 허용한 뒤 알림이 안 보이면 버튼을 한 번 더 누릅니다. 남은 알림은 다시 Run 하면 사라집니다

        // [2] 코루틴에서 1초마다 하나씩 줄입니다. 남은 시간을 바꾸면 화면이, notify 를 부르면 알림이 바뀝니다
        //
        //     job?.cancel()
        //     job = scope.launch {
        //         val manager = getSystemService(NotificationManager::class.java)
        //         for (left in total downTo 1) {
        //             RamenTimer.remaining.value = left
        //             val notification = ramenNotification(this@MyRamenTimerService, left, total)
        //             manager.notify(RamenTimer.NOTIFICATION, notification)
        //             delay(1000)
        //         }
        //     }
        //
        //     확인: 화면의 숫자가 1초마다 줄어듭니다. 홈으로 나가도 알림의 숫자가 계속 줄어듭니다.
        //     다 세어도 알림이 "1초 남았어요"에서 멈춰 있습니다

        // [3] 다 세면 끝냅니다. 아래 주석은 이 자리에서 풀지 않습니다.
        //     다섯 줄을 위 [2] 의 for 가 끝나는 } 바로 아래, launch 가 끝나는 } 앞으로 옮겨 씁니다
        //
        //         RamenTimer.remaining.value = 0
        //         ServiceCompat.stopForeground(this@MyRamenTimerService, ServiceCompat.STOP_FOREGROUND_DETACH)
        //         val done = ramenNotification(this@MyRamenTimerService, 0, total)
        //         manager.notify(RamenTimer.NOTIFICATION, done)
        //         stopSelf(startId)
        //
        //     확인: 다 세면 화면과 알림이 "다 익었어요"로 바뀌고 진행 막대가 사라집니다. 알림을 밀어 지우면 다시 생기지 않습니다

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        // [4] Service 가 끝날 때 세던 코루틴도 같이 멈춥니다. 아래 한 줄을 씁니다
        //
        //     scope.cancel()
        //
        //     확인: 눈에 보이는 차이는 없습니다. 이 줄이 없으면 Service 가 사라진 뒤에도 코루틴이 남을 수 있습니다
        super.onDestroy()
    }
}
// 더 해 보기 1: delay(1000) 을 delay(200) 으로 바꾸면 라면이 얼마나 빨리 익을까요?
// 더 해 보기 2: [2] 의 job?.cancel() 을 지우고 "10초"를 빠르게 두 번 누르면 숫자가 어떻게 움직일까요?

class MyRamenTimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val remaining by RamenTimer.remaining.collectAsStateWithLifecycle()
                RamenTimerScreen(remaining, MyRamenTimerService::class.java)
            }
        }
    }
}
