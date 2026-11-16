package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

// 2일차 8교시: 반응 속도 게임. 화면이 초록색으로 바뀌는 순간 얼마나 빨리 누르는지 잽니다. (정답)
// 실행: Run 한 뒤 에뮬레이터의 앱 목록에서 "반응 속도 게임 정답" 아이콘.

class ReactionAnswerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { ReactionGameScreen() } }
    }
}

@Composable
fun ReactionGameScreen() {
    // 지금 어떤 화면인지 글자 하나로 기억합니다: "처음", "기다림", "지금", "결과", "너무 빠름"
    var screen by remember { mutableStateOf("처음") }
    var goTime by remember { mutableLongStateOf(0L) }             // 초록색이 된 시각
    var result by remember { mutableLongStateOf(0L) }             // 걸린 시간 (ms)
    var waiting by remember { mutableStateOf<Job?>(null) }        // 초록색을 기다리는 코루틴
    val scope = rememberCoroutineScope()

    // 화면 상태에 따라 색과 글자를 정합니다
    val color = when (screen) {
        "기다림" -> Color(0xFFC62828)      // 빨강
        "지금" -> Color(0xFF2E7D32)        // 초록
        else -> Color(0xFF37474F)
    }
    val text = when (screen) {
        "처음" -> "화면을 누르면 시작"
        "기다림" -> "초록색이 되면 누르세요"
        "지금" -> "지금!"
        "결과" -> "${result}ms"
        else -> "너무 빨랐어요"
    }

    Box(
        modifier = Modifier.fillMaxSize().background(color).clickable {
            // 화면을 누를 때마다 여기가 실행됩니다. 지금 어떤 화면이냐에 따라 할 일이 다릅니다
            when (screen) {
                "기다림" -> {
                    // [2] 너무 일찍 눌렀을 때: 기다리던 코루틴을 취소하고 screen 을 "너무 빠름" 으로 바꿉니다
                    waiting?.cancel()
                    screen = "너무 빠름"
                }
                "지금" -> {
                    // [3] 제때 눌렀을 때: 걸린 시간을 result 에 넣고 screen 을 "결과" 로 바꿉니다
                    result = System.currentTimeMillis() - goTime
                    screen = "결과"
                }
                else -> {
                    // [1] 시작: screen 을 "기다림" 으로 바꾸고, 1~4초 뒤에 "지금" 으로 바꿉니다
                    screen = "기다림"
                    waiting = scope.launch {
                        delay(Random.nextLong(1000, 4000))
                        goTime = System.currentTimeMillis()
                        screen = "지금"
                    }
                }
            }
        },
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            if (screen == "결과" || screen == "너무 빠름") {
                Spacer(Modifier.height(12.dp))
                Text("화면을 누르면 다시 시작", color = Color.White)
            }
        }
    }
}
// 더 해 보기: 가장 빨랐던 기록을 기억해서 화면 아래에 보여 주기
