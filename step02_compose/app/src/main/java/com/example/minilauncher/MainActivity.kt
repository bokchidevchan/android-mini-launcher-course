package com.example.minilauncher

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.minilauncher.model.FakeApps
import com.example.minilauncher.ui.AppGrid
import com.example.minilauncher.ui.StatusArea
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MiniLauncher", "MainActivity onCreate")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                LauncherScreen()
            }
        }
    }
}

@Composable
fun LauncherScreen() {
    // remember: 다시 그려져도(Recomposition) 값을 기억합니다. 화면을 돌리면 사라집니다 (3단계에서 해결)
    var selected by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding()) {
        StatusArea(clock = "12:00", network = "Wi-Fi", battery = "80%")
        AppGrid(
            apps = FakeApps,
            selected = selected,
            onAppClick = { app ->
                Log.d("MiniLauncher", "선택: ${app.label}")
                selected = app.packageName
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 914)
@Composable
fun LauncherScreenPreview() {
    MiniLauncherTheme { LauncherScreen() }
}
