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

// 3일차 오전 과제 "즐겨찾기 서랍" 의 뼈대입니다.
// [1] [2] [3] 을 채우고 [4] 를 고칩니다. 순서와 힌트는 README.md 에 있습니다. 완성본은 day3_task1_favorites_answer 폴더입니다.
// 화면은 FavoriteAppsLayout.kt 에 있습니다. 위의 import 가운데 회색인 것은 채우고 나면 쓰입니다.

@Composable
fun FavoriteAppsScreen() {
    val context = LocalContext.current
    var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }               // 설치된 앱 전체
    var favorites by remember { mutableStateOf<List<String>>(emptyList()) }           // [4] 여기를 고칩니다

    // [1] 여기를 채웁니다: 화면이 뜰 때 한 번, 설치된 앱 목록을 읽어 apps 에 넣기

    FavoriteAppsLayout(
        apps = apps,
        favorites = favorites,
        onToggle = { app ->
            // [2] 여기를 채웁니다: 별을 누르면 favorites 에 넣거나 빼기
        },
        onOpen = { app ->
            // [3] 여기를 채웁니다: 앱을 누르면 그 앱을 열기
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
