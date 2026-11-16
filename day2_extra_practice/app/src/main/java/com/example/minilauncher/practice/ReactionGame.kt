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
// 정답은 day2_extra_answer 폴더에 있습니다. 먼저 만들어 보고 엽니다

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
    //        아래 두 줄(private var)은 onTap 함수 밖, 클래스 안에 둡니다
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
