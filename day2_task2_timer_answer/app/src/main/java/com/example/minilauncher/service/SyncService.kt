package com.example.minilauncher.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "SyncService"
private const val CHANNEL_ID = "sync"
private const val NOTIFICATION_ID = 1

// 진행률: null 이면 쉬는 중, 0~99 는 진행 중, 100 이면 완료
// "주행 로그 동기화"는 실제 통신 없이 0.4초마다 2%씩 올라가는 가짜 작업입니다 (약 20초)
class SyncService : Service() {

    // Bound Service: 같은 앱 안에서 bindService 로 붙으면 이 Binder 를 통해 Service 객체를 직접 씁니다
    inner class LocalBinder : Binder() {
        val service: SyncService get() = this@SyncService
    }

    private val binder = LocalBinder()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var job: Job? = null

    private val _progress = MutableStateFlow<Int?>(null)
    val progress: StateFlow<Int?> = _progress.asStateFlow()

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    // startForegroundService 로 시작하면 불립니다. 5초 안에 startForeground 를 불러야 합니다
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        createChannel()
        ServiceCompat.startForeground(
            this, NOTIFICATION_ID, notification(0), ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
        )
        if (job?.isActive != true) {
            job = scope.launch {
                for (p in 0..100 step 2) {
                    _progress.value = p
                    notify(p)
                    delay(400)
                }
                Log.d(TAG, "동기화 완료")
                // 알림은 "완료"로 남기고 Foreground 상태만 풉니다
                ServiceCompat.stopForeground(this@SyncService, ServiceCompat.STOP_FOREGROUND_DETACH)
                notify(100)
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        scope.cancel()
        super.onDestroy()
    }

    private fun createChannel() {
        val channel = NotificationChannel(CHANNEL_ID, "동기화", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    private fun notification(p: Int) = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(android.R.drawable.stat_notify_sync)
        .setContentTitle("주행 로그 동기화")
        .setContentText(if (p >= 100) "동기화 완료" else "$p%")
        .setProgress(100, p, false)
        .setOnlyAlertOnce(true)
        // Android 12 부터 Foreground Service 알림은 최대 10초 늦게 뜰 수 있어서, 바로 보이게 합니다
        .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
        .setOngoing(p < 100)
        .apply { if (p >= 100) setProgress(0, 0, false) }
        .build()

    private fun notify(p: Int) {
        getSystemService(NotificationManager::class.java).notify(NOTIFICATION_ID, notification(p))
    }

    companion object {
        fun start(context: Context) {
            context.startForegroundService(Intent(context, SyncService::class.java))
        }
    }
}
