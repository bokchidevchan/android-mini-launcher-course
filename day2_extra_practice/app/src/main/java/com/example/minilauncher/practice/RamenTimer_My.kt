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

// 직접 만들어 보는 파일입니다. 완성본은 같은 폴더의 RamenTimer.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "컵라면 타이머 연습" 아이콘을 누릅니다.
// 화면(RamenTimerScreen), 알림(createRamenChannel, ramenNotification), 남은 시간(RamenTimer)은 RamenTimer.kt 의 것을 그대로 씁니다.
// 아래 onStartCommand 안만 채우면 완성본과 똑같이 움직입니다. 필요한 import 는 위에 다 있습니다.
// 채우기 전에 시작을 누르면 알림은 뜨지만 시간이 줄지 않습니다. 남은 알림은 다시 Run 하면 사라집니다.

class MyRamenTimerService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val total = intent?.getIntExtra(RamenTimer.EXTRA_SECONDS, 10) ?: 10
        // 알림 채널을 만들고 Foreground 로 올립니다. 여기까지는 써 두었습니다
        createRamenChannel(this)
        ServiceCompat.startForeground(
            this, RamenTimer.NOTIFICATION, ramenNotification(this, total, total), ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
        )
        // 1. 코루틴에서 1초마다 하나씩 줄입니다
        //      job?.cancel()
        //      job = scope.launch {
        //          val manager = getSystemService(NotificationManager::class.java)
        //          for (left in total downTo 1) {
        //              RamenTimer.remaining.value = left
        //              manager.notify(RamenTimer.NOTIFICATION, ramenNotification(this@MyRamenTimerService, left, total))
        //              delay(1000)
        //          }
        // 2. 다 세면 남은 시간을 0 으로 바꾸고, Foreground 를 풀고, "다 익었어요" 알림으로 바꾼 뒤 스스로 멈춥니다
        //          RamenTimer.remaining.value = 0
        //          ServiceCompat.stopForeground(this@MyRamenTimerService, ServiceCompat.STOP_FOREGROUND_DETACH)
        //          manager.notify(RamenTimer.NOTIFICATION, ramenNotification(this@MyRamenTimerService, 0, total))
        //          stopSelf(startId)
        //      }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}

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
