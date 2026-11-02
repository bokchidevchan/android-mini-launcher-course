package com.example.minilauncher.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.minilauncher.model.AppInfo

@Composable
fun AppGrid(
    apps: List<AppInfo>,
    selected: String?,
    onAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier,
    onAppLongClick: (AppInfo) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 88.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        modifier = modifier.testTag("AppGrid"),
    ) {
        // 같은 앱에 실행 화면이 여러 개일 수 있어서 key 는 패키지 + 클래스
        items(apps, key = { it.packageName + "/" + it.className }) { app ->
            AppIcon(
                app,
                selected = app.packageName == selected,
                onClick = { onAppClick(app) },
                onLongClick = { onAppLongClick(app) },
            )
        }
    }
}
