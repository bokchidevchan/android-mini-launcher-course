package com.example.minilauncher

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.model.dockApps
import com.example.minilauncher.model.filterApps
import com.example.minilauncher.ui.Dock
import com.example.minilauncher.ui.InfoPanel
import com.example.minilauncher.ui.SearchField
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
import com.example.minilauncher.ui.StatusArea
import com.example.minilauncher.ui.StatusInfo
import com.example.minilauncher.ui.SyncIndicator
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

private const val TAG = "Lifecycle"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val startSync = {
        if (Build.VERSION.SDK_INT >= 33) askNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
        else SyncService.start(context)
    }
    BackHandler { }

    // rememberSaveable: 화면을 돌려도 검색어가 남습니다 (remember 와 비교해 보기)
    var query by rememberSaveable { mutableStateOf("") }
    val boot = remember { BootReceiver.lastBoot(context) }
    val sync = rememberSyncProgress()
    val status = StatusInfo(clock = rememberClock(), network = rememberNetwork(), battery = rememberBattery(), boot = boot)
    val allApps = (state.apps as? AppsState.Success)?.apps.orEmpty()
    val onAppClick = { app: AppInfo -> vm.select(app); launchApp(context, app) }

    @Composable
    fun LauncherColumn(modifier: Modifier) {
        // 픽셀 런처 배치: 앱 목록, 그 아래 도크, 맨 아래 검색창
        Column(modifier) {
            val shown = state.apps.let { if (it is AppsState.Success) AppsState.Success(filterApps(it.apps, query)) else it }
            AppsContent(
                state = shown,
                selected = state.selected,
                onAppClick = onAppClick,
                onAppLongClick = { app -> openDetail(context, app) },
                onRetry = vm::load,
                modifier = Modifier.weight(1f),
            )
            Dock(dockApps(allApps), onAppClick)
            SearchField(query, { query = it }, onSyncClick = startSync)
        }
    }

    Box(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding()) {
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 가로: 왼쪽 정보 패널 + 오른쪽 검색, 앱, 도크
            Row(Modifier.fillMaxSize()) {
                InfoPanel(status, sync)
                LauncherColumn(Modifier.weight(1f).fillMaxHeight())
            }
        } else {
            Column(Modifier.fillMaxSize()) {
                StatusArea(status) { SyncIndicator(sync) }
                LauncherColumn(Modifier.weight(1f))
            }
        }
    }
}
