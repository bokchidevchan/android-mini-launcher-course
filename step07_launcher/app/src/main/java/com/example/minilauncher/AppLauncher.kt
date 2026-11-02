package com.example.minilauncher

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.minilauncher.model.AppInfo

// 다른 앱 실행: 패키지와 클래스 이름을 정확히 지정한 명시적 Intent
fun launchApp(context: Context, app: AppInfo) {
    val intent = Intent(Intent.ACTION_MAIN)
        .addCategory(Intent.CATEGORY_LAUNCHER)
        .setComponent(ComponentName(app.packageName, app.className))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
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
