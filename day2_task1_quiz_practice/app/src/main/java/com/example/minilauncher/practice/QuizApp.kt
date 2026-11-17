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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

// 2일차 오전 과제 "복습 퀴즈" 의 뼈대입니다.
// [1] [2] [3] 자리를 채웁니다. 무엇을 쓰는지는 README.md 에 있습니다. 완성본은 day2_task1_quiz_answer 폴더입니다.
// 위의 import 가운데 회색인 것은 채우고 나면 쓰입니다.

// [1] 인터넷에서 문제 목록을 받아 옵니다. 오래 걸릴 수 있어서 IO 스레드에서 합니다
suspend fun fetchQuizzes(): List<Quiz> = withContext(Dispatchers.IO) {
    // [1] 여기를 채웁니다. 지금은 빈 목록을 돌려줍니다
    emptyList()
}

class QuizViewModel : ViewModel() {
    // 화면이 보는 상태입니다. 처음에는 Ready (아직 아무것도 안 함)
    private val _state = MutableStateFlow<QuizState>(QuizState.Ready)
    val state: StateFlow<QuizState> = _state.asStateFlow()

    // "하나 받아 오기" 버튼을 누르면 불립니다
    fun load() {
        // [2] 여기를 채웁니다. [2] 를 쓸 때 아래 안내 줄은 지웁니다
        _state.value = QuizState.Error("아직 [2] 를 안 채웠어요")

        // [3] 은 [2] 를 다 한 뒤에 합니다
    }
}

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val vm: QuizViewModel = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                QuizScreen(state = state, onLoad = vm::load)
            }
        }
    }
}
