package com.example.minilauncher.practice

import android.util.Log
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

// 직접 써 보는 파일입니다.
// LauncherPractice.kt 의 단계별 코드를 보고, 같은 단계의 빈 함수 안에 옮겨 적거나 복사해서 붙입니다.
// MainActivity.kt 의 launcherPractice(this, step = 1) 을 myLauncherPractice(this, step = 1) 로 바꾸고 Run 합니다.
// Logcat 검색창에 tag:MyLauncher 을 넣으면 이 파일이 남긴 줄만 보입니다.
// launchableApps 는 LauncherPractice.kt 에 있는 것을 그대로 쓸 수 있습니다

private const val TAG = "MyLauncher"

private fun show(message: String) {
    Log.d(TAG, message)
}

fun myLauncherPractice(context: Context, step: Int) {
    show("===== 내가 쓴 ${step}단계 =====")
    when (step) {
        1 -> myLauncherStep01(context)
        2 -> myLauncherStep02(context)
        3 -> myLauncherStep03(context)
        4 -> myLauncherStep04(context)
        5 -> myLauncherStep05(context)
        6 -> myLauncherStep06(context)
        7 -> myLauncherStep07(context)
        8 -> myLauncherStep08(context)
        else -> show("1부터 8까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 설치된 앱이 몇 개인지 (LauncherPractice.kt 의 step01Count)
@Suppress("UNUSED_PARAMETER")
private fun myLauncherStep01(context: Context) {
    // 여기에 씁니다
}

// 2단계. 앱 이름 읽기 (LauncherPractice.kt 의 step02Labels)
@Suppress("UNUSED_PARAMETER")
private fun myLauncherStep02(context: Context) {
    // 여기에 씁니다
}

// 3단계. 이름순으로 정렬하고 패키지 이름도 같이 (LauncherPractice.kt 의 step03Sorted)
@Suppress("UNUSED_PARAMETER")
private fun myLauncherStep03(context: Context) {
    // 여기에 씁니다
}

// 4단계. 이름으로 앱 하나 찾기 (LauncherPractice.kt 의 step04Find)
@Suppress("UNUSED_PARAMETER")
private fun myLauncherStep04(context: Context) {
    // 여기에 씁니다
}

// 5단계. 찾은 앱 열기 (LauncherPractice.kt 의 step05Open)
@Suppress("UNUSED_PARAMETER")
private fun myLauncherStep05(context: Context) {
    // 여기에 씁니다
}

// 6단계. 내 앱의 앱 정보 화면 열기 (LauncherPractice.kt 의 step06AppInfo)
@Suppress("UNUSED_PARAMETER")
private fun myLauncherStep06(context: Context) {
    // 여기에 씁니다
}

// 7단계. 이 앱이 홈 앱 후보인지 확인하기 (LauncherPractice.kt 의 step07HomeCandidate)
@Suppress("UNUSED_PARAMETER")
private fun myLauncherStep07(context: Context) {
    // 여기에 씁니다
}

// 8단계. 가장 최근에 설치한 앱 찾기 (LauncherPractice.kt 의 step08Newest)
@Suppress("UNUSED_PARAMETER")
private fun myLauncherStep08(context: Context) {
    // 여기에 씁니다
}
