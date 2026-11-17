package com.example.minilauncher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 상태 영역 안 동기화 표시 (Figma "동기화 중", "동기화 완료")
@Composable
fun SyncIndicator(progress: Int?) {
    val primary = MaterialTheme.colorScheme.primary
    when {
        progress == null -> Unit
        progress >= 100 -> Text("동기화 완료", style = MaterialTheme.typography.labelMedium, color = primary)
        else -> Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Sync, contentDescription = null, tint = primary, modifier = Modifier.size(16.dp))
            LinearProgressIndicator(progress = { progress / 100f }, modifier = Modifier.width(28.dp))
            Text("$progress%", style = MaterialTheme.typography.labelMedium, color = primary)
        }
    }
}
