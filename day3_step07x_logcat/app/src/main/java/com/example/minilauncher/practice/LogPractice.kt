package com.example.minilauncher.practice

import android.util.Log

// 3일차 오후 로그 실습 5단계.
// MainActivity.kt 의 logPractice(step = 1) 에서 숫자를 1부터 5까지 바꿔 가며 Run 합니다.
// Logcat 검색창에 tag:LogPractice 를 넣습니다.
// 직접 써 보고 싶으면 같은 폴더의 LogPractice_My.kt 에 옮겨 적습니다.

private const val TAG = "LogPractice"

fun logPractice(step: Int) {
    Log.d(TAG, "===== ${step}단계 =====")
    when (step) {
        1 -> step01Levels()
        2 -> step02Values()
        3 -> step03Tags()
        4 -> step04Caught()
        5 -> step05Crash()
        else -> Log.d(TAG, "1부터 5까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 로그 레벨 다섯 가지
// V, D, I, W, E 순으로 심각해집니다. Logcat 검색창에 level:warn 을 더하면 W 와 E 만 남습니다
private fun step01Levels() {
    Log.v(TAG, "V: 아주 자세한 기록")
    Log.d(TAG, "D: 개발하면서 보는 기록")
    Log.i(TAG, "I: 알아 둘 만한 일")
    Log.w(TAG, "W: 이상하지만 계속 돌 수 있는 일")
    Log.e(TAG, "E: 실패한 일")
    // 바꿔 보기: 검색창을 tag:LogPractice level:warn 으로 바꿔 몇 줄이 남는지 봅니다
}

// 2단계. 값을 같이 찍기
// 로그에는 "어디서, 무슨 값이었는지"를 적습니다. 값이 없는 로그는 나중에 쓸모가 없습니다
private fun step02Values() {
    val apps = listOf("Settings", "Camera", "Chrome")
    val query = "c"
    val result = apps.filter { it.contains(query, ignoreCase = true) }
    Log.d(TAG, "검색어=$query, 전체=${apps.size}개, 결과=${result.size}개 $result")
}

// 3단계. 태그 나누기
// 태그는 "누가 찍었는지"입니다. 기능마다 태그를 다르게 주면 검색창에서 골라 볼 수 있습니다
private fun step03Tags() {
    Log.d("LogPractice.Search", "검색을 시작했습니다")
    Log.d("LogPractice.Network", "서버에 물어봤습니다")
    Log.d("LogPractice.Search", "검색이 끝났습니다")
    // 바꿔 보기: 검색창에 tag:LogPractice.Search 를 넣어 봅니다. tag:LogPractice 만 넣으면 전부 보입니다
}

// 4단계. 예외를 잡아서 찍기
// Log.e 의 세 번째 자리에 예외를 넘기면 어디서 났는지(스택)가 같이 찍힙니다. 잡았으므로 앱은 꺼지지 않습니다
private fun step04Caught() {
    val apps = listOf("Settings", "Camera", "Chrome")
    try {
        Log.d(TAG, "6번째 앱: ${apps[5]}")
    } catch (e: IndexOutOfBoundsException) {
        Log.e(TAG, "없는 자리를 읽었습니다. 크기=${apps.size}", e)
    }
    Log.d(TAG, "예외를 잡았기 때문에 이 줄까지 옵니다")
    // 바꿔 보기: Logcat 에서 at com.example.minilauncher 로 시작하는 줄의 파란 글자를 눌러 봅니다
}

// 5단계. 잡지 않은 예외
// 앱이 꺼집니다. 검색창을 package:mine 으로 바꾸고 FATAL EXCEPTION, 예외 이름, at com.example.minilauncher 줄을 찾습니다
private fun step05Crash() {
    val label: String? = null
    Log.d(TAG, "곧 꺼집니다")
    Log.d(TAG, "글자 수: ${label!!.length}")
    // 확인했으면 step 을 1로 되돌립니다. 그대로 두면 앱을 켤 때마다 꺼집니다
}
