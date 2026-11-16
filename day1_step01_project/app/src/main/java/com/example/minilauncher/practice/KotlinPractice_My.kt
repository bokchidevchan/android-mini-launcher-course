package com.example.minilauncher.practice

import android.util.Log

// 직접 써 보는 파일입니다.
// KotlinPractice.kt 의 단계별 코드를 보고, 같은 단계의 빈 함수 안에 옮겨 적거나 복사해서 붙입니다.
// MainActivity.kt 의 kotlinPractice(step = 1) 을 myKotlinPractice(step = 1) 로 바꾸고 Run 합니다.
// Logcat 검색창에 tag:MyKotlin 을 넣으면 이 파일이 남긴 줄만 보입니다.
// sampleApps, describe, Counter 처럼 KotlinPractice.kt 에 있는 것은 여기서도 그대로 쓸 수 있습니다.

private const val TAG = "MyKotlin"

private fun show(message: String) {
    Log.d(TAG, message)
}

fun myKotlinPractice(step: Int) {
    show("===== 내가 쓴 ${step}단계 =====")
    when (step) {
        1 -> myStep01()
        2 -> myStep02()
        3 -> myStep03()
        4 -> myStep04()
        5 -> myStep05()
        6 -> myStep06()
        7 -> myStep07()
        8 -> myStep08()
        9 -> myStep09()
        10 -> myStep10()
        else -> show("1부터 10까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 변수와 문자열 (KotlinPractice.kt 의 step01Variables)
private fun myStep01() {
    // 여기에 씁니다
}

// 2단계. null 안전 (KotlinPractice.kt 의 step02NullSafety)
private fun myStep02() {
    // 여기에 씁니다
}

// 3단계. if 와 when (KotlinPractice.kt 의 step03Conditions)
private fun myStep03() {
    // 여기에 씁니다
}

// 4단계. 함수 (KotlinPractice.kt 의 step04Functions)
private fun myStep04() {
    // 여기에 씁니다
}

// 5단계. 리스트와 반복 (KotlinPractice.kt 의 step05Lists)
private fun myStep05() {
    // 여기에 씁니다
}

// 6단계. 람다 (KotlinPractice.kt 의 step06Lambdas)
private fun myStep06() {
    // 여기에 씁니다
}

// 7단계. data class (KotlinPractice.kt 의 step07DataClass)
private fun myStep07() {
    // 여기에 씁니다
}

// 8단계. 클래스 (KotlinPractice.kt 의 step08Classes)
private fun myStep08() {
    // 여기에 씁니다
}

// 9단계. enum class 와 when (KotlinPractice.kt 의 step09EnumWhen)
private fun myStep09() {
    // 여기에 씁니다
}

// 10단계. 종합: 앱 검색 (KotlinPractice.kt 의 step10Search)
private fun myStep10() {
    // 여기에 씁니다
}
