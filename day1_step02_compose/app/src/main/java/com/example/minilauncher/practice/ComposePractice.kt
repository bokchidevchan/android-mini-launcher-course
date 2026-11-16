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

// 1일차 13:00 Compose 화면 조각 11개.
// 오른쪽 위 Split 을 누르면 아래 Preview 11개가 차례로 보입니다. (처음엔 Build & Refresh)
// 버튼이나 입력창은 Preview 이름 오른쪽 ⋮ 메뉴 > Start Interactive Mode 로 눌러 봅니다.
// 예제마다 끝에 "바꿔 보기"가 있습니다. 값을 바꾸면 Preview 가 바로 바뀝니다.
//
// 진행 방법
//   보여 주기      예제 코드를 앞 화면에서 같이 읽습니다
//   같이 해 보기    예제 끝의 "바꿔 보기"를 직접 해 보고 Preview 가 어떻게 바뀌는지 봅니다
//   혼자 해 보기    ComposePractice_My.kt 에 완성 화면을 보고 직접 만듭니다
//
// 먼저 알아 둘 말
//   @Composable   "이 함수는 화면을 그리는 함수"라는 표시. 값을 돌려주지 않고, 부르면 그 자리에 화면 조각이 놓입니다
//   Modifier      화면 조각에 붙이는 꾸밈. 크기, 여백, 배경, 눌림 같은 것을 점(.)으로 이어 붙입니다
//   dp, sp        크기 단위. dp 는 길이(화면이 촘촘해도 같은 크기로 보임), sp 는 글자 크기(사용자의 글자 크기 설정을 따름)
//   { } 안에 적기  Column { A(); B() } 처럼 중괄호 안에 적은 순서대로 화면에 놓입니다. C++ 의 함수 호출과 모양이 같습니다
//   @Preview      에뮬레이터 없이 Android Studio 안에서 미리 보게 해 주는 표시. 맨 아래에 모아 두었습니다

// 1. 글자
// Text 는 글자 하나를 그립니다. 크기는 sp, 굵기와 색은 인자로 정합니다.
// fontSize = 28.sp 처럼 "이름 = 값"으로 넘기면 순서를 외우지 않아도 됩니다. 안 넘긴 것은 기본값을 씁니다.
// 색 0xFF1565C0 은 앞에서부터 두 자리씩 불투명도(FF), 빨강(15), 초록(65), 파랑(C0) 입니다
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
// Column 은 위에서 아래로, Row 는 왼쪽에서 오른쪽으로 놓습니다. 오전의 LinearLayout(vertical, horizontal) 자리입니다.
// Column 안에 Row 를 넣는 것처럼 겹쳐 쓸 수 있고, 화면은 대부분 이 둘을 겹쳐서 만듭니다.
// Arrangement.spacedBy(8.dp) 는 사이마다 8dp 씩 띄우기, verticalAlignment 는 Row 안에서 위아래 어디에 맞출지입니다
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
// Box 는 안의 것을 겹쳐 놓습니다. 동그란 배경 위에 글자를 올리면 앱 아이콘 자리가 됩니다.
// Modifier 를 왼쪽부터 읽으면: 바깥 여백 16 → 크기 72 → 동그랗게 자르기 → 배경색 칠하기.
// contentAlignment = Alignment.Center 는 안에 든 것을 한가운데에 놓으라는 뜻입니다
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
// 왼쪽은 여백을 먼저 주고 배경을 칠하고, 오른쪽은 배경을 먼저 칠하고 여백을 줍니다.
// 그래서 왼쪽은 글자에 딱 붙은 노란 배경이, 오른쪽은 글자 둘레까지 넓은 노란 배경이 됩니다.
// 선물을 "상자에 넣고 포장지로 싸기"와 "포장지로 싸고 상자에 넣기"가 다른 것과 같습니다
@Composable
fun Example04ModifierOrder() {
    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("여백 다음 배경", Modifier.padding(16.dp).background(Color(0xFFFFE082)))
        Text("배경 다음 여백", Modifier.background(Color(0xFFFFE082)).padding(16.dp))
    }
    // 바꿔 보기: 두 Modifier 의 padding 값을 4.dp, 32.dp 로 바꿔 차이를 봅니다
}

