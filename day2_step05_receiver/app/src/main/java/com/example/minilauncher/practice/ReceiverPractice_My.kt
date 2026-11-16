package com.example.minilauncher.practice

import android.util.Log
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity

// 직접 써 보는 파일입니다.
// ReceiverPractice.kt 의 단계별 코드를 보고, 같은 단계의 빈 함수 안에 옮겨 적거나 복사해서 붙입니다.
// MainActivity.kt 의 receiverPractice(this, step = 1) 을 myReceiverPractice(this, step = 1) 로 바꾸고 Run 합니다.
// Logcat 검색창에 tag:MyReceiver 을 넣으면 이 파일이 남긴 줄만 보입니다.
// listenWhileAlive, batteryPercentOf 처럼 ReceiverPractice.kt 에 있는 것은 여기서도 그대로 쓸 수 있습니다

private const val TAG = "MyReceiver"

private fun show(message: String) {
    Log.d(TAG, message)
}

fun myReceiverPractice(activity: ComponentActivity, step: Int) {
    show("===== 내가 쓴 ${step}단계 =====")
    when (step) {
        1 -> myReceiverStep01(activity)
        2 -> myReceiverStep02(activity)
        3 -> myReceiverStep03(activity)
        4 -> myReceiverStep04(activity)
        5 -> myReceiverStep05(activity)
        else -> show("1부터 5까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 배터리 값을 한 번만 읽기 (ReceiverPractice.kt 의 step01BatteryOnce)
@Suppress("UNUSED_PARAMETER")
private fun myReceiverStep01(activity: ComponentActivity) {
    // 여기에 씁니다
}

// 2단계. 배터리가 바뀔 때마다 받기 (ReceiverPractice.kt 의 step02BatteryBroadcast)
@Suppress("UNUSED_PARAMETER")
private fun myReceiverStep02(activity: ComponentActivity) {
    // 여기에 씁니다
}

// 3단계. 충전기를 꽂고 뺄 때 받기 (ReceiverPractice.kt 의 step03Charger)
@Suppress("UNUSED_PARAMETER")
private fun myReceiverStep03(activity: ComponentActivity) {
    // 여기에 씁니다
}

// 4단계. 비행기 모드 (ReceiverPractice.kt 의 step04AirplaneMode)
@Suppress("UNUSED_PARAMETER")
private fun myReceiverStep04(activity: ComponentActivity) {
    // 여기에 씁니다
}

// 5단계. 화면이 꺼지고 켜질 때 (ReceiverPractice.kt 의 step05Screen)
@Suppress("UNUSED_PARAMETER")
private fun myReceiverStep05(activity: ComponentActivity) {
    // 여기에 씁니다
}
