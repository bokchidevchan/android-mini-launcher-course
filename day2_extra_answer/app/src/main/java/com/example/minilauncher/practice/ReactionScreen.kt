package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlin.random.Random
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 2일차 8교시 앱 '반응 속도 게임'의 상태와 화면. 고치지 않고 그대로 씁니다

// 게임의 상태. 화면은 이 다섯 가지 가운데 하나를 그립니다
sealed interface GameState {
    data object Ready : GameState                  // 처음. 누르면 시작
    data object Waiting : GameState                // 빨간색. 초록색을 기다리는 중
    data object Go : GameState                     // 초록색. 지금 눌러야 함
    data class Result(val ms: Long) : GameState    // 걸린 시간
    data object TooEarly : GameState               // 초록색이 되기 전에 눌렀음
}

// 걸린 시간에 붙이는 한마디
fun reactionComment(ms: Long): String = when {
    ms < 250 -> "번개 같은 손"
    ms < 400 -> "꽤 빠른 편"
    ms < 700 -> "평범한 사람"
    else -> "혹시 졸았나요"
}

// 화면. 상태를 갖지 않고, 받은 상태를 그리고 눌린 것을 알리기만 합니다
@Composable
fun ReactionScreen(state: GameState, best: Long?, onTap: () -> Unit) {
    val color = when (state) {
        GameState.Waiting -> Color(0xFFC62828)
        GameState.Go -> Color(0xFF2E7D32)
        else -> Color(0xFF37474F)
    }
    val title = when (state) {
        GameState.Ready -> "화면을 누르면 시작"
        GameState.Waiting -> "초록색이 되면 누르세요"
        GameState.Go -> "지금!"
        is GameState.Result -> "${state.ms}ms"
        GameState.TooEarly -> "너무 빨랐어요"
    }
    val sub = when (state) {
        is GameState.Result -> reactionComment(state.ms) + "\n화면을 누르면 다시"
        GameState.TooEarly -> "초록색이 된 뒤에 눌러야 합니다\n화면을 누르면 다시"
        else -> ""
    }
    Box(
        modifier = Modifier.fillMaxSize().background(color).clickable(onClick = onTap).padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(12.dp))
            Text(sub, color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(24.dp))
            Text(if (best == null) "최고 기록 없음" else "최고 기록 ${best}ms", color = Color.White, fontSize = 16.sp)
        }
    }
}
