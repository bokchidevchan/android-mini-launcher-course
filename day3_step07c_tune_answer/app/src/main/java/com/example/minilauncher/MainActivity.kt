package com.example.minilauncher

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.ui.AppsContent
import com.example.minilauncher.ui.HomeFrame
import com.example.minilauncher.ui.isMyAppDefaultHome
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

private const val TAG = "Lifecycle"

// 3일차 4교시: 내 런처 손보기 6단계를 모두 마친 완성본. 시작본은 day3_step07c_tune. 바뀐 곳은 [1]~[6] 주석

class MainActivity : ComponentActivity() {
    private var isHome by mutableStateOf(false)          // [6] 지금 홈 앱인지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")                            // 홈 버튼을 다시 눌러도 이 줄이 또 찍히는지 봄 (singleTask)
        // ViewModel 에 실제 설치 앱을 주는 저장소를 꽂음
        val factory = viewModelFactory { initializer { LauncherViewModel(InstalledAppRepository(applicationContext)) } }
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                HomeFrame(isHome) { LauncherScreen(viewModel(factory = factory)) }   // [6] 홈 앱이면 초록 테두리
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isHome = isMyAppDefaultHome(this)                 // [6] 화면이 다시 보일 때마다 확인
        Log.d(TAG, "onResume, 홈 앱인가: $isHome")
    }
}

@Composable
fun LauncherScreen(vm: LauncherViewModel) {
    val state by vm.uiState.collectAsStateWithLifecycle()   // ViewModel 의 상태를 받아서, 바뀌면 다시 그림
    val context = LocalContext.current                      // 다른 앱을 열 때 필요한 Context
    // 홈 화면에서 뒤로 가기를 눌러도 런처가 닫히지 않게 막습니다 (할 일을 비워 둠)
    BackHandler { }

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding()) {
        AppsContent(
            state = state.apps,
            selected = state.selected,
            onAppClick = { app -> vm.select(app); launchApp(context, app) },   // 짧게 누르면 그 앱을 엶 (AppLauncher.kt)
            onAppLongClick = { app -> openAppInfo(context, app) },            // [5] 길게 누르면 설정 앱의 앱 정보
            onRetry = vm::load,
        )
    }
}
