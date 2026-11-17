package com.example.minilauncher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.model.FakeApps
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 앱의 진짜 아이콘을 그립니다. 설치되지 않은 앱이거나 Preview 처럼 아이콘을 못 가져오면 첫 글자로 대신합니다
@Composable
fun AppIconImage(app: AppInfo, size: Dp = 56.dp) {
    val context = LocalContext.current
    val bitmap = remember(app.packageName) {
        runCatching { context.packageManager.getApplicationIcon(app.packageName).toBitmap().asImageBitmap() }.getOrNull()
    }
    if (bitmap != null) {
        Image(bitmap, contentDescription = null, modifier = Modifier.size(size))
    } else {
        Box(
            Modifier.size(size).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(app.label.take(1), fontSize = (size.value * 0.4f).sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// 아이콘 56dp 와 이름 한 줄. 선택되면 뒤에 옅은 회색 판이 깔리고 이름이 굵어집니다
@Composable
fun AppIcon(
    app: AppInfo,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .background(
                if (selected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background,
                RoundedCornerShape(16.dp),
            )
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        AppIconImage(app)
        Text(
            app.label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF1F3F4)
@Composable
private fun AppIconPreview() {
    MiniLauncherTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            AppIcon(FakeApps[2], selected = false, onClick = {})
            AppIcon(FakeApps[2], selected = true, onClick = {})
        }
    }
}
