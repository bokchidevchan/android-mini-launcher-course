package com.example.minilauncher.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 13:00 Compose 화면 조각 10개.
// 오른쪽 위 Split 을 누르면 아래 Preview 10개가 차례로 보입니다. (처음엔 Build & Refresh)
// 버튼이나 입력창은 Preview 이름 오른쪽 ⋮ 메뉴 > Start Interactive Mode 로 눌러 봅니다.
// 예제마다 끝에 "바꿔 보기"가 있습니다. 값을 바꾸면 Preview 가 바로 바뀝니다.

// 1. 글자
// Text 는 글자 하나를 그립니다. 크기는 sp, 굵기와 색은 인자로 정합니다
@Composable
fun Example01Text() {
    Column(Modifier.padding(16.dp)) {
        Text("보통 글자")
        Text("큰 글자", fontSize = 28.sp)
        Text("굵은 글자", fontWeight = FontWeight.Bold)
        Text("색이 있는 글자", color = Color(0xFF1565C0))
        Text("테마가 정해 둔 모양", style = MaterialTheme.typography.titleLarge)
    }
    // 바꿔 보기: 28.sp 를 40.sp 로, 색 0xFF1565C0 을 0xFFC62828 로 바꿔 봅니다
}

// 2. 세로와 가로로 놓기
// Column 은 위에서 아래로, Row 는 왼쪽에서 오른쪽으로 놓습니다
@Composable
fun Example02ColumnRow() {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Column 안의 첫 줄")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Row 왼쪽")
            Text("Row 가운데")
            Text("Row 오른쪽")
        }
        Text("Column 안의 마지막 줄")
    }
    // 바꿔 보기: spacedBy(12.dp) 를 spacedBy(40.dp) 로, Row 를 Column 으로 바꿔 봅니다
}

// 3. 겹쳐 놓기
// Box 는 안의 것을 겹쳐 놓습니다. 동그란 배경 위에 글자를 올리면 앱 아이콘 자리가 됩니다
@Composable
fun Example03Box() {
    Box(
        modifier = Modifier.padding(16.dp).size(72.dp).clip(CircleShape).background(Color(0xFFDDE4F7)),
        contentAlignment = Alignment.Center,
    ) {
        Text("C", fontSize = 28.sp, fontWeight = FontWeight.Bold)
    }
    // 바꿔 보기: CircleShape 를 RoundedCornerShape(16.dp) 로, 72.dp 를 120.dp 로 바꿔 봅니다
}

// 4. Modifier 는 적은 순서대로 적용됩니다
// 왼쪽은 여백을 먼저 주고 배경을 칠하고, 오른쪽은 배경을 먼저 칠하고 여백을 줍니다
@Composable
fun Example04ModifierOrder() {
    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("여백 다음 배경", Modifier.padding(16.dp).background(Color(0xFFFFE082)))
        Text("배경 다음 여백", Modifier.background(Color(0xFFFFE082)).padding(16.dp))
    }
    // 바꿔 보기: 두 Modifier 의 padding 값을 4.dp, 32.dp 로 바꿔 차이를 봅니다
}

// 5. 버튼과 상태
// remember { mutableIntStateOf(0) } 로 만든 값이 바뀌면 이 함수가 다시 실행되어 화면이 바뀝니다
@Composable
fun Example05Button() {
    var count by remember { mutableIntStateOf(0) }
    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = { count++ }) { Text("+1") }
        OutlinedButton(onClick = { count = 0 }) { Text("처음으로") }
        Text("지금 $count", fontSize = 20.sp)
    }
    // 바꿔 보기: count++ 를 count += 10 으로 바꿔 봅니다. remember { } 를 빼고 mutableIntStateOf(0) 만 남기면
    // 빨간 줄이 생기고, 눌러도 0 에서 안 바뀝니다. 다시 그릴 때마다 0 으로 새로 만들기 때문입니다
}

// 6. 글자 입력
// 입력창은 값을 스스로 기억하지 않습니다. 내가 상태로 들고 있다가 다시 넣어 줍니다
@Composable
fun Example06TextField() {
    var name by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("이름") }, singleLine = true)
        Text(if (name.isBlank()) "이름을 넣어 주세요" else "안녕하세요, $name 님")
    }
    // 바꿔 보기: onValueChange = { name = it } 를 onValueChange = { } 로 바꾸면 입력이 어떻게 될까요?
}

// 7. 스위치와 체크박스
// 켜짐, 꺼짐도 상태입니다. 상태에 따라 글자와 색을 다르게 그립니다
@Composable
fun Example07Switch() {
    var wifi by remember { mutableStateOf(true) }
    var agree by remember { mutableStateOf(false) }
    Column(Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Switch(checked = wifi, onCheckedChange = { wifi = it })
            Text(if (wifi) "Wi-Fi 켜짐" else "오프라인", color = if (wifi) Color.Unspecified else Color(0xFFC62828))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = agree, onCheckedChange = { agree = it })
            Text(if (agree) "확인했습니다" else "확인해 주세요")
        }
    }
    // 바꿔 보기: mutableStateOf(true) 를 false 로 바꾸면 처음 화면이 어떻게 될까요?
}

