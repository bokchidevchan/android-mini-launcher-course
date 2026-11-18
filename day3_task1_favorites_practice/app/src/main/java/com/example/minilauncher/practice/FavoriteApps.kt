package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 화면은 ViewModel 이 들고 있는 값을 그리고, 누른 일은 ViewModel 에 알리기만 함
// vm = viewModel(): 처음엔 새로 만들고, 화면을 돌린 뒤에는 있던 ViewModel 을 다시 줌
@Composable
fun FavoriteAppsScreen(vm: FavoritesViewModel = viewModel()) {
    val context = LocalContext.current   // 다른 앱을 열 때 필요한 Context
    FavoriteAppsLayout(
        apps = vm.apps,
        favorites = vm.favorites,
        onToggle = { app -> vm.toggle(app) },
        // [3] 여기를 채웁니다: 앱을 누르면 그 앱을 열기 (힌트: InstalledApps.kt 에 있는 launchApp(context, app))
        onOpen = { app -> },
    )
}

// Run 하면 뜨는 화면. 내용은 위의 FavoriteAppsScreen 이 그림
class FavoriteAppsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()    // 상태바 뒤까지 화면을 꽉 채움
        setContent { MiniLauncherTheme { FavoriteAppsScreen() } }
    }
}
