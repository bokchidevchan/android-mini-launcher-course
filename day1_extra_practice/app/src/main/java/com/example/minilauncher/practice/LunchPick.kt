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

// 1일차 8교시: 오늘 점심 뭐 먹지? 버튼을 누르면 점심 메뉴를 하나 뽑아 줍니다.
// 실행: Run 한 뒤 에뮬레이터의 앱 목록에서 "점심 뽑기 연습" 아이콘.
// 파일은 이것 하나입니다. 화면 모양은 만들어져 있고, [1] [2] [3] 세 군데만 채우면 됩니다.
// 위의 import 가운데 회색으로 보이는 것은 아직 안 써서 그런 것이니 그대로 둡니다.

val lunchMenus = listOf("김치찌개", "돈까스", "쌀국수", "비빔밥", "짜장면", "초밥", "제육볶음", "샌드위치")

class LunchPickPracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { LunchPickScreen() } }
    }
}

@Composable
fun LunchPickScreen() {
    var menu by remember { mutableStateOf("?") }                      // 지금 뽑힌 메뉴
    var history by remember { mutableStateOf(listOf<String>()) }      // 지금까지 뽑은 메뉴들

    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("오늘 점심 뭐 먹지?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        Text(menu, fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Text("${history.size}번 뽑음")
        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            // [1] 뽑기: 메뉴 목록에서 아무거나 하나 골라 menu 에 넣고, history 뒤에 붙입니다
            //     힌트: menu = lunchMenus.random()      history = history + menu
        }, modifier = Modifier.width(200.dp)) { Text("뽑기!") }
        OutlinedButton(onClick = {
            // [2] 처음부터: menu 를 "?" 로, history 를 빈 목록으로 되돌립니다
            //     힌트: history = emptyList()
        }, modifier = Modifier.width(200.dp)) { Text("처음부터") }
        Spacer(Modifier.height(24.dp))
        Text("뽑은 기록", fontWeight = FontWeight.Bold)
        // [3] 기록 보여 주기: history 에 든 것을 한 줄씩 글자로 그립니다
        //     힌트: history.forEachIndexed { index, item -> Text("${index + 1}. $item") }
    }
}
// 더 해 보기: 같은 메뉴가 연달아 나오지 않게 하기. 기록을 최근 5개만 보여 주기 (history.takeLast(5))
