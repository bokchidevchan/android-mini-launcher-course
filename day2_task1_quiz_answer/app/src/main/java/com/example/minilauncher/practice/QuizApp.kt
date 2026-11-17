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

// 2일차 오전 과제 "복습 퀴즈" 의 완성본입니다.
// 버튼을 누르면 인터넷에서 문제 목록을 받아 와 하나를 보여 줍니다.
// [1] [2] [3] 이 뼈대 프로젝트에서 채우는 자리입니다.

// [1] 인터넷에서 문제 목록을 받아 옵니다. 오래 걸릴 수 있어서 IO 스레드에서 합니다
suspend fun fetchQuizzes(): List<Quiz> = withContext(Dispatchers.IO) {
    val text = URL(QUIZ_URL).readText()     // 주소에 있는 글자를 모두 읽어 옴
    parseQuizzes(text)                      // 글자를 문제 목록으로 바꿈
}

class QuizViewModel : ViewModel() {
    // 화면이 보는 상태입니다. 처음에는 Ready (아직 아무것도 안 함)
    private val _state = MutableStateFlow<QuizState>(QuizState.Ready)
    val state: StateFlow<QuizState> = _state.asStateFlow()

    // "하나 받아 오기" 버튼을 누르면 불립니다
    fun load() {
        // [2] 받아 오는 중으로 바꾸고, 코루틴에서 받아 와 하나를 보여 줍니다
        _state.value = QuizState.Loading
        viewModelScope.launch {
            // [3] 인터넷이 안 되면 실패 글자를 보여 줍니다
            try {
                val quizzes = fetchQuizzes()
                _state.value = QuizState.Success(quizzes.random())
            } catch (e: Exception) {
                _state.value = QuizState.Error("받아 오지 못했습니다. 인터넷 연결을 확인해 주세요")
            }
        }
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
