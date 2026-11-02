package com.example.minilauncher.lab

import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

private const val TAG = "Coroutine"

// 2일차 09:00 실험실: ANR 재현과 해결, race condition
class LabActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { LabScreen() } }
    }
}

// 시간이 오래 걸리는 일 (예: 큰 파일 읽기). 일부러 스레드를 붙잡습니다
private fun heavyWork(): String {
    Log.d(TAG, "heavyWork 시작, 스레드=${Thread.currentThread().name}")
    Thread.sleep(20_000)
    return "끝 (${Thread.currentThread().name})"
}

@Composable
fun LabScreen() {
    var result by remember { mutableStateOf("버튼을 눌러 보세요") }
    var running by remember { mutableStateOf(false) }
    var taps by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("실험실", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Text("1. 메인 스레드와 ANR", style = MaterialTheme.typography.titleMedium)
        Button(onClick = {
            // 잘못된 예: 메인 스레드에서 20초 멈춤. 그동안 다른 곳을 누르면 Android 16 은 5초, Android 17 은 15초 뒤 ANR 로 기록됩니다
            result = heavyWork()
        }, Modifier.fillMaxWidth()) { Text("메인 스레드에서 20초 작업 (ANR)") }

        Button(onClick = {
            running = true
            scope.launch {
                // 고친 예: 무거운 일은 Dispatchers.Default(작업용 스레드)로 보내고, 결과만 메인에서 받습니다
                result = withContext(Dispatchers.Default) { heavyWork() }
                running = false
            }
        }, Modifier.fillMaxWidth()) { Text("코루틴으로 20초 작업 (안 멈춤)") }

        FilledTonalButton(onClick = { taps++ }, Modifier.fillMaxWidth()) { Text("화면이 살아 있나 눌러 보기: $taps") }
        if (running) CircularProgressIndicator()

        Text("2. race condition", style = MaterialTheme.typography.titleMedium)
        FilledTonalButton(onClick = {
            scope.launch {
                val n = RaceCounter.unsafe(Dispatchers.Default)
                result = "Mutex 없음: $n / ${RaceCounter.TIMES * 2}"
                Log.d(TAG, result)
            }
        }, Modifier.fillMaxWidth()) { Text("코루틴 2개가 동시에 +1 (Mutex 없음)") }
        FilledTonalButton(onClick = {
            scope.launch {
                val n = RaceCounter.withMutex(Dispatchers.Default)
                result = "Mutex 사용: $n / ${RaceCounter.TIMES * 2}"
                Log.d(TAG, result)
            }
        }, Modifier.fillMaxWidth()) { Text("Mutex 로 고친 버전") }

        Text("결과", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(result, style = MaterialTheme.typography.titleMedium)
    }
}
