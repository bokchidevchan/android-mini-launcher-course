package com.example.minilauncher.model

// 검색: 앱 목록(apps)과 검색어(query)를 받아서, 이름에 검색어가 든 앱만 돌려줍니다
fun filterApps(apps: List<AppInfo>, query: String): List<AppInfo> {
    val q = query.trim()                 // trim: 앞뒤 빈칸 지우기. "  set " -> "set"
    if (q.isEmpty()) return apps         // 검색어가 비었으면 전체 목록 그대로

    // filter { 조건 }: 조건이 true 인 것만 남깁니다. it 은 목록에서 꺼낸 앱 하나
    // contains(q, ignoreCase = true): 이름 안에 q 가 들어 있나. ignoreCase = true 는 대소문자를 무시하라는 옵션
    //   그래서 "set" 으로 "Settings" 를 찾습니다. 옵션이 없으면 s 와 S 가 달라서 못 찾습니다
    return apps.filter { it.label.contains(q, ignoreCase = true) }
}

// 아래 고정 줄에 둘 앱 이름. 이 순서대로 보여 줍니다 (Figma 완성본과 같은 순서)
val DockLabels = listOf("Phone", "Messages", "Chrome", "Camera")

// dock 은 영어로 "배를 대는 곳". 홈 화면 맨 아래 고정 줄을 앱 업계에서 이렇게 부릅니다
fun dockApps(apps: List<AppInfo>): List<AppInfo> =
    // 이름 하나씩 돌면서 설치된 목록에서 같은 이름의 첫 앱을 찾습니다
    // firstOrNull: 찾으면 그 앱, 없으면 null.  mapNotNull: null 은 빼고 모읍니다 -> 기기에 없는 앱은 빠짐
    DockLabels.mapNotNull { label -> apps.firstOrNull { it.label == label } }
