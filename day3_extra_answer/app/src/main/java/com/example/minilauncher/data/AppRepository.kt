package com.example.minilauncher.data

import com.example.minilauncher.model.AppInfo

// 앱 목록을 가져오는 약속(interface)입니다
interface AppRepository {
    suspend fun loadApps(): List<AppInfo>
}
