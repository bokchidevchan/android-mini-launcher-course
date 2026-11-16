package com.example.minilauncher.practice

import android.util.Log

// 직접 써 보는 파일입니다.
// LogPractice.kt 의 단계별 코드를 보고, 같은 단계의 빈 함수 안에 옮겨 적거나 복사해서 붙입니다.
// MainActivity.kt 의 logPractice(step = 1) 을 myLogPractice(step = 1) 로 바꾸고 Run 합니다.
// Logcat 검색창에 tag:MyLog 을 넣으면 이 파일이 남긴 줄만 보입니다.
// 여기서는 Log.d(TAG, ...) 대신 show(...) 를 써도 됩니다

private const val TAG = "MyLog"

private fun show(message: String) {
    Log.d(TAG, message)
}

fun myLogPractice(step: Int) {
    show("===== 내가 쓴 ${step}단계 =====")
    when (step) {
        1 -> myLogStep01()
        2 -> myLogStep02()
        3 -> myLogStep03()
        4 -> myLogStep04()
        5 -> myLogStep05()
        else -> show("1부터 5까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 로그 레벨 다섯 가지 (LogPractice.kt 의 step01Levels)
@Suppress("UNUSED_PARAMETER")
private fun myLogStep01() {
    // 여기에 씁니다
}

// 2단계. 값을 같이 찍기 (LogPractice.kt 의 step02Values)
@Suppress("UNUSED_PARAMETER")
private fun myLogStep02() {
    // 여기에 씁니다
}

// 3단계. 태그 나누기 (LogPractice.kt 의 step03Tags)
@Suppress("UNUSED_PARAMETER")
private fun myLogStep03() {
    // 여기에 씁니다
}

// 4단계. 예외를 잡아서 찍기 (LogPractice.kt 의 step04Caught)
@Suppress("UNUSED_PARAMETER")
private fun myLogStep04() {
    // 여기에 씁니다
}

// 5단계. 잡지 않은 예외 (LogPractice.kt 의 step05Crash)
@Suppress("UNUSED_PARAMETER")
private fun myLogStep05() {
    // 여기에 씁니다
}
