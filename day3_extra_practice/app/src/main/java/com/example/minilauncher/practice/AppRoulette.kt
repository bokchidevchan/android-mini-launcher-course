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
// 화면 모양(RouletteLayout)은 만들어져 있습니다. AppRouletteScreen 안을 직접 만듭니다.
// 정답: AppRouletteAnswer.kt ("앱 룰렛 정답" 아이콘)
class AppRoulettePracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { AppRouletteScreen() } }
    }
}

@Composable
fun AppRouletteScreen() {
    val apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }     // 설치된 앱 목록
    val current by remember { mutableStateOf<AppInfo?>(null) }              // 지금 보이는 앱
    val spinning by remember { mutableStateOf(false) }                      // 돌아가는 중인지

    // 여기부터 직접 만듭니다. 요구사항은 교안의 3일차 8교시 장에 있습니다. 위의 val 은 값을 바꿀 수 있게 var 로 고칩니다.
    // 필요한 것(LocalContext, LaunchedEffect, rememberCoroutineScope, launch, delay, InstalledAppRepository, launchApp)은
    // 위에 import 해 두었습니다.
    // 1. 화면이 뜨면 설치된 앱 목록을 읽습니다.
    //      val context = LocalContext.current
    //      LaunchedEffect(Unit) { apps = InstalledAppRepository(context.applicationContext).loadApps() }
    // 2. 돌리기: 0.08초마다 아무 앱으로 15번 바꾸고 멈춥니다. 목록이 비어 있으면 아무것도 하지 않습니다.
    //      val scope = rememberCoroutineScope()
    //      if (apps.isNotEmpty()) scope.launch { spinning = true; repeat(15) { current = apps.random(); delay(80) }; spinning = false }
    // 3. 이 앱 열기: 오전에 쓴 launchApp 을 부릅니다. current 는 null 일 수 있어서 먼저 확인합니다.
    //      val app = current
    //      if (app != null) launchApp(context, app)
    RouletteLayout(
        count = apps.size,
        label = current?.label,
        spinning = spinning,
        onSpin = { /* 2번 */ },
        onOpen = { /* 3번 */ },
    )
}

// 화면 모양. 상태를 갖지 않고, 받은 값을 그리고 눌린 것을 알리기만 합니다
@Composable
fun RouletteLayout(count: Int, label: String?, spinning: Boolean, onSpin: () -> Unit, onOpen: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("앱 룰렛", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("설치된 앱 ${count}개 가운데 하나", fontSize = 16.sp)
        Spacer(Modifier.height(40.dp))
        Text(label ?: "돌려 보세요", fontSize = 40.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(40.dp))
        Button(onClick = onSpin, enabled = !spinning, modifier = Modifier.width(200.dp)) {
            Text(if (spinning) "돌아가는 중" else "돌리기")
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onOpen, enabled = label != null && !spinning, modifier = Modifier.width(200.dp)) {
            Text("이 앱 열기")
        }
    }
}
