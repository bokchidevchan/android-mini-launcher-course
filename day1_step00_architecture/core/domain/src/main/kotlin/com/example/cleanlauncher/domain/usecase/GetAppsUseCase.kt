package com.example.cleanlauncher.domain.usecase

import com.example.cleanlauncher.domain.model.AppInfo
import com.example.cleanlauncher.domain.repository.AppRepository

// 규칙 하나: 앱 목록은 이름순으로 보여 줍니다
class GetAppsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): List<AppInfo> =
        repository.loadApps().sortedBy { it.label.lowercase() }
}
