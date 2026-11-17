package com.example.minilauncher.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

// 같이 해 보기 파일입니다. 완성본은 같은 폴더의 ComposeBasics.kt 입니다.
// 아래 [1]부터 차례로 MyCounter 안에 붙이고, 붙일 때마다 오른쪽 Preview 를 봅니다.
// 필요한 import 는 위에 다 있습니다. 회색으로 보이는 것은 아직 안 써서 그렇고, 그대로 두면 됩니다.
//
// [1] 글자 하나. MyCounter 안에 붙입니다
//         Text("0", fontSize = 64.sp, fontWeight = FontWeight.Bold)
//
// [2] 세로로 쌓고 가운데에 놓기. [1]을 지우고 이것으로 바꿉니다
//         Column(
//             modifier = Modifier.fillMaxSize(),
//             verticalArrangement = Arrangement.Center,
//             horizontalAlignment = Alignment.CenterHorizontally,
//         ) {
//             Text("눌린 횟수")
//             Text("0", fontSize = 64.sp, fontWeight = FontWeight.Bold)
//         }
//
// [3] 상태 만들기. MyCounter 의 첫 줄에 붙이고, 숫자 글자의 "0" 을 "$count" 로 바꿉니다
//         var count by remember { mutableIntStateOf(0) }
//
// [4] 버튼. Column 의 { } 안 맨 아래에 붙입니다. Interactive Mode 에서 눌러 봅니다
//         Button(onClick = { count++ }) { Text("+1") }
//
// 다 했으면: 버튼을 하나 더 붙여 0 으로 되돌리는 "처음으로" 버튼을 만들어 봅니다
@Composable
fun MyCounter() {
    // 여기에 붙입니다
    var count by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "눌린 횟수")
        Text(text = "${count}",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold)
        Button(onClick = {
            count++
        }) {
            Text(text = "+1")
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 412,
    heightDp = 500,
    name = "내가 만드는 카운터")
@Composable
private fun MyCounterPreview() {
    MiniLauncherTheme { MyCounter() }
}
