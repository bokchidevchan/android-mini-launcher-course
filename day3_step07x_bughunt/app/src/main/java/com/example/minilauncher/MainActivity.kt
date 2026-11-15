package com.example.minilauncher

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.service.SyncService
import com.example.minilauncher.service.rememberSyncProgress
import com.example.minilauncher.system.BootReceiver
import com.example.minilauncher.system.rememberBattery
import com.example.minilauncher.system.rememberClock
import com.example.minilauncher.system.rememberNetwork
import com.example.minilauncher.ui.AppsContent
import com.example.minilauncher.ui.ButtonRow
import com.example.minilauncher.ui.StatusArea
import com.example.minilauncher.ui.StatusInfo
import com.example.minilauncher.ui.SyncIndicator
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

private const val TAG = "Lifecycle"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 개발 중에만 켜는 검사기. 메인 스레드에서 디스크나 네트워크를 쓰면 Logcat 에 경고를 남깁니다
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        Log.d(TAG, "onCreate (복원 데이터 ${if (savedInstanceState == null) "없음" else "있음"})")
        val factory = viewModelFactory { initializer { LauncherViewModel(InstalledAppRepository(applicationContext)) } }
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
    val state by vm.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val askNotification = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        SyncService.start(context)
    }
    // 홈 화면에서 뒤로 가기를 눌러도 런처가 닫히지 않게 막습니다
    BackHandler { }

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding()) {
        val boot = remember { BootReceiver.lastBoot(context) }
        val sync = rememberSyncProgress()
        StatusArea(StatusInfo(clock = rememberClock(), network = rememberNetwork(), battery = rememberBattery(), boot = boot)) {
            SyncIndicator(sync)
        }
        ButtonRow(
            "새로고침" to vm::load,
            "동기화 시작" to {
                if (Build.VERSION.SDK_INT >= 33) askNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
                else SyncService.start(context)
            },
        )
        // 탭하면 실행, 길게 누르면 상세 화면
        AppsContent(
            state = state.apps,
            selected = state.selected,
            onAppClick = { app -> vm.select(app); launchApp(context, app) },
            onAppLongClick = { app -> openDetail(context, app) },
            onRetry = vm::load,
        )
    }
}
