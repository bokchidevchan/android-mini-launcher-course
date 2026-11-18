package com.example.minilauncher

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.practice.myNotificationPractice
import com.example.minilauncher.practice.notificationPractice
import com.example.minilauncher.service.SyncService
import com.example.minilauncher.service.rememberSyncProgress
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 2일차 6~7교시 폴더입니다. 이번 시간에 쓰는 코드만 남겼습니다.
// service/SyncService.kt            동기화를 하는 Foreground Service
// service/SyncConnection.kt         화면이 Service 에 붙어(bind) 진행률을 받아 오는 통로
// practice/NotificationPractice.kt  알림 실습 5단계
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 알림 실습: 숫자를 1부터 5까지 바꿔 가며 Run 합니다
        notificationPractice(this, step = 1)
        // myNotificationPractice(this, step = 1)   // 직접 쓴 코드(NotificationPractice_My.kt)를 실행하려면 위 줄 대신 이 줄을 씁니다
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                SyncScreen()
            }
        }
    }
}

@Composable
fun SyncScreen() {
    val context = LocalContext.current
    // Android 13 부터 알림은 실행 중에 사용자에게 허락을 받아야 합니다. 거절해도 Service 는 돌아갑니다
    val askNotification = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        SyncService.start(context)
    }
    // 화면이 보이는 동안 Service 에 붙어 진행률을 받습니다 (service/SyncConnection.kt)
    val sync = rememberSyncProgress()

    Column(
        Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
    ) {
        Text("동기화 Service", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        // 진행률: null 이면 쉬는 중, 0~99 는 진행 중, 100 이면 완료 (SyncService.progress)
        val text = when {
            sync == null -> "쉬는 중"
            sync >= 100 -> "동기화 완료"
            else -> "$sync%"
        }
        Text(text, fontSize = 40.sp, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(progress = { (sync ?: 0) / 100f }, modifier = Modifier.fillMaxWidth())
        Button(onClick = {
            if (Build.VERSION.SDK_INT >= 33) askNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
            else SyncService.start(context)
        }) { Text("동기화 시작") }
        Text(
            "시작한 뒤 화면 위쪽을 끌어내리면 알림의 진행 막대가 보입니다",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
