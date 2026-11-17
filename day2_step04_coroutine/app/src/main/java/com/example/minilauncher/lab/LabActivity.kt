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

// 2일차 2교시 실험실: race condition (경쟁 상태) 와 Mutex
class LabActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { LabScreen() } }
    }
}

@Composable
fun LabScreen() {
    var result by remember { mutableStateOf("버튼을 눌러 보세요") }
    val scope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("실험실", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Text("race condition (경쟁 상태)", style = MaterialTheme.typography.titleMedium)
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
