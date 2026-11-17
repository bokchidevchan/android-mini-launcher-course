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
import org.json.JSONException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

// 2일차 오전 과제 "복습 퀴즈" 앱의 완성본입니다.
// Run 하면 복습 퀴즈 화면이 바로 뜹니다.
// 버튼을 누르면 인터넷에서 문제 목록을 받아 와 하나를 보여 줍니다. 문제는 어제와 오늘 배운 내용입니다.
// 화면과 상태는 같은 폴더의 QuizScreen.kt 에 있습니다. 번호 [2] [3] 은 뼈대 프로젝트의 같은 번호 자리입니다.

// [2] 인터넷에서 받아 오기
suspend fun fetchQuizzes(): List<Quiz> = withContext(Dispatchers.IO) {
    val connection = URL(QUIZ_URL).openConnection() as HttpURLConnection
    connection.connectTimeout = 5000        // 5초 안에 연결이 안 되면 실패로 봅니다
    connection.readTimeout = 5000
    try {
        val text = connection.inputStream.bufferedReader().readText()    // 서버가 준 글자 전체
        parseQuizzes(text)
    } finally {
        connection.disconnect()             // 성공하든 실패하든 연결을 닫습니다
    }
}

class QuizViewModel : ViewModel() {
    private val _state = MutableStateFlow<QuizState>(QuizState.Ready)
    val state: StateFlow<QuizState> = _state.asStateFlow()

    // [3] 받아 오는 중으로 바꾸고, 코루틴에서 받아 와 상태에 넣기
    fun load() {
        _state.value = QuizState.Loading                   // 먼저 "받아 오는 중"으로 바꿉니다
        viewModelScope.launch {                            // 화면이 사라지면 같이 취소됩니다
            _state.value = try {
                QuizState.Success(fetchQuizzes().random())   // 받아 온 것 가운데 하나를 고릅니다
            } catch (e: IOException) {                     // 인터넷이 안 될 때
                QuizState.Error("인터넷 연결을 확인해 주세요")
            } catch (e: JSONException) {                   // 받은 글자가 약속한 모양이 아닐 때
                QuizState.Error("받은 내용을 읽지 못했습니다")
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
