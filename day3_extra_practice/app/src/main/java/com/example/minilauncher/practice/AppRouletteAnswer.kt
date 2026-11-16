package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.launchApp
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 3일차 8교시 정답: 앱 룰렛.
// 3일 동안 배운 것이 다 들어 있습니다. 설치된 앱 읽기(3일차), 코루틴의 delay(2일차), Compose 의 상태(1일차), Intent 로 앱 열기(3일차)
class AppRouletteAnswerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { AppRouletteAnswerScreen() } }
    }
}

@Composable
fun AppRouletteAnswerScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }
    var current by remember { mutableStateOf<AppInfo?>(null) }
    var spinning by remember { mutableStateOf(false) }

    // 화면이 뜨면 한 번, 설치된 앱 목록을 읽습니다
    LaunchedEffect(Unit) {
        apps = InstalledAppRepository(context.applicationContext).loadApps()
    }

    RouletteLayout(
        count = apps.size,
        label = current?.label,
        spinning = spinning,
        onSpin = {
            if (apps.isNotEmpty() && !spinning) {
                scope.launch {
                    spinning = true
                    repeat(15) {
                        current = apps.random()      // 상태만 바꾸면 화면의 글자가 따라 바뀝니다
                        delay(80)
                    }
                    spinning = false
                }
            }
        },
        onOpen = { current?.let { launchApp(context, it) } },
    )
}
