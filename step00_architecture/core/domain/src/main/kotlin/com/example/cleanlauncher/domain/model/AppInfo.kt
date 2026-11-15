package com.example.cleanlauncher.domain.model

// 런처에 보여 줄 앱 하나
data class AppInfo(
    val label: String,
    val packageName: String,
    val className: String,
)
