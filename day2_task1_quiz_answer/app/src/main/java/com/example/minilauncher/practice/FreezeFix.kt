package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// 2일차 오전 과제 [4]: 멈추는 화면 고치기 (완성).
// Run 한 뒤 에뮬레이터의 앱 목록에서 "멈춤 고치기 (완성)" 아이콘을 누릅니다.
// "3초 걸리는 일"을 눌러도 화면이 멈추지 않습니다. 그동안 "눌러 보기"를 누르면 숫자가 바로 오릅니다.
// 오래 걸리는 일만 워커 스레드에서 하고, 결과만 받아서 화면에 넣기 때문입니다.

// 오래 걸리는 일 (예: 큰 파일 읽기). 일부러 3초 동안 스레드를 붙잡습니다
private fun slowWork(): String {
    Thread.sleep(3000)
    return "끝났습니다"
}

@Composable
fun FreezeFixScreen() {
    var result by remember { mutableStateOf("버튼을 눌러 보세요") }
    var taps by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()        // 이 화면에서 코루틴을 시작하는 곳. 화면이 사라지면 같이 취소됨

    Column(
        Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("멈춤 고치기", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Button(onClick = {
            // [4] 버튼을 누르면 먼저 글자를 바꾸고, 오래 걸리는 일은 코루틴에 맡깁니다
            result = "일하는 중"
            scope.launch {
                result = withContext(Dispatchers.Default) { slowWork() }   // 오래 걸리는 일만 워커 스레드로
            }
        }, modifier = Modifier.fillMaxWidth()) { Text("3초 걸리는 일") }
        FilledTonalButton(onClick = { taps++ }, modifier = Modifier.fillMaxWidth()) { Text("눌러 보기: $taps") }
        Text(result, style = MaterialTheme.typography.titleLarge)
    }
}

class FreezeFixActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { FreezeFixScreen() } }
    }
}
