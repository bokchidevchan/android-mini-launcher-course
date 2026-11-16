package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.launchApp
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 3일차 8교시: 앱 룰렛.
// "돌리기"를 누르면 설치된 앱 이름이 빠르게 바뀌다가 하나에서 멈춥니다. "이 앱 열기"를 누르면 그 앱이 열립니다.
// 실행: 에뮬레이터의 앱 목록에서 "앱 룰렛 연습" 아이콘.
// AppRouletteScreen 안에 화면과 동작을 모두 직접 만듭니다.
// 정답은 day3_extra_answer 폴더에 있습니다. 먼저 만들어 보고 엽니다
class AppRoulettePracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { AppRouletteScreen() } }
    }
}

@Composable
fun AppRouletteScreen() {
    // 여기에 화면과 동작을 모두 직접 만듭니다. 완성 화면과 요구사항은 교안의 3일차 8교시 장에 있습니다.
    // 필요한 것: 설치된 앱 목록, 지금 보이는 앱, 돌아가는 중인지를 기억할 상태 세 개,
    //           글자 두 줄과 버튼 두 개(돌리기, 이 앱 열기).
    // 막히면 README.md 의 "막히면 보는 힌트"를 엽니다
    Text("앱 룰렛")
}
