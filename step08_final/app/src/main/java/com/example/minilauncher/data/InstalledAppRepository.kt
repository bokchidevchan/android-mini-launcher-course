package com.example.minilauncher.data

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.minilauncher.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 실제 설치된 앱 중 "앱 서랍에 보일 앱"(MAIN + LAUNCHER)을 PackageManager 에 묻습니다
class InstalledAppRepository(private val context: Context) : AppRepository {

    override suspend fun loadApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        pm.queryIntentActivities(intent, 0)
            .map {
                AppInfo(
                    label = it.loadLabel(pm).toString(),
                    packageName = it.activityInfo.packageName,
                    className = it.activityInfo.name,
                )
            }
            .filter { it.packageName != context.packageName }   // 런처 자신은 빼기
            .sortedBy { it.label.lowercase() }
            .also { Log.d("Launcher", "설치 앱 ${it.size}개 조회") }
    }
}
