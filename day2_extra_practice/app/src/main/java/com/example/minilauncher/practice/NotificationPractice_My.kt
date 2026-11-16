package com.example.minilauncher.practice

import android.util.Log
import android.content.Context
import android.os.Handler
import android.os.Looper

// 직접 써 보는 파일입니다.
// NotificationPractice.kt 의 단계별 코드를 보고, 같은 단계의 빈 함수 안에 옮겨 적거나 복사해서 붙입니다.
// MainActivity.kt 의 notificationPractice(this, step = 1) 을 myNotificationPractice(this, step = 1) 로 바꾸고 Run 합니다.
// Logcat 검색창에 tag:MyNoti 을 넣으면 이 파일이 남긴 줄만 보입니다.
// createPracticeChannel, practiceNotification, PRACTICE_NOTIFICATION 은 NotificationPractice.kt 에 있는 것을 그대로 쓸 수 있습니다

private const val TAG = "MyNoti"

private fun show(message: String) {
    Log.d(TAG, message)
}

fun myNotificationPractice(context: Context, step: Int) {
    show("===== 내가 쓴 ${step}단계 =====")
    when (step) {
        1 -> myNotificationStep01(context)
        2 -> myNotificationStep02(context)
        3 -> myNotificationStep03(context)
        4 -> myNotificationStep04(context)
        5 -> myNotificationStep05(context)
        else -> show("1부터 5까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 알림 채널 만들기 (NotificationPractice.kt 의 step01Channel)
@Suppress("UNUSED_PARAMETER")
private fun myNotificationStep01(context: Context) {
    // 여기에 씁니다
}

// 2단계. 알림 하나 띄우기 (NotificationPractice.kt 의 step02Notify)
@Suppress("UNUSED_PARAMETER")
private fun myNotificationStep02(context: Context) {
    // 여기에 씁니다
}

// 3단계. 같은 번호로 다시 띄워 바꾸기 (NotificationPractice.kt 의 step03Update)
@Suppress("UNUSED_PARAMETER")
private fun myNotificationStep03(context: Context) {
    // 여기에 씁니다
}

// 4단계. 진행 막대가 있는 알림 (NotificationPractice.kt 의 step04Progress)
@Suppress("UNUSED_PARAMETER")
private fun myNotificationStep04(context: Context) {
    // 여기에 씁니다
}

// 5단계. 알림 지우기 (NotificationPractice.kt 의 step05Cancel)
@Suppress("UNUSED_PARAMETER")
private fun myNotificationStep05(context: Context) {
    // 여기에 씁니다
}
