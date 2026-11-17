package com.example.minilauncher.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.theme.LauncherBackground

// 3일차 오전 과제 "즐겨찾기 서랍" 에서 고치지 않고 그대로 쓰는 화면입니다.
// 받은 값(apps, favorites)을 그리기만 하고, 별을 누르면 onToggle, 앱을 누르면 onOpen 으로 알립니다.

@Composable
fun FavoriteAppsLayout(
    apps: List<AppInfo>,                // 설치된 앱 전체
    favorites: List<String>,            // 즐겨찾기한 앱의 패키지 이름
    onToggle: (AppInfo) -> Unit,        // 별을 누르면 불림
    onOpen: (AppInfo) -> Unit,          // 앱을 누르면 불림
) {
    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(16.dp)) {
        Text("즐겨찾기 서랍", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        // 위쪽: 즐겨찾기한 앱만
        val favoriteApps = apps.filter { it.packageName in favorites }
        if (favoriteApps.isEmpty()) {
            Text("별을 눌러 자주 쓰는 앱을 담아 보세요", modifier = Modifier.height(56.dp))
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                favoriteApps.take(5).forEach { app ->
                    Column(Modifier.clickable { onOpen(app) }) { AppIconImage(app, 56.dp) }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("설치된 앱 ${apps.size}개", fontWeight = FontWeight.Bold)
        HorizontalDivider()

        // 아래쪽: 전체 목록
        LazyColumn {
            items(apps, key = { it.packageName + it.className }) { app ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onOpen(app) }.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppIconImage(app, 40.dp)
                    Spacer(Modifier.width(12.dp))
                    Text(app.label, modifier = Modifier.weight(1f))
                    TextButton(onClick = { onToggle(app) }) {
                        Text(if (app.packageName in favorites) "★" else "☆", fontSize = 22.sp)
                    }
                }
            }
        }
    }
}

// 앱의 진짜 아이콘을 그립니다. 아이콘을 못 가져오면 이름의 첫 글자로 대신합니다
@Composable
fun AppIconImage(app: AppInfo, size: Dp) {
    val context = LocalContext.current
    val bitmap = remember(app.packageName) {
        runCatching { context.packageManager.getApplicationIcon(app.packageName).toBitmap().asImageBitmap() }.getOrNull()
    }
    if (bitmap != null) {
        Image(bitmap, contentDescription = null, modifier = Modifier.size(size))
    } else {
        Box(Modifier.size(size).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape), contentAlignment = Alignment.Center) {
            Text(app.label.take(1), fontSize = (size.value * 0.4f).sp)
        }
    }
}
