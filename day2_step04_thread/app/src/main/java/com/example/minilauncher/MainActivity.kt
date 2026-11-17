package com.example.minilauncher

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.minilauncher.data.FakeAppRepository
import com.example.minilauncher.practice.myThreadPractice
import com.example.minilauncher.practice.threadPractice
import com.example.minilauncher.ui.AppsContent
import com.example.minilauncher.ui.ButtonRow
import com.example.minilauncher.ui.StatusArea
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

private const val TAG = "Lifecycle"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (복원 데이터 ${if (savedInstanceState == null) "없음" else "있음"})")
        // 스레드 실습: 숫자를 1부터 6까지 바꿔 가며 Run 합니다. Logcat 검색창에 tag:Thread 를 넣습니다. 0 이면 건너뜁니다
        threadPractice(this, step = 1)
        // myThreadPractice(this, step = 1)   // 직접 쓴 코드(ThreadPractice_My.kt)를 실행하려면 위 줄 대신 이 줄을 씁니다
        // 실패 확률을 바꿔 보고 싶으면: adb shell am start -n com.example.minilauncher/.MainActivity --ef fail_rate 1.0
        // failRate: 앱 목록을 불러올 때 일부러 실패하게 만드는 확률입니다 (실패 화면을 보려는 연습용).
        // 뒤의 숫자가 기본값입니다. 0.3 이면 열 번에 세 번쯤 실패하고, 0 이면 실패하지 않습니다
        val failRate = intent.getFloatExtra("fail_rate", 0.3f)
        // factory: ViewModel 을 만드는 방법을 적어 둔 것입니다. 만들 때 넘길 값(가짜 목록)이 있어서 필요합니다.
        // 아래의 viewModel(factory = factory) 는 ViewModel 이 없으면 이 방법으로 만들고, 있으면 있던 것을
        // 돌려줍니다. 그래서 화면을 돌려도 같은 ViewModel 이 남습니다
        val factory = viewModelFactory { initializer { LauncherViewModel(FakeAppRepository(failRate)) } }
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                LauncherScreen(viewModel(factory = factory))
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { Log.d(TAG, "onPause"); super.onPause() }
    override fun onStop() { Log.d(TAG, "onStop"); super.onStop() }
    override fun onDestroy() { Log.d(TAG, "onDestroy (회전 때문이면 true: $isChangingConfigurations)"); super.onDestroy() }
}

@Composable
fun LauncherScreen(vm: LauncherViewModel) {
    // StateFlow 를 Compose 상태로 바꿉니다. 값이 바뀔 때마다 이 함수가 다시 그려집니다
    val state by vm.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding()) {
        StatusArea(clock = "12:00", network = "Wi-Fi", battery = "80%")
        ButtonRow(
            "새로고침" to vm::load,
            "상세 보기" to {
                val apps = (state.apps as? AppsState.Success)?.apps.orEmpty()
                apps.find { it.packageName == state.selected }?.let { app ->
                    context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(DetailActivity.EXTRA_LABEL, app.label)
                            .putExtra(DetailActivity.EXTRA_PACKAGE, app.packageName)
                    )
                }
            },
        )
        AppsContent(state = state.apps, selected = state.selected, onAppClick = vm::select, onRetry = vm::load)
    }
}
