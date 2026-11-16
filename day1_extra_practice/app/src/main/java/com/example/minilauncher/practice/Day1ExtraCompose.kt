package com.example.minilauncher.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 시간이 남으면: Compose 문제 2개.
// 이 파일을 열고 Split 을 누르면 오른쪽에 Preview 가 보입니다.
// Preview 이름 오른쪽 ⋮ > Start Interactive Mode 로 버튼을 눌러 확인합니다.
// 정답은 이 파일 맨 아래에 있습니다.

// 문제 6. 누를 때마다 숫자가 1씩 늘어나는 좋아요 버튼으로 고칩니다.
// (힌트: var count by remember { mutableIntStateOf(0) })
@Composable
fun LikeButton() {
    val count = 0                                  // TODO: 값을 기억하는 상태로 바꿉니다
    Button(onClick = { /* TODO: 1 늘립니다 */ }) {
        Text("좋아요 $count")
    }
}

// 문제 7. 버튼 두 개가 같은 숫자를 보여 주고, 어느 쪽을 눌러도 같이 늘어나게 고칩니다.
// (힌트: 상태를 LikeRow 가 갖고, SharedLikeButton 에는 값과 onClick 을 넘깁니다)
@Composable
fun LikeRow() {
    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        LikeButton()                               // TODO: SharedLikeButton 으로 바꿉니다
        LikeButton()
    }
}

@Composable
fun SharedLikeButton(count: Int, onClick: () -> Unit) {
    Button(onClick = onClick) { Text("좋아요 $count") }
}

@Preview(showBackground = true, widthDp = 360, name = "문제 6 좋아요 버튼")
@Composable
private fun LikeButtonPreview() { MiniLauncherTheme { Column(Modifier.padding(16.dp)) { LikeButton() } } }

@Preview(showBackground = true, widthDp = 360, name = "문제 7 같은 숫자를 보여 주는 버튼 두 개")
@Composable
private fun LikeRowPreview() { MiniLauncherTheme { LikeRow() } }

// ---------------------------------------------------------------- 정답
@Composable
fun LikeButtonAnswer() {
    var count by remember { mutableIntStateOf(0) }
    Button(onClick = { count++ }) {
        Text("좋아요 $count")
    }
}

@Composable
fun LikeRowAnswer() {
    var count by remember { mutableIntStateOf(0) }     // 상태는 부모 한 곳에만
    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        SharedLikeButton(count = count, onClick = { count++ })
        SharedLikeButton(count = count, onClick = { count++ })
    }
}

@Preview(showBackground = true, widthDp = 360, name = "정답 6, 7")
@Composable
private fun AnswerPreview() { MiniLauncherTheme { Column(Modifier.padding(16.dp)) { LikeButtonAnswer(); LikeRowAnswer() } } }