// 5. 버튼과 상태
// "상태"는 화면이 기억해야 하는 값입니다. 여기서는 몇 번 눌렀는지(count)입니다.
// var count by remember { mutableIntStateOf(0) } 를 한 부분씩 읽으면:
//   mutableIntStateOf(0)   값이 바뀌면 Compose 에 "바뀌었어요"라고 알려 주는 상자. 처음 값은 0
//   remember { }           이 함수가 다시 실행돼도 상자를 새로 만들지 않고 기억해 둠
//   by                     상자를 열지 않고 count 라는 이름으로 바로 읽고 쓰게 해 줌
// 버튼을 누르면 생기는 일:
//   1) count 가 1 늘어남  2) Compose 가 count 를 읽는 곳을 다시 실행함  3) Text 가 새 값으로 그려짐
// 이렇게 다시 실행해서 다시 그리는 것을 recomposition(리컴포지션)이라고 부릅니다.
// XML 때처럼 textView.text = ... 로 글자를 직접 고치는 줄이 없다는 점을 봅니다
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
// 입력창은 값을 스스로 기억하지 않습니다. 내가 상태(name)로 들고 있다가 다시 넣어 줍니다.
//   value = name                    지금 보여 줄 글자
//   onValueChange = { name = it }   사용자가 글자를 칠 때마다 불립니다. it 은 새로 바뀐 글자 전체
// 글자를 침 → name 이 바뀜 → 입력창과 아래 인사말이 같이 다시 그려짐. 5번의 버튼과 같은 흐름입니다
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
// 켜짐, 꺼짐도 상태입니다. true, false 를 기억해 두고, 그 값에 따라 글자와 색을 다르게 그립니다.
// Text(if (wifi) "Wi-Fi 켜짐" else "오프라인") 처럼 "값이 이러면 화면은 이렇다"만 적습니다.
// 켜졌을 때 글자를 바꾸는 코드를 따로 쓰지 않는다는 점이 XML 방식과 다릅니다
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
// Card 는 내용을 둥근 판 위에 올립니다. Icon 은 Material 아이콘을 이름으로 가져옵니다.
// 안쪽 구조: Card 안에 Row(가로), 그 안에 아이콘, 빈칸(Spacer), Column(세로로 글자 두 줄).
// contentDescription 은 화면을 소리로 읽어 주는 기능이 읽을 설명입니다
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
// LazyColumn 은 화면에 보이는 줄만 만듭니다. 줄이 천 개여도 느려지지 않습니다.
// items(labels) { label -> ... } 는 "목록의 항목 하나마다 이렇게 그려라"입니다. for 문을 대신합니다.
// 그냥 Column 에 for 로 1000 줄을 넣으면 안 보이는 줄까지 다 만들어서 느려집니다.
// XML 방식으로는 RecyclerView 에 Adapter, ViewHolder, 한 줄 xml 까지 적어야 했습니다 (오전 XmlPracticeActivity.kt 의 6단계).
// 같은 일을 여기서는 LazyColumn 과 items 몇 줄로 합니다
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
// 런처의 아이콘 격자 축소판입니다. 누른 칸의 이름을 상태(selected)로 기억해서 그 칸만 색을 바꿉니다.
//   selected 는 String? 입니다. 아직 아무것도 안 눌렀으면 null
//   clickable { selected = label }   칸을 누르면 그 칸의 이름을 기억
//   val isSelected = label == selected   지금 그리는 칸이 눌린 칸인지
// GridCells.Adaptive(minSize = 88.dp) 는 "한 칸이 적어도 88dp. 화면 너비에 들어가는 만큼 한 줄에 놓기"입니다
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

// 11. 상태 끌어올리기 (state hoisting, 스테이트 호이스팅)
// 한 줄로: 상태를 자식 조각에서 빼서 부모에게 옮기는 것입니다.
//
// 왜 하나요
//   5번은 count 를 버튼 바로 옆에 두었습니다. 조각이 하나일 때는 괜찮습니다.
//   하지만 버튼을 따로 떼어 낸 조각(PlusButton) 안에 count 를 두면, 그 버튼만 값을 압니다.
//   옆에 있는 글자나 다른 버튼은 그 값을 볼 수도, 바꿀 수도 없습니다.
// 어떻게 하나요
//   1) count 를 부모(Example11Hoisting)가 가집니다.
//   2) 자식(PlusButton)에게는 두 가지만 넘깁니다. 보여 줄 글자(label)와, 눌렸을 때 부를 함수(onClick).
//   3) 자식은 눌리면 onClick() 을 부르기만 합니다. 값을 어떻게 바꿀지는 부모가 정합니다.
//   정리하면 "값은 위에서 아래로 내려가고, 눌렸다는 소식은 아래에서 위로 올라갑니다".
// 비유
//   TV 리모컨을 아이마다 주지 않고 부모가 하나만 듭니다. 아이는 "채널 올려 주세요"라고 말만 합니다.
// C++ 로 치면
//   멤버 변수를 자식 객체에서 빼서 부모가 갖고, 자식에게는 값과 콜백(std::function<void()>)만 넘기는 것입니다.
@Composable
fun Example11Hoisting() {
    var count by remember { mutableIntStateOf(0) }       // 1) 상태는 부모 한 곳에만 있습니다
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("부모가 가진 값: $count", fontSize = 20.sp)   // 부모가 가졌으니 글자도 이 값을 볼 수 있습니다
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PlusButton(label = "왼쪽 +1", onClick = { count++ })      // 2) 눌렸을 때 할 일을 { } 로 넘깁니다
            PlusButton(label = "오른쪽 +1", onClick = { count++ })    //    두 버튼이 같은 count 를 바꿉니다
        }
    }
    // 바꿔 보기: 오른쪽 버튼의 count++ 를 count = 0 으로 바꿔 봅니다. PlusButton 은 고치지 않아도 됩니다.
    // 버튼이 "무엇을 할지"를 모르기 때문에, 같은 버튼 조각을 더하기에도 지우기에도 쓸 수 있습니다
}

// 3) 상태가 없는 화면 조각. count 가 있는지도 모릅니다. 받은 글자를 그리고, 눌리면 onClick() 으로 알리기만 합니다.
// onClick: () -> Unit 은 "인자 없이 부르고, 돌려주는 값도 없는 함수"라는 타입입니다. C++ 의 std::function<void()> 자리
@Composable
private fun PlusButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick) { Text(label) }
}

// 예제 11개를 한 화면에 모아 봅니다
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
        Example11Hoisting()
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
