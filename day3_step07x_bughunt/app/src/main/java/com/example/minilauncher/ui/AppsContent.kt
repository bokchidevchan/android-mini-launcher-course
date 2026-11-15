package com.example.minilauncher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.AppsState
import com.example.minilauncher.model.AppInfo

@Composable
fun AppsContent(
    state: AppsState,
    selected: String?,
    onAppClick: (AppInfo) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    onAppLongClick: (AppInfo) -> Unit = {},
) {
    when (state) {
        AppsState.Loading -> CenterColumn(modifier) {
            CircularProgressIndicator(Modifier.size(40.dp))
            Text("앱 목록 불러오는 중", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        is AppsState.Error -> CenterColumn(modifier) {
            Text("앱 목록을 불러오지 못했습니다", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(state.message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Button(onClick = onRetry) { Text("다시 시도") }
        }
        is AppsState.Success -> AppGrid(state.apps, selected, onAppClick, modifier, onAppLongClick)
    }
}

@Composable
private fun CenterColumn(modifier: Modifier, content: @Composable () -> Unit) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) { content() }
}
