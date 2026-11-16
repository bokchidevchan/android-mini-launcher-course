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

// 2일차 8교시: 반응 속도 게임.
// 화면을 누르면 빨간색이 되고, 조금 뒤 초록색으로 바뀝니다. 초록색이 되자마자 누르면 몇 ms 걸렸는지 나옵니다.
// 실행: 에뮬레이터의 앱 목록에서 "반응 속도 게임 연습" 아이콘.
// 화면(ReactionScreen)은 만들어져 있습니다. ReactionViewModel 의 onTap 을 직접 만듭니다.
// 정답: ReactionAnswer.kt ("반응 속도 게임 정답" 아이콘)

// 게임의 상태. 화면은 이 다섯 가지 가운데 하나를 그립니다
sealed interface GameState {
    data object Ready : GameState                  // 처음. 누르면 시작
    data object Waiting : GameState                // 빨간색. 초록색을 기다리는 중
    data object Go : GameState                     // 초록색. 지금 눌러야 함
    data class Result(val ms: Long) : GameState    // 걸린 시간
    data object TooEarly : GameState               // 초록색이 되기 전에 눌렀음
}

class ReactionViewModel : ViewModel() {

    private val _state = MutableStateFlow<GameState>(GameState.Ready)
    val state: StateFlow<GameState> = _state.asStateFlow()

    private val _best = MutableStateFlow<Long?>(null)      // 가장 빨랐던 기록
    val best: StateFlow<Long?> = _best.asStateFlow()

    // 화면이 눌릴 때마다 불립니다. 여기를 직접 만듭니다. 요구사항은 교안의 2일차 8교시 장에 있습니다.
    //   지금 상태가 Ready, Result, TooEarly 면: Waiting 으로 바꾸고, 1~4초 가운데 아무 시간이나 기다린 뒤 Go 로 바꿉니다
    //   지금 상태가 Waiting 이면: 기다리던 코루틴을 취소하고 TooEarly 로 바꿉니다
    //   지금 상태가 Go 면: 초록색이 된 뒤 지금까지 걸린 시간을 재서 Result 로 바꿉니다
    // (힌트) 필요한 것(Job, viewModelScope, launch, delay, Random)은 위에 import 해 두었습니다
    //        private var job: Job? = null          // 초록색을 기다리는 코루틴
    //        private var goAt = 0L                 // 초록색이 된 시각
    //        job = viewModelScope.launch { delay(Random.nextLong(1000, 4000)); goAt = System.currentTimeMillis(); _state.value = GameState.Go }
    //        job?.cancel()
    //        걸린 시간은 System.currentTimeMillis() - goAt
    //        when (_state.value) { GameState.Waiting -> ...; GameState.Go -> ...; else -> ... }
    fun onTap() {
    }
}

class ReactionPracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val vm: ReactionViewModel = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                val best by vm.best.collectAsStateWithLifecycle()
                ReactionScreen(state = state, best = best, onTap = vm::onTap)
            }
        }
    }
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
