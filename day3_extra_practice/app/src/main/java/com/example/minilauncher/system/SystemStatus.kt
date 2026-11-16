package com.example.minilauncher.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "SystemEvent"

data class BatteryInfo(val level: Int, val charging: Boolean)

// 배터리: 동적 등록. 화면에 보이는 동안만 받고, 화면이 사라지면 해제합니다 (등록과 해제는 항상 짝)
@Composable
fun rememberBattery(): BatteryInfo? {
    val context = LocalContext.current
    var info by remember { mutableStateOf<BatteryInfo?>(null) }
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context, intent: Intent) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                val charging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
                info = BatteryInfo(level * 100 / scale, charging)
                Log.d(TAG, "배터리 변경: $info")
            }
        }
        // ACTION_BATTERY_CHANGED 는 sticky 방송이라 등록하자마자 현재 값이 한 번 들어옵니다
        ContextCompat.registerReceiver(
            context, receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED), ContextCompat.RECEIVER_NOT_EXPORTED,
        )
        onDispose {
            context.unregisterReceiver(receiver)
            Log.d(TAG, "배터리 Receiver 해제")
        }
    }
    return info
}

// 네트워크: 방송 대신 ConnectivityManager 콜백을 씁니다 (CONNECTIVITY_ACTION 방송은 막혔습니다)
@Composable
fun rememberNetwork(): String {
    val context = LocalContext.current
    val cm = remember { context.getSystemService(ConnectivityManager::class.java) }
    var name by remember { mutableStateOf(describe(cm, cm.activeNetwork)) }
    DisposableEffect(Unit) {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network, caps: NetworkCapabilities) {
                name = describe(caps)
                Log.d(TAG, "네트워크: $name")
            }

            override fun onLost(network: Network) {
                name = OFFLINE
                Log.d(TAG, "네트워크: 끊김")
            }
        }
        // 콜백은 메인 스레드가 아닌 곳에서 불립니다. Compose 상태는 어느 스레드에서 바꿔도 안전합니다
        cm.registerDefaultNetworkCallback(callback)
        onDispose { cm.unregisterNetworkCallback(callback) }
    }
    return name
}

const val OFFLINE = "오프라인"

private fun describe(cm: ConnectivityManager, network: Network?): String =
    network?.let { cm.getNetworkCapabilities(it) }?.let(::describe) ?: OFFLINE

private fun describe(caps: NetworkCapabilities): String = when {
    caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"
    caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "모바일"
    caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "유선"
    else -> "연결됨"
}

// 1초마다 바뀌는 시계. produceState 안의 코루틴은 화면이 사라지면 같이 멈춥니다
@Composable
fun rememberClock(): String {
    val clock by produceState(now()) {
        while (true) {
            value = now()
            delay(1_000)
        }
    }
    return clock
}

private fun now() = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(Date())
