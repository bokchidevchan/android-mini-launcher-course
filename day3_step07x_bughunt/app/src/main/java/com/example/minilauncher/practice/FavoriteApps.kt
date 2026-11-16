package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.launchApp
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.AppIconImage
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 3일차 오전: 설치된 앱 목록으로 만드는 앱 "즐겨찾기 서랍".
// Run 한 뒤 에뮬레이터의 앱 목록(홈 화면을 위로 밀기)에서 "즐겨찾기 서랍" 아이콘을 누릅니다.
// Mini Launcher 를 기본 홈 앱으로 바꾼 뒤에는 아이콘이 안 보이므로, 그 전에 하거나 홈 앱을 잠시 되돌립니다.
// 별을 누르면 위쪽 즐겨찾기 줄에 담기고, 앱 이름이나 아이콘을 누르면 그 앱이 열립니다.
// 읽는 순서: 1. 상태와 동작  2. 화면
// 직접 써 보고 싶으면 같은 폴더의 FavoriteApps_My.kt 를 엽니다.

class FavoriteAppsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { FavoriteAppsScreen() } }
    }
}

// ---------------------------------------------------------------- 1. 상태와 동작
@Composable
fun FavoriteAppsScreen() {
    val context = LocalContext.current
    var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }      // 설치된 앱 전체
    var favorites by remember { mutableStateOf<List<String>>(emptyList()) }   // 즐겨찾기한 앱의 패키지 이름

    // 화면이 뜰 때 한 번, 설치된 앱 목록을 읽습니다. 오래 걸릴 수 있어 코루틴 안에서 부릅니다
    LaunchedEffect(Unit) {
        apps = InstalledAppRepository(context.applicationContext).loadApps()
    }

    FavoriteAppsLayout(
        apps = apps,
        favorites = favorites,
        // 별을 누르면: 이미 있으면 빼고, 없으면 넣습니다
        onToggle = { app ->
            favorites = if (app.packageName in favorites) favorites - app.packageName else favorites + app.packageName
        },
        // 앱을 누르면: 그 앱을 엽니다
        onOpen = { app -> launchApp(context, app) },
    )
    // 바꿔 보기: favorites 를 remember 대신 rememberSaveable 로 바꾸면 화면을 돌려도 즐겨찾기가 남습니다 (빨간 줄이 뜨면 Alt+Enter 로 import)
}

// ---------------------------------------------------------------- 2. 화면
// 상태를 받아서 그리기만 합니다. 누른 일은 onToggle, onOpen 으로 알립니다
@Composable
fun FavoriteAppsLayout(
    apps: List<AppInfo>,
    favorites: List<String>,
    onToggle: (AppInfo) -> Unit,
    onOpen: (AppInfo) -> Unit,
) {
    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(16.dp)) {
        Text("즐겨찾기 서랍", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        // 위쪽: 즐겨찾기한 앱만
        val favoriteApps = apps.filter { it.packageName in favorites }
        if (favoriteApps.isEmpty()) {
            Text("별을 눌러 자주 쓰는 앱을 담아 보세요", modifier = Modifier.height(56.dp))
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                favoriteApps.take(5).forEach { app ->
                    Column(Modifier.clickable { onOpen(app) }) { AppIconImage(app, 56.dp) }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("설치된 앱 ${apps.size}개", fontWeight = FontWeight.Bold)
        HorizontalDivider()

        // 아래쪽: 전체 목록
        LazyColumn {
            items(apps, key = { it.packageName + it.className }) { app ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onOpen(app) }.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppIconImage(app, 40.dp)
                    Spacer(Modifier.width(12.dp))
                    Text(app.label, modifier = Modifier.weight(1f))
                    TextButton(onClick = { onToggle(app) }) {
                        Text(if (app.packageName in favorites) "★" else "☆", fontSize = 22.sp)
                    }
                }
            }
        }
    }
}
