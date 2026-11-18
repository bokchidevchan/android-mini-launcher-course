package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 3일차 오후 과제 "홈 화면 위젯" 에서 고치지 않고 그대로 쓰는 안내 화면입니다.
// Run 하면 이 화면이 뜨고, 위젯 제목을 눌러도 이 화면이 열립니다. 채우는 곳은 MiniWidget.kt 하나입니다.
class WidgetGuideActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                Column(
                    Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("위젯 연습", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("1. 홈 버튼을 눌러 홈 화면(Pixel Launcher)으로 갑니다")
                    Text("2. 빈 곳을 길게 누름 > Widgets > 위젯 연습 > Add")
                    Text("3. [1] [2]를 채운 뒤 위젯의 \"새로고침\"을 누르면 시각이 바뀝니다")
                    Text("4. 위젯의 제목을 누르면 이 화면이 다시 열립니다")
                }
            }
        }
    }
}
