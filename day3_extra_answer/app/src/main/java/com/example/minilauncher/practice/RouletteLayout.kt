package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.launchApp
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 3일차 8교시 앱 '앱 룰렛' 정답의 화면 모양

// 화면 모양. 상태를 갖지 않고, 받은 값을 그리고 눌린 것을 알리기만 합니다
@Composable
fun RouletteLayout(count: Int, label: String?, spinning: Boolean, onSpin: () -> Unit, onOpen: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("앱 룰렛", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("설치된 앱 ${count}개 가운데 하나", fontSize = 16.sp)
        Spacer(Modifier.height(40.dp))
        Text(label ?: "돌려 보세요", fontSize = 40.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(40.dp))
        Button(onClick = onSpin, enabled = !spinning, modifier = Modifier.width(200.dp)) {
            Text(if (spinning) "돌아가는 중" else "돌리기")
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onOpen, enabled = label != null && !spinning, modifier = Modifier.width(200.dp)) {
            Text("이 앱 열기")
        }
    }
}
