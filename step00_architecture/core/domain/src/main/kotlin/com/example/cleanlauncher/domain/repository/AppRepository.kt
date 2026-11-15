package com.example.cleanlauncher.domain.repository

import com.example.cleanlauncher.domain.model.AppInfo

// "앱 목록을 달라"는 약속만 적어 둡니다.
// 실제로 어디서 가져오는지는 core:data 모듈이 정합니다 (C++ 의 순수 가상 클래스 자리)
interface AppRepository {
    suspend fun loadApps(): List<AppInfo>
}
