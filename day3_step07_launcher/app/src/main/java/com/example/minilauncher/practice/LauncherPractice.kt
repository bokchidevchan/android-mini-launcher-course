package com.example.minilauncher.practice

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.provider.Settings
import android.util.Log

// 3일차 오전 런처 실습 8단계.
// MainActivity.kt 의 launcherPractice(this, step = 1) 에서 숫자를 1부터 8까지 바꿔 가며 Run 합니다.
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
        5 -> step05Open(context)
        6 -> step06AppInfo(context)
        7 -> step07HomeCandidate(context)
        8 -> step08Newest(context)
        else -> show("1부터 8까지의 숫자를 넣어 주세요")
    }
}

// "앱 목록에 아이콘이 보이는 화면"을 모두 찾습니다. MAIN + LAUNCHER 가 적힌 화면이 그것입니다.
// Manifest 의 <queries> 에 같은 것을 적어 두어야 다른 앱이 보입니다
fun launchableApps(context: Context): List<ResolveInfo> {
    val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    return context.packageManager.queryIntentActivities(intent, 0)
}

// 1단계. 설치된 앱이 몇 개인지
private fun step01Count(context: Context) {
    show("아이콘이 있는 앱: ${launchableApps(context).size}개")
    // 바꿔 보기: AndroidManifest.xml 의 <queries> 덩어리를 주석으로 막고 Run 하면 몇 개가 될까요? 확인하고 되돌립니다
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

// 5단계. 찾은 앱 열기
// 패키지와 화면 이름을 콕 집어 적은 Intent (명시적 Intent). Run 하면 설정 앱이 바로 열립니다
private fun step05Open(context: Context) {
    val pm = context.packageManager
    val found = launchableApps(context).find { it.loadLabel(pm).toString() == "Settings" }
    if (found == null) {
        show("Settings 라는 이름의 앱이 없습니다")
        return
    }
    val intent = Intent(Intent.ACTION_MAIN)
        .addCategory(Intent.CATEGORY_LAUNCHER)
        .setComponent(ComponentName(found.activityInfo.packageName, found.activityInfo.name))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
        context.startActivity(intent)
        show("열었습니다: ${found.activityInfo.packageName}")
    } catch (e: ActivityNotFoundException) {
        Log.e(TAG, "열 수 없음", e)
    }
    // 확인했으면 step 을 1로 되돌립니다. 그대로 두면 앱을 켤 때마다 설정이 열립니다
}

// 6단계. 내 앱의 "앱 정보" 화면 열기
// 이번에는 화면을 콕 집지 않고 "앱 정보를 보여 달라"고만 적습니다 (암시적 Intent). 어느 앱인지는 package: 주소로 알립니다
private fun step06AppInfo(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${context.packageName}"))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
        context.startActivity(intent)
        show("앱 정보 화면을 열었습니다: ${context.packageName}")
    } catch (e: ActivityNotFoundException) {
        Log.e(TAG, "열 수 없음", e)
    }
    // 확인했으면 step 을 1로 되돌립니다
}

// 7단계. 이 앱이 홈 앱 후보인지 확인하기
// 홈 버튼을 누르면 시스템은 HOME 카테고리가 적힌 화면을 찾습니다. 같은 것을 물어서 내 앱이 들어 있는지 봅니다
private fun step07HomeCandidate(context: Context) {
    val home = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
    val mine = context.packageManager.queryIntentActivities(home, 0)
        .any { it.activityInfo.packageName == context.packageName }
    show("이 앱은 홈 앱 후보인가: $mine")
    // 바꿔 보기: AndroidManifest.xml 에서 HOME 카테고리 줄을 주석으로 막고 Run 하면 false 가 됩니다. 확인하고 되돌립니다
}

// 8단계. 가장 최근에 설치한 앱 찾기
// 앱마다 설치된 시각을 물어서 가장 늦은 것을 고릅니다. 방금 Run 한 이 앱이 나오는 것이 보통입니다
private fun step08Newest(context: Context) {
    val pm = context.packageManager
    val newest = launchableApps(context).maxByOrNull { info ->
        pm.getPackageInfo(info.activityInfo.packageName, 0).lastUpdateTime
    }
    if (newest == null) {
        show("앱이 없습니다")
    } else {
        show("가장 최근에 설치하거나 고친 앱: ${newest.loadLabel(pm)}")
    }
    // 바꿔 보기: maxByOrNull 을 minByOrNull 로 바꾸면 가장 오래된 앱이 나옵니다
}
