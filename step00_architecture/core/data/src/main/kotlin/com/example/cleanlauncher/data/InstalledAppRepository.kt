package com.example.cleanlauncher.data

import android.content.Context
import android.content.Intent
import com.example.cleanlauncher.domain.model.AppInfo
import com.example.cleanlauncher.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 실제 기기에 설치된 앱을 PackageManager 에 물어서 가져옵니다.
// 안드로이드를 아는 코드는 이 모듈 안에만 있습니다
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
            .filter { it.packageName != context.packageName }   // 자기 자신은 뺍니다
    }
}
