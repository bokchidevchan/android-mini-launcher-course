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

// 2일차 오전 과제 [4]: 멈추는 화면 고치기 (뼈대).
// 아래 "[4] 여기를 고칩니다" 자리를 고칩니다. 무엇을 고치는지는 README.md 에 있습니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "멈춤 고치기" 아이콘을 누릅니다.

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
            // [4] 여기를 고칩니다. 지금은 메인 스레드에서 3초를 붙잡습니다
            result = slowWork()
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
