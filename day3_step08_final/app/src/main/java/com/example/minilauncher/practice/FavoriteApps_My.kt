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
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.launchApp
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 직접 만들어 보는 파일입니다. 완성본은 같은 폴더의 FavoriteApps.kt 입니다.
// Run 한 뒤 앱 목록에서 "즐겨찾기 서랍 연습" 아이콘을 누릅니다.
// 화면 모양(FavoriteAppsLayout)은 FavoriteApps.kt 의 것을 그대로 씁니다.
// 아래 1, 2, 3번을 채우면 완성본과 똑같이 움직입니다. 필요한 import 는 위에 다 있습니다.

@Composable
fun MyFavoriteAppsScreen() {
    val context = LocalContext.current
    var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }      // 설치된 앱 전체
    var favorites by remember { mutableStateOf<List<String>>(emptyList()) }   // 즐겨찾기한 앱의 패키지 이름

    // 1. 화면이 뜰 때 한 번, 설치된 앱 목록을 읽어 apps 에 넣습니다
    //      LaunchedEffect(Unit) { apps = InstalledAppRepository(context.applicationContext).loadApps() }

    FavoriteAppsLayout(
        apps = apps,
        favorites = favorites,
        onToggle = { app ->
            // 2. 별을 누르면: app.packageName 이 favorites 에 있으면 빼고(-), 없으면 넣습니다(+)
        },
        onOpen = { app ->
            // 3. 앱을 누르면: AppLauncher.kt 의 launchApp(context, app) 으로 엽니다
        },
    )
}

class MyFavoriteAppsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { MyFavoriteAppsScreen() } }
    }
}
