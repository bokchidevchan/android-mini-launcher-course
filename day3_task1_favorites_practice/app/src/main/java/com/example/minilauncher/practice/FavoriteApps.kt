package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.launchApp
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 화면은 ViewModel 이 들고 있는 값을 그리고, 누른 일은 ViewModel 에 알리기만 함
@Composable
fun FavoriteAppsScreen(vm: FavoritesViewModel = viewModel()) {
    val context = LocalContext.current
    FavoriteAppsLayout(
        apps = vm.apps,
        favorites = vm.favorites,
        onToggle = { app -> vm.toggle(app) },
        // [3] 여기를 채웁니다: 앱을 누르면 그 앱을 열기 (힌트: 2교시의 launchApp(context, app))
        onOpen = { app -> },
    )
}

class FavoriteAppsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { FavoriteAppsScreen() } }
    }
}
