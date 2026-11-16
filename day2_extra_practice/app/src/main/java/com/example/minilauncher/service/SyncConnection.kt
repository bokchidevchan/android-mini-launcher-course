package com.example.minilauncher.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

// 화면이 보이는 동안 SyncService 에 붙어(bind) 진행률을 받아 옵니다
@Composable
fun rememberSyncProgress(): Int? {
    val context = LocalContext.current
    var service by remember { mutableStateOf<SyncService?>(null) }
    var progress by remember { mutableStateOf<Int?>(null) }

    DisposableEffect(Unit) {
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                Log.d("SyncService", "화면이 Service 에 연결됨")
                service = (binder as SyncService.LocalBinder).service
            }

            override fun onServiceDisconnected(name: ComponentName) {
                service = null
            }
        }
        // BIND_AUTO_CREATE: Service 가 없으면 만들어서 붙습니다
        context.bindService(Intent(context, SyncService::class.java), connection, Context.BIND_AUTO_CREATE)
        onDispose { context.unbindService(connection) }
    }

    LaunchedEffect(service) {
        service?.progress?.collect { progress = it }
    }
    return progress
}
