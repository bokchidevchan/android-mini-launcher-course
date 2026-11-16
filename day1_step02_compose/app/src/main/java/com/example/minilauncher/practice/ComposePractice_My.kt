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

// 직접 만들어 보는 파일입니다.
// 교안의 "Compose 화면 직접 만들어 보기" 장에 있는 완성 화면과 요구사항을 보고 MyExample 안에 만듭니다.
// 오전에 XML 로 만든 것과 같은 화면입니다. Split 을 누르면 오른쪽 Preview 에 바로 보이고,
// 버튼은 Preview 이름 오른쪽 ⋮ > Start Interactive Mode 로 눌러 봅니다.
// ComposePractice.kt 의 예제(2번 세로와 가로, 5번 버튼과 상태)에서 필요한 것을 가져다 써도 됩니다.
// 정답은 같은 폴더의 ComposePractice_MyAnswer.kt 에 있습니다. 먼저 만들어 보고 엽니다
@Composable
fun MyExample() {
    Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Mini Launcher", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        // 여기에 만듭니다.
        // 1. 한 줄 소개 글자
        // 2. 좋아요 수를 기억하는 상태와 그것을 보여 주는 글자
        // 3. 가로로 놓인 버튼 두 개(좋아요, 처음으로)
    }
}

@Preview(showBackground = true, widthDp = 360, name = "내가 만드는 화면")
@Composable
private fun MyExamplePreview() { MiniLauncherTheme { MyExample() } }
