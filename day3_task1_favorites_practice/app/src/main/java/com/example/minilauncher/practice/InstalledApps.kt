package com.example.minilauncher.practice

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 3일차 오전 과제 "즐겨찾기 서랍" 에서 고치지 않고 그대로 쓰는 도구 모음입니다.
// 1교시, 2교시에 본 코드를 이 과제에 필요한 만큼만 옮겨 왔습니다.

// 설치된 앱 하나. className 은 그 앱을 열 때 씁니다
data class AppInfo(
    val label: String,          // 화면에 보이는 이름 (예: Chrome)
    val packageName: String,    // 앱의 주소 (예: com.android.chrome)
    val className: String,      // 열 화면의 이름
)

// 아이콘이 있는 앱(MAIN + LAUNCHER)을 시스템에 물어 이름순으로 돌려줌 (1교시와 같은 질문)
suspend fun loadInstalledApps(context: Context): List<AppInfo> = withContext(Dispatchers.IO) {   // Dispatchers.IO: 오래 걸리는 일을 화면 스레드 밖에서
    val pm = context.packageManager
    val query = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    pm.queryIntentActivities(query, 0)      // 0: 추가 옵션 없음
        .map { AppInfo(it.loadLabel(pm).toString(), it.activityInfo.packageName, it.activityInfo.name) }
        .filter { it.packageName != context.packageName }      // 이 앱 자신은 빼기
        .sortedBy { it.label.lowercase() }
}

// 그 앱을 엶. 받는 화면을 콕 집어 적는 명시적 Intent (2교시와 같은 코드)
fun launchApp(context: Context, app: AppInfo) {
    val intent = Intent(Intent.ACTION_MAIN)
        .addCategory(Intent.CATEGORY_LAUNCHER)
        .setComponent(ComponentName(app.packageName, app.className))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)   // 다른 앱을 새 작업으로 엶
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // 목록을 받은 뒤 앱이 지워졌으면 여기로 옴
        Toast.makeText(context, "${app.label} 을(를) 열 수 없습니다", Toast.LENGTH_SHORT).show()
    }
}
