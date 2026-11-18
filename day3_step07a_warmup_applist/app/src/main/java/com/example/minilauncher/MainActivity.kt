package com.example.minilauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.practice.launcherPractice
import com.example.minilauncher.practice.myLauncherPractice
import com.example.minilauncher.practice.warmUp
import com.example.minilauncher.ui.AppsContent
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 3일차 1교시: 몸풀기와 설치된 앱 목록 읽기.
// 아이콘을 누르면 고른 표시만 바뀌고, 그 앱을 여는 것은 2교시(day3_step07b_open_home)에서 함

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 몸풀기: 아래 줄의 // 를 지우고 숫자를 1~4로 바꿔 Run. 끝나면 다시 // 로 막음
        // warmUp(this, step = 1)
        launcherPractice(this, step = 1)                  // 런처 1~4단계 (practice/LauncherPractice.kt)
        // myLauncherPractice(this, step = 1)             // 직접 쓴 코드(LauncherPractice_My.kt)를 실행하려면 위 줄 대신 이 줄
        // ViewModel 에 실제 설치 앱을 주는 저장소를 꽂음. FakeAppRepository() 로 바꾸면 고정 목록 16개
        val factory = viewModelFactory { initializer { LauncherViewModel(InstalledAppRepository(applicationContext)) } }
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                LauncherScreen(viewModel(factory = factory))
            }
        }
    }
}

@Composable
fun LauncherScreen(vm: LauncherViewModel) {
    val state by vm.uiState.collectAsStateWithLifecycle()   // ViewModel 의 상태를 받아서, 바뀌면 다시 그림
    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding()) {
        AppsContent(
            state = state.apps,                          // 불러오는 중, 성공(앱 목록), 실패
            selected = state.selected,                   // 고른 앱 (테두리로 표시)
            onAppClick = { app -> vm.select(app) },      // 누르면 고르기만 함. 앱 열기는 2교시
            onRetry = vm::load,                          // 실패했을 때 "다시 시도"
        )
    }
}
