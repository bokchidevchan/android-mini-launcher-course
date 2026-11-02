package com.example.minilauncher.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 13:00 Compose 첫 실습: 상태가 바뀌면 화면이 다시 그려진다 (Figma "Compose 카운터")
// Preview 가 안 보이면 오른쪽 위 Split 을 누르고 Build & Refresh 를 한 번 합니다.
// Preview 이름 오른쪽 ⋮ 메뉴 > Start Interactive Mode 를 고르면 버튼을 눌러 볼 수 있습니다
@Composable
fun Counter() {
    var count by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("눌린 횟수", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("$count", fontSize = 64.sp, fontWeight = FontWeight.Bold)
        Button(onClick = { count++ }) { Text("+1") }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 500)
@Composable
private fun CounterPreview() {
    MiniLauncherTheme { Counter() }
}
