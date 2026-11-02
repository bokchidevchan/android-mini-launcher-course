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
        // 실패 확률을 바꿔 보고 싶으면: adb shell am start -n com.example.minilauncher/.MainActivity --ef fail_rate 1.0
        val failRate = intent.getFloatExtra("fail_rate", 0.3f)
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
