package com.example.cleanlauncher.domain.usecase

import com.example.cleanlauncher.domain.model.AppInfo

// 규칙 하나: 검색어가 이름에 들어 있는 앱만 남깁니다. 대소문자는 가리지 않습니다
class SearchAppsUseCase {
    operator fun invoke(apps: List<AppInfo>, query: String): List<AppInfo> {
        val keyword = query.trim()
        if (keyword.isEmpty()) return apps
        return apps.filter { it.label.contains(keyword, ignoreCase = true) }
    }
}
