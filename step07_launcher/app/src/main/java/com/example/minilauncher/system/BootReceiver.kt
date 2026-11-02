package com.example.minilauncher.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 부팅이 끝나면 시스템이 보내는 방송을 받아 시각을 저장합니다.
// 주의: 설치 후 앱을 한 번도 안 열었거나 "강제 종료"한 앱은 받지 못합니다
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val time = SimpleDateFormat("HH:mm", Locale.KOREA).format(Date())
        Log.d("SystemEvent", "부팅 완료 수신: $time")
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString(KEY_BOOT, time).apply()
    }

    companion object {
        const val PREFS = "status"
        const val KEY_BOOT = "boot_time"

        fun lastBoot(context: Context): String? =
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_BOOT, null)
    }
}
