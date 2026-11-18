package com.example.minilauncher.practice

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.provider.Settings
import android.util.Log

// 3일차 1교시 런처 실습 1~4단계.
// MainActivity.kt 의 launcherPractice(this, step = 1) 에서 숫자를 1부터 4까지 바꿔 가며 Run 합니다.
// Logcat 검색창에 tag:LauncherPractice 를 넣으면 이 파일이 남긴 줄만 보입니다.
// 직접 써 보고 싶으면 같은 폴더의 LauncherPractice_My.kt 에 옮겨 적습니다.

private const val TAG = "LauncherPractice"

private fun show(message: String) {
    Log.d(TAG, message)
}

fun launcherPractice(context: Context, step: Int) {
    show("===== ${step}단계 =====")
    when (step) {
        1 -> step01Count(context)
        2 -> step02Labels(context)
        3 -> step03Sorted(context)
        4 -> step04Find(context)
        else -> show("1부터 4까지의 숫자를 넣어 주세요")
    }
}

// "앱 목록에 아이콘이 보이는 화면"을 모두 찾습니다. MAIN + LAUNCHER 가 적힌 화면이 그것입니다.
// Manifest 의 <queries> 에 같은 것을 적어 두어야 다른 앱이 보입니다
fun launchableApps(context: Context): List<ResolveInfo> {
    // ACTION_MAIN: 첫 화면으로 들어가기.  CATEGORY_LAUNCHER: 앱 서랍에 아이콘으로 보이는 화면. 둘을 합치면 "아이콘 누르면 뜨는 첫 화면"
    val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    return context.packageManager.queryIntentActivities(intent, 0)
}

// 1단계. 설치된 앱이 몇 개인지
private fun step01Count(context: Context) {
    show("아이콘이 있는 앱: ${launchableApps(context).size}개")
    // 바꿔 보면: AndroidManifest.xml 의 <queries> 덩어리를 주석으로 막고 Run 하면 숫자가
    // 몇 개로 확 줄어듭니다. 오류는 나지 않습니다. 확인하고 되돌립니다
}

// 2단계. 앱 이름 읽기
// 이름(label)은 PackageManager 에게 물어야 나옵니다. 기기 언어에 따라 다른 이름이 나올 수 있습니다
private fun step02Labels(context: Context) {
    val pm = context.packageManager
    launchableApps(context).take(5).forEach { info ->
        show("이름: ${info.loadLabel(pm)}")
    }
    // 바꿔 보기: take(5) 를 지워 전부 찍어 봅니다
}

// 3단계. 이름순으로 정렬하고 패키지 이름도 같이
private fun step03Sorted(context: Context) {
    val pm = context.packageManager
    launchableApps(context)
        .sortedBy { it.loadLabel(pm).toString().lowercase() }
        .take(5)
        .forEach { info -> show("${info.loadLabel(pm)} (${info.activityInfo.packageName})") }
    // 바꿔 보기: sortedBy 를 sortedByDescending 으로 바꿔 봅니다
}

// 4단계. 이름으로 앱 하나 찾기
// 앱을 열려면 패키지 이름과 화면(클래스) 이름 두 가지가 필요합니다
private fun step04Find(context: Context) {
    val pm = context.packageManager
    val found = launchableApps(context).find { it.loadLabel(pm).toString() == "Settings" }
    if (found == null) {
        show("Settings 라는 이름의 앱이 없습니다 (에뮬레이터 언어가 영어인지 확인)")
    } else {
        show("패키지: ${found.activityInfo.packageName}")
        show("화면: ${found.activityInfo.name}")
    }
    // 바꿔 보기: "Settings" 를 "Chrome" 으로 바꿔 봅니다
}




