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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.practice.launcherPractice
import com.example.minilauncher.practice.myLauncherPractice
import com.example.minilauncher.ui.AppsContent
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

private const val TAG = "Lifecycle"

// 3일차 2교시: 다른 앱 열기, 상세 화면, 기본 홈 앱 등록

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 런처 5~8단계: 아래 줄의 // 를 지우고 숫자를 5~8로 바꿔 Run. 5, 6은 Run 하면 다른 화면이 열리니 확인한 뒤 다시 // 로 막음
        // launcherPractice(this, step = 5)
        // myLauncherPractice(this, step = 5)             // 직접 쓴 코드(LauncherPractice_My.kt)를 실행하려면 이 줄
        Log.d(TAG, "onCreate")                            // 홈 버튼을 다시 눌러도 이 줄이 또 찍히는지 봄 (singleTask)
        // ViewModel 에 실제 설치 앱을 주는 저장소를 꽂음
        val factory = viewModelFactory { initializer { LauncherViewModel(InstalledAppRepository(applicationContext)) } }
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                LauncherScreen(viewModel(factory = factory))
            }
        }
    }

    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
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
            onAppLongClick = { app -> openDetail(context, app) },             // 길게 누르면 상세 화면 (DetailActivity)
            onRetry = vm::load,
        )
    }
}
