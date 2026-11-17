package com.example.minilauncher.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 오후 Compose 첫 실습: 카운터 (Figma "Compose 카운터")
//
// Compose 는 화면을 Kotlin 함수로 만드는 방법입니다. @Composable 이 붙은 함수를 부르면 그 자리에 글자나 버튼이 놓입니다.
// 여기서 볼 것은 하나입니다. 숫자(count)만 바꾸면 화면의 글자가 알아서 따라 바뀝니다.
// 엘리베이터의 층 표시와 같습니다. 엘리베이터가 움직이면 누가 숫자판을 고쳐 쓰지 않아도 숫자가 바뀝니다.
// 오전의 XML 방식처럼 textView.text = ... 로 글자를 직접 고치는 줄이 이 파일에는 없습니다
//
// ===== 보여 주기 (앞 화면을 보며 따라옵니다) =====
//   1. 오른쪽 위 Split 을 눌러 Preview 를 엽니다. 안 보이면 Build & Refresh 를 한 번 합니다
//   2. Preview 이름 오른쪽 ⋮ 메뉴 > Start Interactive Mode 로 +1 을 눌러 숫자가 오르는 것을 봅니다
//   3. count++ 를 count += 10 으로 바꾸면 Preview 가 바로 바뀌는 것을 봅니다
//
// ===== 같이 해 보기 (한 덩어리씩 복사합니다) =====
//   같은 폴더의 ComposeBasics_My.kt 를 엽니다. 아래 [1]~[4] 표시가 붙은 줄을 차례로 가져가
//   빈 함수 MyCounter 안에 붙이고, 붙일 때마다 Preview 가 어떻게 바뀌는지 봅니다
@Composable
fun Counter() {
    var count by remember { mutableIntStateOf(0) }                // [3] 상태: 화면이 기억하는 값
    Column(                                                       // [2] 세로로 쌓고 가운데에 놓기
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("눌린 횟수", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("$count", fontSize = 64.sp, fontWeight = FontWeight.Bold)       // [1] 글자 하나
        Button(onClick = { count++ }) { Text("+1") }                  // [4] 버튼이 상태를 바꿈
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 500)
@Composable
private fun CounterPreview() {
    MiniLauncherTheme { Counter() }
}
