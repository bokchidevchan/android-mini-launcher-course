package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 8교시: 같은 "오늘 점심 뭐 먹지?" 를 Compose 로 만듭니다.
// 실행: "점심 뽑기 Compose 연습" 아이콘. 또는 Split 을 눌러 Preview 로 봅니다.
// LunchScreen 안에 직접 만듭니다. 정답은 아래 LunchScreenAnswer ("점심 뽑기 Compose 정답" 아이콘)
class ComposeLunchPracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { LunchScreen() } }
    }
}

class ComposeLunchAnswerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { LunchScreenAnswer() } }
    }
}

@Composable
fun LunchScreen() {
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("오늘 점심 뭐 먹지?", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // 여기부터 직접 만듭니다. 완성 화면과 요구사항은 교안의 1일차 8교시 장에 있습니다.
        // 1. 기억할 값을 상태로 만듭니다.    var menu by remember { mutableStateOf<String?>(null) }
        // 2. 뽑힌 메뉴를 크게 보여 줍니다.   Text(menu ?: "?", fontSize = 44.sp)
        // 3. 버튼을 누르면 값만 바꿉니다.    Button(onClick = { menu = lunchMenus.random() }) { Text("뽑기!") }
        //    XML 과 달리 글자를 직접 고치지 않습니다. 상태만 바꾸면 화면이 따라 바뀝니다
    }
}

@Composable
fun LunchScreenAnswer() {
    var menu by remember { mutableStateOf<String?>(null) }    // 방금 뽑은 메뉴
    var count by remember { mutableIntStateOf(0) }            // 몇 번 뽑았는지
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("오늘 점심 뭐 먹지?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(40.dp))
        Text(menu ?: "?", fontSize = 44.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(if (count == 0) "아직 안 뽑았어요" else "${count}번째 뽑기", fontSize = 16.sp)
        Spacer(Modifier.height(40.dp))
        Button(
            onClick = {
                menu = pickLunchAnswer(lunchMenus, menu)
                count++
            },
            modifier = Modifier.width(200.dp),
        ) { Text("뽑기!") }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(
            onClick = {
                menu = null
                count = 0
            },
            modifier = Modifier.width(200.dp),
        ) { Text("처음부터") }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 520, name = "내가 만드는 점심 뽑기")
@Composable
private fun LunchScreenPreview() { MiniLauncherTheme { LunchScreen() } }

@Preview(showBackground = true, widthDp = 360, heightDp = 520, name = "정답")
@Composable
private fun LunchScreenAnswerPreview() { MiniLauncherTheme { LunchScreenAnswer() } }
