package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 8교시 정답, 둘째 화면: 기록 보기 (Compose).
// Compose 는 화면 모양과 동작이 한 파일에 있고, 받은 값(history)대로 그리기만 합니다
class LunchHistoryAnswerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val history: List<String> = intent.getStringArrayListExtra(EXTRA_HISTORY) ?: emptyList()
        setContent {
            MiniLauncherTheme {
                LunchHistoryAnswerScreen(history = history, onBack = { finish() })
            }
        }
    }
}

@Composable
fun LunchHistoryAnswerScreen(history: List<String>, onBack: () -> Unit) {
    val most = mostPickedAnswer(history)
    Column(Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp)) {
        Text("뽑은 기록", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("모두 ${history.size}번", fontSize = 16.sp)
        if (most != null) {
            Text("가장 많이 나온 메뉴: ${most.first} (${most.second}번)", fontSize = 16.sp)
        }
        Spacer(Modifier.height(16.dp))
        if (history.isEmpty()) {
            Text("아직 뽑은 것이 없어요", Modifier.weight(1f), fontSize = 18.sp)
        } else {
            LazyColumn(Modifier.weight(1f)) {
                itemsIndexed(history) { index, menu ->
                    Text("${index + 1}. $menu", Modifier.padding(vertical = 10.dp), fontSize = 18.sp)
                    HorizontalDivider()
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("돌아가기") }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 520, name = "정답")
@Composable
private fun LunchHistoryAnswerPreview() {
    MiniLauncherTheme { LunchHistoryAnswerScreen(history = listOf("돈까스", "쌀국수", "돈까스"), onBack = {}) }
}
