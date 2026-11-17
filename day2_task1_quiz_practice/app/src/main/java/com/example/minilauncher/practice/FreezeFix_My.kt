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

// 같이 해 보기 파일입니다. 멈추는 화면을 직접 고쳐 봅니다. 완성된 모양은 lab/LabActivity.kt 의 두 번째 버튼입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "멈춤 고치기" 아이콘을 누릅니다.
//
// 먼저 겪어 보기
//   "3초 걸리는 일"을 누르고 바로 "눌러 보기"를 여러 번 누릅니다. 3초 동안 숫자가 안 오릅니다. 메인 스레드가 붙잡혔기 때문입니다.
//
// 고치기 (아래 [1] 자리의 한 줄을 바꿉니다)
//   [1] result = slowWork() 를 지우고 아래 네 줄을 붙입니다
//         result = "일하는 중"
//         scope.launch {
//             result = withContext(Dispatchers.Default) { slowWork() }     // 오래 걸리는 일만 뒤쪽 스레드로
//         }
//   다시 Run 해서 "3초 걸리는 일"을 누른 뒤 "눌러 보기"를 누릅니다. 이번에는 숫자가 바로 오릅니다.
//
// 더 해 보기
//   [2] Thread.sleep(3000) 을 10000 으로 늘려도 화면이 살아 있는지 봅니다

// 오래 걸리는 일 (예: 큰 파일 읽기). 일부러 3초 동안 스레드를 붙잡습니다
private fun slowWork(): String {
    Thread.sleep(3000)
    return "끝났습니다"
}

@Composable
fun FreezeFixScreen() {
    var result by remember { mutableStateOf("버튼을 눌러 보세요") }
    var taps by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("멈춤 고치기", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Button(onClick = {
            // [1] 여기를 고칩니다. 지금은 메인 스레드에서 3초를 붙잡습니다
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
