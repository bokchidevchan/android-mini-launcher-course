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
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Logcat 검색창에 tag:MiniLauncher 를 넣으면 이 줄만 보입니다
        Log.d("MiniLauncher", "MainActivity onCreate, 프로세스 id=${android.os.Process.myPid()}")
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
