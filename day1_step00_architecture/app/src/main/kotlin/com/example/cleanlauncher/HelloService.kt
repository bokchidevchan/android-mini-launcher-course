package com.example.cleanlauncher

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat

// Foreground Service 맛보기. 앱을 켜면 알림을 하나 띄우고 20초 뒤에 스스로 끝납니다.
// 화면이 없는 부품이라서 AndroidManifest.xml 에 적어 두어야 시스템이 찾을 수 있습니다.
// 자세한 내용은 2일차 2-3 에서 다룹니다
class HelloService : Service() {

    // 다른 곳에서 연결해 쓰는 Service 가 아니므로 null 을 돌려줍니다
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "HelloService 시작")

        // 1. 알림 채널 만들기. 알림은 채널에 속해야 뜹니다 (Android 8 부터)
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(
            NotificationChannel(CHANNEL_ID, "실행 알림", NotificationManager.IMPORTANCE_DEFAULT)
        )

        // 2. 알림 만들기
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .setContentTitle("Clean Launcher")
            .setContentText("Service 실행 중 (20초 뒤 끝남)")
            .setOngoing(true)
            // Android 12 부터 시스템이 이 알림을 최대 10초 늦게 보여 줄 수 있습니다. 바로 보이게 합니다
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()

        // 3. "알림을 띄우고 일하겠다"고 시스템에 알립니다.
        //    종류(dataSync)는 Manifest 의 foregroundServiceType 과 같아야 합니다
        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        } else {
            0
        }
        ServiceCompat.startForeground(this, NOTIFICATION_ID, notification, type)

        // 4. 20초 뒤에 스스로 끝냅니다
        Handler(Looper.getMainLooper()).postDelayed({ stopSelf() }, 20_000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "HelloService 끝")
        super.onDestroy()
    }

    private companion object {
        const val TAG = "CleanLauncher"
        const val CHANNEL_ID = "hello"
        const val NOTIFICATION_ID = 1
    }
}
