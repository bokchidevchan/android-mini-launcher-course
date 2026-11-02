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
import com.example.minilauncher.system.BootReceiver
import com.example.minilauncher.system.rememberBattery
import com.example.minilauncher.system.rememberClock
import com.example.minilauncher.system.rememberNetwork
import com.example.minilauncher.service.SyncService
import com.example.minilauncher.service.rememberSyncProgress
import com.example.minilauncher.ui.StatusArea
import com.example.minilauncher.ui.SyncIndicator
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.minilauncher.ui.StatusInfo
import androidx.compose.runtime.remember
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

private const val TAG = "Lifecycle"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (복원 데이터 ${if (savedInstanceState == null) "없음" else "있음"})")
        // 2일차 오후부터는 에러 화면이 실습을 방해하지 않게 실패 확률 기본값을 0 으로 둡니다 (오전 실습은 0.3)
        val failRate = intent.getFloatExtra("fail_rate", 0f)
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
    // Android 13 부터 알림은 실행 중에 사용자에게 허락을 받아야 합니다. 거절해도 Service 는 돌아갑니다
    val askNotification = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        SyncService.start(context)
    }

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding()) {
        // 고정 값 대신 시스템 이벤트로 받은 값을 넣습니다
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
