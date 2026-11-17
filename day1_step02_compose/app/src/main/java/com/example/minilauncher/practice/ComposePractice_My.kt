package com.example.minilauncher.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
fun MyExample1() {

    Column() {
        Text("보통 텍스트")
        Text("큰 글자", fontSize = 32.sp)
        Text("굵은 글자", fontWeight = FontWeight.Bold)
        Text("글자 색 바꾸기", color = Color.Red)
        Text("배경 색 바꾸기", modifier = Modifier
            .padding(8.dp)
            .background(Color.Yellow))
    }

}

@Composable
fun MyExample2() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text("첫번째줄 텍스트")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("두번째줄 텍스트1", fontSize = 5.sp, fontWeight = FontWeight.Bold)
            Text("두번째줄 텍스트2", fontSize = 5.sp, fontWeight = FontWeight.Bold)
            Text("두번째줄 텍스트3", fontSize = 5.sp, fontWeight = FontWeight.Bold)
        }
        Text("세번째줄 텍스트")

    }


}


@Composable
fun MyExample3() {

    Box(
        modifier = Modifier
            .width(70.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Red)
            .clickable {

            }
        ,
        contentAlignment = Alignment.Center
    ) {
        Text("여기는 버튼입니다.")
    }

}

@Composable
fun MyExample4() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Red)
                .padding(top = 20.dp, start = 20.dp)
        ) {

            Text("안녕하세요", modifier = Modifier.align(Alignment.Center))

        }

    }

}

@Composable
fun MyExample5(){

    val labels = listOf("라벨1", "라벨2", "라벨3", "라벨4", "라벨5", "라벨6", "라벨7", "라벨8", "라벨9", "라벨10")
    Column() {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(labels) { label ->
                Box(
                    modifier = Modifier
                        .background(Color.Yellow)
                        .padding(16.dp)
                ) {
                    Text(label, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }


}


@Composable
fun MyExample6(){

    val textList = listOf("라벨1", "라벨2", "라벨3", "라벨4", "라벨5", "라벨6", "라벨7", "라벨8", "라벨9", "라벨10")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(30.dp),
    ) {

        items(textList) { text ->
            Box(
                modifier = Modifier
                    .background(Color.Yellow)
                    .padding(16.dp)
            ) {
                Text(text, modifier = Modifier.align(Alignment.Center))
            }
        }
    }


}


@Preview(showBackground = true, widthDp = 360, name = "내가 만드는 화면")
@Composable
private fun MyExamplePreview() {
    MiniLauncherTheme {
        MyExample6()
    }
}