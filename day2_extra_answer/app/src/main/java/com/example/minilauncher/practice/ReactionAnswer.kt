package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
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

// 2일차 8교시 정답: 반응 속도 게임.
// 기다리는 시간(waitMs)과 지금 시각(now)을 밖에서 넣을 수 있게 해 두어, 테스트에서는 정해진 값으로 바꿔 끼웁니다
class ReactionAnswerViewModel(
    private val waitMs: () -> Long = { Random.nextLong(1000, 4000) },
    private val now: () -> Long = { System.currentTimeMillis() },
) : ViewModel() {

    private val _state = MutableStateFlow<GameState>(GameState.Ready)
    val state: StateFlow<GameState> = _state.asStateFlow()

    private val _best = MutableStateFlow<Long?>(null)
    val best: StateFlow<Long?> = _best.asStateFlow()

    private var job: Job? = null      // 초록색을 기다리는 코루틴
    private var goAt = 0L             // 초록색이 된 시각

    fun onTap() {
        when (_state.value) {
            GameState.Waiting -> {
                job?.cancel()                         // 기다리던 것을 취소합니다. 안 하면 조금 뒤 초록색이 되어 버립니다
                _state.value = GameState.TooEarly
            }
            GameState.Go -> {
                val ms = now() - goAt
                _state.value = GameState.Result(ms)
                val best = _best.value
                if (best == null || ms < best) _best.value = ms
            }
            else -> start()
        }
    }

    private fun start() {
        _state.value = GameState.Waiting
        job = viewModelScope.launch {
            delay(waitMs())                           // 기다리는 동안에도 화면은 멈추지 않습니다
            goAt = now()
            _state.value = GameState.Go
        }
    }
}

class ReactionAnswerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val vm: ReactionAnswerViewModel = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                val best by vm.best.collectAsStateWithLifecycle()
                ReactionScreen(state = state, best = best, onTap = vm::onTap)
            }
        }
    }
}
