package com.example.minilauncher.model

// 검색: 이름에 글자가 들어 있으면 남깁니다 (대소문자 무시). 빈 검색어면 전체
fun filterApps(apps: List<AppInfo>, query: String): List<AppInfo> {
    val q = query.trim()
    if (q.isEmpty()) return apps
    return apps.filter { it.label.contains(q, ignoreCase = true) }
}

// 도크에 고정할 앱 이름 (Figma 완성본과 같은 순서)
val DockLabels = listOf("Phone", "Messages", "Chrome", "Camera")

fun dockApps(apps: List<AppInfo>): List<AppInfo> =
    DockLabels.mapNotNull { label -> apps.firstOrNull { it.label == label } }
