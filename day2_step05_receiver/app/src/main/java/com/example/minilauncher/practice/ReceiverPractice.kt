package com.example.minilauncher.practice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

// 2일차 오후 시스템 이벤트 실습 5단계.
// MainActivity.kt 의 receiverPractice(this, step = 1) 에서 숫자를 1부터 5까지 바꿔 가며
// Run 합니다.
// Logcat 검색창에 tag:Receiver 를 넣으면 이 파일이 남긴 줄만 보입니다.
// 직접 써 보고 싶으면 같은 폴더의 ReceiverPractice_My.kt 에 옮겨 적습니다.

private const val TAG = "Receiver"

private fun show(message: String) {
    Log.d(TAG, message)
}

fun receiverPractice(activity: ComponentActivity, step: Int) {
    show("===== ${step}단계 =====")
    when (step) {
        1 -> step01BatteryOnce(activity)
        2 -> step02BatteryBroadcast(activity)
        3 -> step03Charger(activity)
        4 -> step04AirplaneMode(activity)
        5 -> step05Screen(activity)
        else -> show("1부터 5까지의 숫자를 넣어 주세요")
    }
}

// 방송을 받기 시작하고, 화면이 끝날 때(onDestroy) 알아서 그만 받게 합니다.
// 그만 받는 것(unregisterReceiver)을 빼먹으면 3일차 버그 2의 "has leaked
// IntentReceiver" 가 찍힙니다
fun listenWhileAlive(activity: ComponentActivity, filter: IntentFilter, onReceive: (Intent) -> Unit) {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) = onReceive(intent)
    }
    // 시스템이 보내는 방송만 받을 것이라 다른 앱에는 열지 않습니다
    // (RECEIVER_NOT_EXPORTED)
    ContextCompat.registerReceiver(activity, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
    activity.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) activity.unregisterReceiver(receiver)
    })
}

// 배터리 방송에 들어 있는 두 숫자(level, scale)를 퍼센트로 바꿉니다
fun batteryPercentOf(intent: Intent): Int {
    val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
    return level * 100 / scale
}

// 1단계. 배터리 값을 한 번만 읽기
// 배터리 방송은 시스템이 마지막 것을 들고 있습니다. 받는 쪽(receiver)을 null 로 주면 그
// 마지막 방송을 바로 돌려줍니다
private fun step01BatteryOnce(activity: ComponentActivity) {
    val intent = activity.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    if (intent == null) {
        show("배터리 방송이 아직 없습니다")
    } else {
        show("지금 배터리: ${batteryPercentOf(intent)}%")
    }
    // 바꿔 보기: 에뮬레이터 Extended Controls > Battery 에서 Charge level 을 바꾸고
    // 다시 Run 합니다
}

// 2단계. 배터리가 바뀔 때마다 받기
// IntentFilter 는 "어떤 방송을 받을지" 적는 것입니다. 주파수를 맞추는 것과 같습니다
private fun step02BatteryBroadcast(activity: ComponentActivity) {
    listenWhileAlive(activity, IntentFilter(Intent.ACTION_BATTERY_CHANGED)) { intent ->
        show("배터리 방송: ${batteryPercentOf(intent)}%")
    }
    show("배터리 방송을 받기 시작했습니다. Extended Controls > Battery 에서 Charge level 을 바꿔 보세요")
    // 바꿔 보기: 20% 이하면 "배터리 부족" 을 같이 찍게 고쳐 봅니다
}

// 3단계. 충전기를 꽂고 뺄 때 받기
// 필터 하나에 방송 여러 개를 적을 수 있습니다. 어떤 방송이 왔는지는 intent.action 으로
// 가립니다
private fun step03Charger(activity: ComponentActivity) {
    val filter = IntentFilter().apply {
        addAction(Intent.ACTION_POWER_CONNECTED)
        addAction(Intent.ACTION_POWER_DISCONNECTED)
    }
    listenWhileAlive(activity, filter) { intent ->
        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> show("충전기를 꽂았습니다")
            Intent.ACTION_POWER_DISCONNECTED -> show("충전기를 뺐습니다")
        }
    }
    show("충전기 방송을 받기 시작했습니다. Extended Controls > Battery 에서 Charger connection 을 바꿔 보세요")
    // 바꿔 보기: 꽂았을 때 지금 배터리 퍼센트도 같이 찍어 봅니다 (1단계의 방법)
}

// 4단계. 비행기 모드
// 방송에는 값이 같이 실려 옵니다. 비행기 모드 방송에는 켜졌는지(state)가 들어 있습니다
private fun step04AirplaneMode(activity: ComponentActivity) {
    listenWhileAlive(activity, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)) { intent ->
        val on = intent.getBooleanExtra("state", false)
        show(if (on) "비행기 모드가 켜졌습니다" else "비행기 모드가 꺼졌습니다")
    }
    show("비행기 모드 방송을 받기 시작했습니다. 화면 위쪽을 끌어내려 비행기 모드를 켜고 꺼 보세요")
    // 바꿔 보기: 켜진 횟수를 세어 "3번째" 처럼 같이 찍어 봅니다
}

// 5단계. 화면이 꺼지고 켜질 때
// 이 두 방송은 Manifest 에 적어서는 못 받고, 이렇게 코드로 등록해야만 받습니다
private fun step05Screen(activity: ComponentActivity) {
    val filter = IntentFilter().apply {
        addAction(Intent.ACTION_SCREEN_OFF)
        addAction(Intent.ACTION_SCREEN_ON)
    }
    listenWhileAlive(activity, filter) { intent ->
        show(if (intent.action == Intent.ACTION_SCREEN_OFF) "화면이 꺼졌습니다" else "화면이 켜졌습니다")
    }
    show("화면 방송을 받기 시작했습니다. 에뮬레이터 도구막대의 전원 버튼을 두 번 눌러 보세요")
    // 바꿔 보기: 화면이 꺼질 때 tag:Lifecycle 에는 무엇이 찍히는지 같이 봅니다
}
