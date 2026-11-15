package com.example.minilauncher.data

import com.example.minilauncher.model.AppInfo

// 앱 목록을 어디서 가져오든 ViewModel 은 이 약속(interface)만 압니다.
// 2일차의 가짜 목록과 3일차의 실제 목록을 갈아 끼울 수 있는 이유입니다
interface AppRepository {
    suspend fun loadApps(): List<AppInfo>
}
