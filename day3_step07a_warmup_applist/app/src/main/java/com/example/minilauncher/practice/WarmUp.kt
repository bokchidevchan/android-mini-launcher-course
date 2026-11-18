package com.example.minilauncher.practice

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

// 3일차 1교시 몸풀기: 한 줄로 다른 앱 열기.
// MainActivity.kt 의 // warmUp(this, step = 1) 줄에서 // 를 지우고, 숫자를 1부터 4까지 바꿔 가며 Run 합니다.
// Logcat 검색칸에 tag:WarmUp. 끝나면 그 줄을 다시 // 로 막습니다 (안 그러면 앱을 켤 때마다 열림)

fun warmUp(context: Context, step: Int) {
    // 4단계에서 쓸 "보내기" 편지: 글자(text/plain) 하나를 담음
    val send = Intent(Intent.ACTION_SEND).setType("text/plain")
        .putExtra(Intent.EXTRA_TEXT, "안녕하세요")
    // when: 숫자에 따라 편지 하나를 고름 (C++ switch 와 비슷하지만 고른 값을 돌려줌)
    val intent = when (step) {
        1 -> Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com"))  // 웹 주소를 보여 줘 -> 브라우저
        2 -> Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Seoul+Station"))       // 이 장소를 보여 줘 -> 지도
        3 -> Intent(Intent.ACTION_DIAL, Uri.parse("tel:0000"))                       // 이 번호로 전화 걸 준비 -> 전화 앱
        else -> Intent.createChooser(send, "어디로 보낼까요?")                          // 받을 앱 고르는 창을 늘 띄움
    }
    try {
        context.startActivity(intent)                     // 받을 앱은 시스템이 주소 종류(https, geo, tel)를 보고 찾음
        Log.d("WarmUp", "${step}단계: 열었습니다")
    } catch (e: ActivityNotFoundException) {             // 받을 앱이 하나도 없으면 여기로 (앱이 꺼지지 않음)
        Log.e("WarmUp", "${step}단계: 받을 앱이 없습니다", e)
    }
}
