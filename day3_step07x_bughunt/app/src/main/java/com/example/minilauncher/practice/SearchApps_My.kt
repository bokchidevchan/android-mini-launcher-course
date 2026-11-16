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

// 같이 해 보기 파일입니다. 완성본은 같은 폴더의 SearchApps.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "앱 찾기 직접" 아이콘을 누릅니다.
// 지금은 설치된 앱이 전부 나오기만 합니다. 아래 [1] [2] [3] 을 차례로 하고, 할 때마다 Run 해서 봅니다.
//
// [1] 검색어 입력창. "[1] 여기에" 자리에 붙입니다
//         OutlinedTextField(
//             value = query,
//             onValueChange = { query = it },
//             label = { Text("앱 이름") },
//             singleLine = true,
//             modifier = Modifier.fillMaxWidth(),
//         )
//
// [2] 거르기. val shown = apps 를 아래 줄로 바꿉니다
//         val shown = apps.filter { it.label.contains(query, ignoreCase = true) }
//
// [3] 누르면 열기. Row 의 modifier 에서 fillMaxWidth() 뒤에 붙입니다
//         .clickable { launchApp(context, app) }

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

    val shown = apps

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(16.dp)) {
        Text("앱 찾기", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        // [1] 여기에
        Text("${shown.size}개", modifier = Modifier.padding(vertical = 8.dp))
        LazyColumn {
            items(shown, key = { it.packageName + it.className }) { app ->
                Row(
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
