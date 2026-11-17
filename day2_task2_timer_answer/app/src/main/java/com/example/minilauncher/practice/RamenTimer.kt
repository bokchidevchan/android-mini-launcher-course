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

// 2일차 오후 과제 2 "컵라면 타이머" 앱의 완성본입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "컵라면 타이머" 아이콘을 누릅니다.
// 시작을 누르고 홈으로 나가도 알림에서 남은 시간이 계속 줄어듭니다. 화면이 아니라 Service 가 세고 있기 때문입니다.
// 화면과 알림은 같은 폴더의 RamenTimerScreen.kt 에 있습니다. 번호 [1] [2] [3] 은 뼈대 프로젝트의 같은 번호 자리입니다.

class RamenTimerService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null      // 화면이 직접 붙지 않으므로 null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val total = intent?.getIntExtra(RamenTimer.EXTRA_SECONDS, 10) ?: 10
        // [1] 알림과 함께 Foreground 로 올리기
        createRamenChannel(this)
        // "화면이 없어도 일하겠습니다"라고 알림과 함께 알립니다. Service 가 시작되고
        // 정해진 시간(요즘 버전은 30초쯤) 안에 startForeground 를 불러야 합니다
        val first = ramenNotification(this, total, total)
        val type = ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        ServiceCompat.startForeground(this, RamenTimer.NOTIFICATION, first, type)
        // [2] 코루틴에서 1초마다 하나씩 줄이고, 다 세면 끝내기
        job?.cancel()                                          // 이미 세고 있었다면 처음부터 다시
        job = scope.launch {
            val manager = getSystemService(NotificationManager::class.java)
            for (left in total downTo 1) {
                RamenTimer.remaining.value = left
                val notification = ramenNotification(this@RamenTimerService, left, total)
                manager.notify(RamenTimer.NOTIFICATION, notification)
                delay(1000)
            }
            // 다 세면 알림을 바꾸고 스스로 멈춥니다
            RamenTimer.remaining.value = 0
            // 알림은 남겨 두고 Foreground 상태만 풉니다. 그다음 "다 익었어요"로 바꾸고
            // 스스로 멈춥니다
            ServiceCompat.stopForeground(this@RamenTimerService, ServiceCompat.STOP_FOREGROUND_DETACH)
            val done = ramenNotification(this@RamenTimerService, 0, total)
            manager.notify(RamenTimer.NOTIFICATION, done)
            stopSelf(startId)      // 그사이 새로 시작한 타이머가 있으면 멈추지 않습니다
        }
        return START_NOT_STICKY
        // 바꿔 보기: delay(1000) 을 delay(200) 으로 바꾸면 라면이 얼마나 빨리 익을까요?
    }

    override fun onDestroy() {
        // [3] Service 가 끝날 때 코루틴도 멈추기
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
