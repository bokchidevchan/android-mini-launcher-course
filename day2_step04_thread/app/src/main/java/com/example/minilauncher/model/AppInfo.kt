package com.example.minilauncher.model

// 런처 화면에 보여 줄 앱 하나. C++ 의 struct 자리에 data class 를 씁니다
data class AppInfo(
    val label: String,
    val packageName: String,
)

// 1~2일차는 고정된 앱 목록으로 화면을 만듭니다 (3일차에 실제 설치 앱 목록으로 바꿉니다).
// 패키지 이름은 에뮬레이터에 깔린 실제 앱과 같아서, 아이콘은 진짜 앱 아이콘이 나옵니다
val FakeApps: List<AppInfo> = listOf(
    AppInfo("Calendar", "com.google.android.calendar"),
    AppInfo("Camera", "com.android.camera2"),
    AppInfo("Chrome", "com.android.chrome"),
    AppInfo("Clock", "com.google.android.deskclock"),
    AppInfo("Contacts", "com.google.android.contacts"),
    AppInfo("Drive", "com.google.android.apps.docs"),
    AppInfo("Files", "com.google.android.documentsui"),
    AppInfo("Gmail", "com.google.android.gm"),
    AppInfo("Google", "com.google.android.googlequicksearchbox"),
    AppInfo("Maps", "com.google.android.apps.maps"),
    AppInfo("Messages", "com.google.android.apps.messaging"),
    AppInfo("Phone", "com.google.android.dialer"),
    AppInfo("Photos", "com.google.android.apps.photos"),
    AppInfo("Settings", "com.android.settings"),
    AppInfo("YouTube", "com.google.android.youtube"),
    AppInfo("YT Music", "com.google.android.apps.youtube.music"),
)
