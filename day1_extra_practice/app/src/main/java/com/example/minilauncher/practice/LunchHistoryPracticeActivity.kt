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

// 1일차 8교시 둘째 화면: 기록 보기. Compose 로 만듭니다.
// 첫 화면의 "기록 보기" 버튼이 이 화면을 엽니다. Split 을 눌러 Preview 로 모양을 먼저 볼 수 있습니다.
// 정답은 day1_extra_answer 폴더에 있습니다. 먼저 만들어 보고 엽니다
class LunchHistoryPracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 첫 화면이 넘겨준 기록을 꺼냅니다. 못 받았으면 빈 목록
        val history: List<String> = intent.getStringArrayListExtra(EXTRA_HISTORY) ?: emptyList()
        setContent {
            MiniLauncherTheme {
                // finish() 는 이 화면을 닫고 앞 화면으로 돌아갑니다
                LunchHistoryScreen(history = history, onBack = { finish() })
            }
        }
    }
}

@Composable
fun LunchHistoryScreen(history: List<String>, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp)) {
        Text("뽑은 기록", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // 여기부터 직접 만듭니다. 완성 화면과 요구사항은 교안의 1일차 8교시 장에 있습니다.
        // (LazyColumn, itemsIndexed, HorizontalDivider, Button, Spacer 는 위에 import 해 두었습니다)
        // 1. 몇 번 뽑았는지 보여 줍니다.       Text("모두 ${history.size}번")
        // 2. 기록이 비었으면 안내 글자를, 아니면 목록을 보여 줍니다.   if (history.isEmpty()) { ... } else { ... }
        // 3. 목록은 뽑은 순서대로.
        //      LazyColumn(Modifier.weight(1f)) { itemsIndexed(history) { index, menu -> Text("${index + 1}. $menu") } }
        // 4. 돌아가기 버튼을 누르면 onBack() 을 부릅니다.   Button(onClick = onBack) { Text("돌아가기") }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 520, name = "내가 만드는 기록 화면")
@Composable
private fun LunchHistoryPreview() {
    MiniLauncherTheme { LunchHistoryScreen(history = listOf("돈까스", "쌀국수", "돈까스"), onBack = {}) }
}
