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
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.launchApp
import com.example.minilauncher.model.AppInfo

// 3일차 8교시: 앱 룰렛. 설치된 앱 가운데 하나를 뽑아서 열어 줍니다. (정답)
// 실행: Run 하면 이 화면이 바로 뜹니다.

class AppRouletteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { AppRouletteScreen() } }
    }
}

@Composable
fun AppRouletteScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }     // 설치된 앱 목록
    var current by remember { mutableStateOf<AppInfo?>(null) }              // 지금 보이는 앱
    var spinning by remember { mutableStateOf(false) }                      // 돌아가는 중인지

    // 화면이 뜰 때 한 번 실행됩니다
    LaunchedEffect(Unit) {
        // [1] 설치된 앱 목록을 읽어 apps 에 넣습니다
        apps = InstalledAppRepository(context.applicationContext).loadApps()
    }

    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("앱 룰렛", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("설치된 앱 ${apps.size}개 가운데 하나", fontSize = 16.sp)
        Spacer(Modifier.height(40.dp))
        Text(current?.label ?: "돌려 보세요", fontSize = 40.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(40.dp))
        Button(enabled = !spinning && apps.isNotEmpty(), modifier = Modifier.width(200.dp), onClick = {
            // [2] 코루틴으로 0.08초마다 current 를 아무 앱으로 15번 바꾸고 멈춥니다. 도는 동안 spinning 은 true
            scope.launch {
                spinning = true
                repeat(15) { current = apps.random(); delay(80) }
                spinning = false
            }
        }) { Text(if (spinning) "돌아가는 중" else "돌리기") }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(enabled = current != null && !spinning, modifier = Modifier.width(200.dp), onClick = {
            // [3] 지금 보이는 앱(current)을 엽니다. current 는 null 일 수 있습니다
            current?.let { launchApp(context, it) }
        }) { Text("이 앱 열기") }
    }
}
