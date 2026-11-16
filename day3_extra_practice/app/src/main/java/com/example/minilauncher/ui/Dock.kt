package com.example.minilauncher.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.minilauncher.model.AppInfo

// 화면 아래 자주 쓰는 앱 4개. 픽셀 런처처럼 배경 없이 아이콘만 둡니다
@Composable
fun Dock(apps: List<AppInfo>, onAppClick: (AppInfo) -> Unit) {
    if (apps.isEmpty()) return
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        apps.forEach { app ->
            Box(
                Modifier.clip(CircleShape).clickable { onAppClick(app) }
                    .semantics { contentDescription = "도크 ${app.label}" },
            ) {
                AppIconImage(app, size = 56.dp)
            }
        }
    }
}
