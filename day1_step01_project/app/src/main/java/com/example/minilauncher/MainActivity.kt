package com.example.minilauncher

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.minilauncher.practice.kotlinPractice
import com.example.minilauncher.practice.myKotlinPractice
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Logcat 검색창에 tag:MiniLauncher 를 넣으면 이 줄만 보입니다
        Log.d("MiniLauncher", "MainActivity onCreate, 프로세스 id=${android.os.Process.myPid()}")
        // Kotlin 문법 실습: 숫자를 1부터 12까지 바꿔 가며 Run 합니다. Logcat 검색창에 tag:Kotlin 을 넣습니다
        kotlinPractice(step = 1)
        // myKotlinPractice(step = 1)   // 직접 쓴 코드(KotlinPractice_My.kt)를 실행하려면 위 줄 대신 이 줄을 씁니다
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                FirstScreen()
            }
        }
    }
}

@Composable
fun FirstScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(LauncherBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Mini Launcher", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(
            "네트워크: Wi-Fi",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 914)
@Composable
fun FirstScreenPreview() {
    MiniLauncherTheme { FirstScreen() }
}
