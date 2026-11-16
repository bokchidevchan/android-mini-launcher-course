package com.example.minilauncher.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 연습 문제: Compose 6개.
// 이 파일을 열고 Split 을 누르면 오른쪽에 Preview 가 보입니다.
// Preview 이름 오른쪽 ⋮ > Start Interactive Mode 로 눌러 보며 확인합니다.
// 정답은 이 파일 아래쪽 "정답" 밑에 있습니다. 먼저 풀어 보고 봅니다.

private val practiceNames = listOf("Calendar", "Camera", "Chrome", "Maps", "Phone", "Settings")

// 문제 C1. 누를 때마다 숫자가 1씩 늘어나는 좋아요 버튼으로 고칩니다.
// (힌트: var count by remember { mutableIntStateOf(0) })
@Composable
fun LikeButton() {
    val count = 0                                  // TODO: 값을 기억하는 상태로 바꿉니다
    Button(onClick = { /* TODO: 1 늘립니다 */ }) {
        Text("좋아요 $count")
    }
}

// 문제 C2. 버튼 두 개가 같은 숫자를 보여 주고, 어느 쪽을 눌러도 같이 늘어나게 고칩니다.
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

// 문제 C3. 입력한 글자가 아래 줄에 그대로 보이게 고칩니다.
// (힌트: var name by remember { mutableStateOf("") }, onValueChange = { name = it })
@Composable
fun EchoField() {
    val name = ""                                  // TODO: 상태로 바꿉니다
    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { /* TODO */ }, label = { Text("이름") })
        Text("입력한 글자: $name")
    }
}

// 문제 C4. 스위치를 켜면 "Wi-Fi 켜짐", 끄면 "Wi-Fi 꺼짐" 이 보이게 고칩니다.
// (힌트: var on by remember { mutableStateOf(false) }, onCheckedChange = { on = it })
@Composable
fun WifiSwitch() {
    val on = false                                 // TODO: 상태로 바꿉니다
    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = on, onCheckedChange = { /* TODO */ })
        Text("Wi-Fi 꺼짐", Modifier.padding(start = 12.dp))   // TODO: on 에 따라 글자를 바꿉니다
    }
}

// 문제 C5. practiceNames 의 이름을 한 줄에 하나씩 보여 줍니다.
// (힌트: LazyColumn { items(practiceNames) { name -> Text(name) } })
@Composable
fun NameList() {
    Text("TODO: 여기에 목록을 만듭니다", Modifier.padding(16.dp))
}

// 문제 C6. 이름을 누르면 그 줄의 배경이 회색으로 바뀌게 고칩니다. 다른 줄을 누르면 그 줄로 옮겨 갑니다.
// (힌트: var selected by remember { mutableStateOf<String?>(null) }, Modifier.clickable { selected = name })
@Composable
fun SelectableList() {
    val selected: String? = null                   // TODO: 상태로 바꿉니다
    LazyColumn(Modifier.height(200.dp)) {
        items(practiceNames) { name ->
            Text(
                name,
                Modifier
                    .fillMaxWidth()
                    .background(if (name == selected) Color(0xFFE0E0E0) else Color.Transparent)
                    .padding(12.dp),               // TODO: 누르면 selected 를 바꿉니다
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, name = "C1 좋아요 버튼")
@Composable
private fun C1Preview() { MiniLauncherTheme { Column(Modifier.padding(16.dp)) { LikeButton() } } }

@Preview(showBackground = true, widthDp = 360, name = "C2 같은 숫자를 보여 주는 버튼 두 개")
@Composable
private fun C2Preview() { MiniLauncherTheme { LikeRow() } }

@Preview(showBackground = true, widthDp = 360, name = "C3 입력한 글자 보여 주기")
@Composable
private fun C3Preview() { MiniLauncherTheme { EchoField() } }

@Preview(showBackground = true, widthDp = 360, name = "C4 스위치")
@Composable
private fun C4Preview() { MiniLauncherTheme { WifiSwitch() } }

@Preview(showBackground = true, widthDp = 360, name = "C5 이름 목록")
@Composable
private fun C5Preview() { MiniLauncherTheme { NameList() } }

@Preview(showBackground = true, widthDp = 360, name = "C6 눌러서 고르는 목록")
@Composable
private fun C6Preview() { MiniLauncherTheme { SelectableList() } }

// ================================================================ 정답
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

@Composable
fun EchoFieldAnswer() {
    var name by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("이름") })
        Text("입력한 글자: $name")
    }
}

@Composable
fun WifiSwitchAnswer() {
    var on by remember { mutableStateOf(false) }
    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = on, onCheckedChange = { on = it })
        Text(if (on) "Wi-Fi 켜짐" else "Wi-Fi 꺼짐", Modifier.padding(start = 12.dp))
    }
}

@Composable
fun NameListAnswer() {
    LazyColumn(Modifier.height(200.dp)) {
        items(practiceNames) { name ->
            Text(name, Modifier.padding(12.dp))
        }
    }
}

@Composable
fun SelectableListAnswer() {
    var selected by remember { mutableStateOf<String?>(null) }
    LazyColumn(Modifier.height(200.dp)) {
        items(practiceNames) { name ->
            Text(
                name,
                Modifier
                    .fillMaxWidth()
                    .clickable { selected = name }
                    .background(if (name == selected) Color(0xFFE0E0E0) else Color.Transparent)
                    .padding(12.dp),
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, name = "정답 C1 ~ C4")
@Composable
private fun AnswerPreview1() {
    MiniLauncherTheme { Column(Modifier.padding(16.dp)) { LikeButtonAnswer(); LikeRowAnswer(); EchoFieldAnswer(); WifiSwitchAnswer() } }
}

@Preview(showBackground = true, widthDp = 360, name = "정답 C5, C6")
@Composable
private fun AnswerPreview2() {
    MiniLauncherTheme { Column { NameListAnswer(); SelectableListAnswer() } }
}
