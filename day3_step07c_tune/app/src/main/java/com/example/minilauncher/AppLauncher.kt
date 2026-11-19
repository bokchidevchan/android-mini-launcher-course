package com.example.minilauncher

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.minilauncher.model.AppInfo

// 다른 앱 실행: 패키지와 클래스 이름을 정확히 지정한 명시적 Intent
// app: 누른 아이콘 한 칸의 정보 (1교시에 시스템에서 받은 AppInfo). Settings 를 눌렀다면
//   app.packageName = "com.android.settings"           어느 앱
//   app.className   = "com.android.settings.Settings"  그 앱의 어느 화면(Activity)
fun launchApp(context: Context, app: AppInfo) {
    val intent = Intent(Intent.ACTION_MAIN)
        .addCategory(Intent.CATEGORY_LAUNCHER)   // 아이콘으로 여는 화면처럼 (앱 서랍에서 누른 것과 같게)
        .setComponent(ComponentName(app.packageName, app.className))   // 이 앱의 이 화면으로 콕 집음 (명시적 Intent)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)   // NEW_TASK: 최근 앱 화면에 새 카드로 엶
    try {
        Log.d("Launcher", "실행: ${app.packageName}/${app.className}")
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // 목록을 받은 뒤 앱이 삭제되면 여기로 옵니다
        Log.e("Launcher", "실행 실패: ${app.packageName}", e)
        Toast.makeText(context, "${app.label} 을(를) 열 수 없습니다", Toast.LENGTH_SHORT).show()
    }
}

fun openDetail(context: Context, app: AppInfo) {
    context.startActivity(
        Intent(context, DetailActivity::class.java)
            .putExtra(DetailActivity.EXTRA_LABEL, app.label)
            .putExtra(DetailActivity.EXTRA_PACKAGE, app.packageName)
    )
}