// 8. 카드와 아이콘
// Card 는 내용을 둥근 판 위에 올립니다. Icon 은 Material 아이콘을 이름으로 가져옵니다
@Composable
fun Example08Card() {
    Card(Modifier.padding(16.dp).fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Settings, contentDescription = "설정")
            Spacer(Modifier.width(12.dp))
            Column {
                Text("Settings", fontWeight = FontWeight.Bold)
                Text("com.android.settings", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
    // 바꿔 보기: Icons.Outlined.Settings 를 지우고 Icons.Outlined. 까지만 치면 쓸 수 있는 아이콘 목록이 나옵니다
}

// 9. 긴 목록
// LazyColumn 은 화면에 보이는 줄만 만듭니다. 줄이 천 개여도 느려지지 않습니다
@Composable
fun Example09List() {
    val labels = List(30) { "앱 ${it + 1}" }
    LazyColumn(Modifier.height(220.dp).fillMaxWidth()) {
        items(labels) { label ->
            Text(label, Modifier.padding(horizontal = 16.dp, vertical = 10.dp))
            HorizontalDivider()
        }
    }
    // 바꿔 보기: List(30) 을 List(1000) 으로 바꾸고 Interactive Mode 에서 끝까지 밀어 봅니다
}

// 10. 격자와 선택
// 런처의 아이콘 격자 축소판입니다. 누른 칸을 상태로 기억해서 색을 바꿉니다
@Composable
fun Example10Grid() {
    val labels = listOf("Calendar", "Camera", "Chrome", "Clock", "Maps", "Phone", "Photos", "Settings")
    var selected by remember { mutableStateOf<String?>(null) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 88.dp),
        modifier = Modifier.height(240.dp).padding(8.dp),
    ) {
        items(labels) { label ->
            val isSelected = label == selected
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) Color(0xFFE0E0E0) else Color.Transparent)
                    .clickable { selected = label }
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFDDE4F7)), contentAlignment = Alignment.Center) {
                    Text(label.take(1), fontWeight = FontWeight.Bold)
                }
                Text(label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
            }
        }
    }
    // 바꿔 보기: 88.dp 를 120.dp 로 바꾸면 한 줄에 몇 칸이 될까요? 다음 실습의 ui/AppGrid.kt 가 이 모양입니다
}

// 11. 상태 끌어올리기 (state hoisting)
// 5번은 상태를 버튼 옆에 두었습니다. 여기서는 상태를 부모가 가집니다.
// 값은 아래로 내려 주고, 눌렸다는 사실은 람다(onClick)로 위에 알립니다. 그래서 버튼 두 개가 같은 값을 바꿉니다
@Composable
fun Example11Hoisting() {
    var count by remember { mutableIntStateOf(0) }       // 상태는 부모 한 곳에만 있습니다
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("부모가 가진 값: $count", fontSize = 20.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PlusButton(label = "왼쪽 +1", onClick = { count++ })
            PlusButton(label = "오른쪽 +1", onClick = { count++ })
        }
    }
    // 바꿔 보기: 오른쪽 버튼의 count++ 를 count = 0 으로 바꿔 봅니다. PlusButton 은 고치지 않아도 됩니다
}

// 상태가 없는 화면 조각. 받은 글자를 그리고, 눌리면 알리기만 합니다
@Composable
private fun PlusButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick) { Text(label) }
}

// 예제 10개를 한 화면에 모아 봅니다
@Composable
fun ComposePracticeGallery() {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        Example01Text()
        Example02ColumnRow()
        Example03Box()
        Example04ModifierOrder()
        Example05Button()
        Example06TextField()
        Example07Switch()
        Example08Card()
        Example09List()
        Example10Grid()
    }
}

@Preview(showBackground = true, widthDp = 360, name = "01 글자")
@Composable
private fun Example01Preview() { MiniLauncherTheme { Example01Text() } }

@Preview(showBackground = true, widthDp = 360, name = "02 세로와 가로")
@Composable
private fun Example02Preview() { MiniLauncherTheme { Example02ColumnRow() } }

@Preview(showBackground = true, widthDp = 360, name = "03 겹쳐 놓기")
@Composable
private fun Example03Preview() { MiniLauncherTheme { Example03Box() } }

@Preview(showBackground = true, widthDp = 360, name = "04 Modifier 순서")
@Composable
private fun Example04Preview() { MiniLauncherTheme { Example04ModifierOrder() } }

@Preview(showBackground = true, widthDp = 360, name = "05 버튼과 상태")
@Composable
private fun Example05Preview() { MiniLauncherTheme { Example05Button() } }

@Preview(showBackground = true, widthDp = 360, name = "06 글자 입력")
@Composable
private fun Example06Preview() { MiniLauncherTheme { Example06TextField() } }

@Preview(showBackground = true, widthDp = 360, name = "07 스위치와 체크박스")
@Composable
private fun Example07Preview() { MiniLauncherTheme { Example07Switch() } }

@Preview(showBackground = true, widthDp = 360, name = "08 카드와 아이콘")
@Composable
private fun Example08Preview() { MiniLauncherTheme { Example08Card() } }

@Preview(showBackground = true, widthDp = 360, name = "09 긴 목록")
@Composable
private fun Example09Preview() { MiniLauncherTheme { Example09List() } }

@Preview(showBackground = true, widthDp = 360, name = "10 격자와 선택")
@Composable
private fun Example10Preview() { MiniLauncherTheme { Example10Grid() } }

@Preview(showBackground = true, widthDp = 360, name = "11 상태 끌어올리기")
@Composable
private fun Example11Preview() { MiniLauncherTheme { Example11Hoisting() } }
