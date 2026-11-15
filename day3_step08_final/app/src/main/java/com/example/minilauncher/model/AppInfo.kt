package com.example.minilauncher.model

// 런처 화면에 보여 줄 앱 하나. className 은 실제 앱을 실행할 때 씁니다
data class AppInfo(
    val label: String,
    val packageName: String,
    val className: String = "",
)

// 1~2일차에 쓰던 고정 목록. 3일차부터는 Preview 와 테스트에서만 씁니다.
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
