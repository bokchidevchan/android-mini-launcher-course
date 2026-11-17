package com.example.minilauncher.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 비교 코드에서 같이 쓰는 화면 모양. 상태를 갖지 않고 받은 값만 그립니다.
// MVVM(practice/mvvm)이든 MVI(practice/mvi)든 이 함수는 똑같이 씁니다. 두 방식의 차이는 화면 모양이 아니라 ViewModel 쪽에 있습니다
@Composable
fun LikePanel(title: String, count: Int, message: String, onPlus: () -> Unit, onReset: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text("$count", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Text(message, fontSize = 16.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onPlus) { Text("+1") }
            OutlinedButton(onClick = onReset) { Text("처음으로") }
        }
    }
}
