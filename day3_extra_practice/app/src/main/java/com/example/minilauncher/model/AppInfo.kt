package com.example.minilauncher.model

// 설치된 앱 하나. className 은 실제 앱을 실행할 때 씁니다
data class AppInfo(
    val label: String,
    val packageName: String,
    val className: String = "",
)
