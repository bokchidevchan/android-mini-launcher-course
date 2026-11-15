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

// 칸 너비 88dp 기준으로 열 수를 정합니다. 세로 폰은 4열, 가로로 돌리면 자동으로 늘어납니다
@Composable
fun AppGrid(
    apps: List<AppInfo>,
    selected: String?,
    onAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 88.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        modifier = modifier.testTag("AppGrid"),
    ) {
        items(apps, key = { it.packageName }) { app ->
            AppIcon(app, selected = app.packageName == selected, onClick = { onAppClick(app) })
        }
    }
}
