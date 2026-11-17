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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.launchApp
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 3일차 오전 과제 "즐겨찾기 서랍" 의 완성본입니다.
// 설치된 앱 목록에서 별을 누르면 위쪽에 담기고, 앱을 누르면 그 앱이 열립니다.
// [1] [2] [3] [4] 가 뼈대 프로젝트에서 채우는 자리입니다. 화면은 FavoriteAppsLayout.kt 에 있습니다.

@Composable
fun FavoriteAppsScreen() {
    val context = LocalContext.current
    var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }               // 설치된 앱 전체
    var favorites by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }   // [4] 화면을 돌려도 남게

    // [1] 화면이 뜰 때 한 번, 설치된 앱 목록을 읽어 옵니다
    LaunchedEffect(Unit) {
        apps = InstalledAppRepository(context.applicationContext).loadApps()
    }

    FavoriteAppsLayout(
        apps = apps,
        favorites = favorites,
        onToggle = { app ->
            // [2] 별을 누르면: 이미 있으면 빼고, 없으면 넣습니다
            favorites = if (app.packageName in favorites) favorites - app.packageName else favorites + app.packageName
        },
        onOpen = { app ->
            // [3] 앱을 누르면 그 앱을 엽니다
            launchApp(context, app)
        },
    )
}

class FavoriteAppsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { FavoriteAppsScreen() } }
    }
}
