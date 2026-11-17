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

// 2일차 오전 과제: 복습 퀴즈 앱 만들기 (뼈대).
// Run 한 뒤 에뮬레이터의 앱 목록에서 "복습 퀴즈" 아이콘을 누릅니다.
// 화면과 상태는 같은 폴더의 QuizScreen.kt 에 만들어져 있습니다. 이 파일의 [1] [2] [3] 만 채웁니다.
// 하나 채울 때마다 Run 해서 "확인"에 적힌 대로 되는지 봅니다. 채울 코드는 번호 아래 주석에 있습니다.
// 완성본은 day2_task1_quiz_answer 폴더에 있습니다. 먼저 만들어 보고 엽니다.
// 위의 import 가운데 회색으로 보이는 것은 아직 안 써서 그런 것이니 그대로 둡니다.

// [2] 인터넷에서 문제 목록을 받아 옵니다. 오래 걸릴 수 있어서 Dispatchers.IO 에서 합니다.
//     아래 함수를 통째로 지우고, 주석의 함수로 바꿉니다
//
//     suspend fun fetchQuizzes(): List<Quiz> = withContext(Dispatchers.IO) {
//         val connection = URL(QUIZ_URL).openConnection() as HttpURLConnection
//         connection.connectTimeout = 5000
//         connection.readTimeout = 5000
//         try {
//             val text = connection.inputStream.bufferedReader().readText()
//             parseQuizzes(text)
//         } finally {
//             connection.disconnect()
//         }
//     }
//
//     확인: 화면은 아직 그대로입니다. [3] 까지 해야 받아 온 문제가 보입니다
suspend fun fetchQuizzes(): List<Quiz> {
    return listOf(Quiz("아직 [2] 를 안 채웠어요", "fetchQuizzes 를 채웁니다"))
}

class QuizViewModel : ViewModel() {
    private val _state = MutableStateFlow<QuizState>(QuizState.Ready)
    val state: StateFlow<QuizState> = _state.asStateFlow()

    // 버튼이 눌리면 불립니다
    fun load() {
        // [1] 상태를 바꾸면 화면이 따라 바뀌는지 먼저 봅니다. 아래 한 줄을 씁니다
        //
        //     _state.value = QuizState.Success(Quiz("연습용 문제입니다", "연습용 답"))
        //
        //     확인: "하나 받아 오기"를 누르면 "연습용 문제입니다"가 뜨고, "정답 보기"를 누르면 답이 보입니다

        // [3] [1] 의 줄을 지우고, 아래 주석의 코드를 씁니다.
        //     받아 오는 중으로 바꾸고, 코루틴에서 받아 와서 상태에 넣습니다. 실패하면 안내 글자를 넣습니다
        //
        //     _state.value = QuizState.Loading
        //     viewModelScope.launch {
        //         _state.value = try {
        //             QuizState.Success(fetchQuizzes().random())
        //         } catch (e: IOException) {
        //             QuizState.Error("인터넷 연결을 확인해 주세요")
        //         } catch (e: JSONException) {
        //             QuizState.Error("받은 내용을 읽지 못했습니다")
        //         }
        //     }
        //
        //     확인: 누르면 인터넷에서 받아 온 문제가 나옵니다 (같은 문제가 또 나올 수도 있습니다)
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
