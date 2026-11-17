package com.example.minilauncher.practice

import android.util.Log

// 직접 써 보는 파일입니다. [1] 부터 [5] 까지 채웁니다. 순서와 힌트는 README.md 에 있습니다.
// 막히면 LogPractice.kt 의 같은 단계 함수를 봅니다.
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
private fun myLogStep01() {
    // [1] 여기를 채웁니다: 로그 레벨 다섯 가지(V, D, I, W, E)로 한 줄씩 찍기
}

// 2단계. 값을 같이 찍기 (LogPractice.kt 의 step02Values)
private fun myLogStep02() {
    // [2] 여기를 채웁니다: 값을 같이 찍기. 목록과 검색어, 거른 결과를 한 줄에
}

// 3단계. 태그 나누기 (LogPractice.kt 의 step03Tags)
private fun myLogStep03() {
    // [3] 여기를 채웁니다: 태그 두 개로 나눠 찍기
}

// 4단계. 예외를 잡아서 찍기 (LogPractice.kt 의 step04Caught)
private fun myLogStep04() {
    // [4] 여기를 채웁니다: 없는 자리를 읽는 예외를 잡아서 Log.e 로 찍기
}

// 5단계. 잡지 않은 예외 (LogPractice.kt 의 step05Crash)
private fun myLogStep05() {
    // [5] 여기를 채웁니다: 잡지 않은 예외로 앱이 꺼지게 하기
}
