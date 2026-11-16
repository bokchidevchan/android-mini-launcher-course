package com.example.cleanlauncher

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cleanlauncher.data.InstalledAppRepository
import com.example.cleanlauncher.domain.model.AppInfo
import com.example.cleanlauncher.domain.repository.AppRepository
import com.example.cleanlauncher.domain.usecase.GetAppsUseCase
import com.example.cleanlauncher.domain.usecase.SearchAppsUseCase
import com.example.cleanlauncher.feature.home.HomeScreen
import com.example.cleanlauncher.feature.home.HomeViewModel

class MainActivity : ComponentActivity() {

    // 조립하는 곳. "어떤 저장소를 쓸지"는 여기서만 정합니다.
    // 실무에서는 이 조립을 Hilt 같은 도구에 맡깁니다
    private val viewModel: HomeViewModel by viewModels {
        viewModelFactory {
            initializer {
                val repository: AppRepository = InstalledAppRepository(applicationContext)
                HomeViewModel(GetAppsUseCase(repository), SearchAppsUseCase())
            }
        }
    }

    // 알림 권한을 묻는 창. 허락하든 거절하든 Service 는 시작합니다 (거절하면 알림만 안 보임)
    private val askNotification =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { startHelloService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 처음 켤 때 한 번만. 화면을 돌려 다시 만들어질 때는 건너뜁니다
        if (savedInstanceState == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                askNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                startHelloService()
            }
        }
        setContent {
            MaterialTheme {
                HomeScreen(viewModel, onAppClick = ::launchApp)
            }
        }
    }

    // Manifest 에 적어 둔 HelloService 를 시작합니다. 알림이 20초 동안 보입니다
    private fun startHelloService() {
        startForegroundService(Intent(this, HelloService::class.java))
    }

    private fun launchApp(app: AppInfo) {
        val intent = Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .setClassName(app.packageName, app.className)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("CleanLauncher", "앱을 열 수 없음: ${app.packageName}", e)
        }
    }
}
