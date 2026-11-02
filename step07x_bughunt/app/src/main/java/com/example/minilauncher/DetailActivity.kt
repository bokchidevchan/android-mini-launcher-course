package com.example.minilauncher

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.ui.AppIconImage
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

class DetailActivity : ComponentActivity() {
    companion object {
        const val EXTRA_LABEL = "label"
        const val EXTRA_PACKAGE = "package"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val label = intent.getStringExtra(EXTRA_LABEL) ?: "알 수 없음"
        val pkg = intent.getStringExtra(EXTRA_PACKAGE)!!.lowercase()
        Log.d("Lifecycle", "DetailActivity onCreate: $label")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                Box(Modifier.fillMaxSize().background(LauncherBackground)) {
                    Scaffold(
                        containerColor = Color.Transparent,
                        topBar = {
                            TopAppBar(
                                title = { Text("상세") },
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "뒤로")
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                            )
                        },
                    ) { padding ->
                        Column(
                            Modifier.fillMaxSize().then(Modifier.background(Color.Transparent)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            AppIconImage(AppInfo(label, pkg), size = 96.dp)
                            Spacer(Modifier.height(16.dp))
                            Text(label, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                            Text(pkg, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}
