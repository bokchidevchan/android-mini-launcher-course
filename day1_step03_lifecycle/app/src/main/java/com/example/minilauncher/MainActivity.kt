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
import com.example.minilauncher.ui.AppGrid
import com.example.minilauncher.ui.ButtonRow
import com.example.minilauncher.ui.StatusArea
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

private const val TAG = "Lifecycle"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 브레이크포인트 실습: 이 줄 왼쪽을 눌러 빨간 점을 찍고 Debug 로 실행합니다
        Log.d(TAG, "onCreate (복원 데이터 ${if (savedInstanceState == null) "없음" else "있음"})")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                LauncherScreen()
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
fun LauncherScreen(vm: LauncherViewModel = viewModel()) {
    val selected by vm.selected.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding()) {
        StatusArea(clock = "12:00", network = "Wi-Fi", battery = "80%")
        ButtonRow(
            "상세 보기" to {
                val app = vm.apps.find { it.packageName == selected } ?: return@to
                // 명시적 Intent: 열 화면(클래스)을 직접 지정하고, 넘길 값은 extra 로 담습니다
                val intent = Intent(context, DetailActivity::class.java)
                    .putExtra(DetailActivity.EXTRA_LABEL, app.label)
                    .putExtra(DetailActivity.EXTRA_PACKAGE, app.packageName)
                context.startActivity(intent)
            },
        )
        AppGrid(apps = vm.apps, selected = selected, onAppClick = vm::select)
    }
}
