package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.launchApp
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.AppIconImage
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 7교시 같이 만들기 "앱 찾기" 의 뼈대입니다. 완성본은 같은 폴더의 SearchApps.kt 입니다.
// 기본 홈 앱을 원래 런처로 되돌린 뒤 Run 하고, 에뮬레이터의 앱 목록에서 "앱 찾기 직접" 아이콘을 누릅니다.
// [1] [2] [3] 자리를 위에서부터 차례로 채웁니다. 순서와 힌트는 README.md 에 있습니다.

class MySearchAppsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { MySearchAppsScreen() } }
    }
}

@Composable
fun MySearchAppsScreen() {
    val context = LocalContext.current
    var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }     // 설치된 앱 전체
    var query by rememberSaveable { mutableStateOf("") }                    // 검색어

    LaunchedEffect(Unit) {
        apps = InstalledAppRepository(context.applicationContext).loadApps()
    }

    val shown = apps    // [1] 여기를 고칩니다: 검색어가 이름에 든 앱만 남기기

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(16.dp)) {
        Text("앱 찾기", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        // [2] 여기를 채웁니다: 검색어 입력창
        Text("${shown.size}개", modifier = Modifier.padding(vertical = 8.dp))
        LazyColumn {
            items(shown, key = { it.packageName + it.className }) { app ->
                Row(
                    // [3] 여기를 고칩니다: 아래 modifier 줄에 누르면 앱 열기를 더합니다
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppIconImage(app, 40.dp)
                    Spacer(Modifier.width(12.dp))
                    Text(app.label)
                }
            }
        }
    }
}
