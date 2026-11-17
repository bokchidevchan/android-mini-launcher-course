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

// 2일차 오전 직접 만들기: 복습 퀴즈 앱. 완성본은 같은 폴더의 QuizApp.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "복습 퀴즈 직접" 아이콘을 누릅니다.
// 화면(QuizScreen), 상태(QuizState), 주소(QUIZ_URL), parseQuizzes 는 QuizApp.kt 의 것을 그대로 씁니다.
// [1] 부터 [4] 까지 차례로 채웁니다. 하나 채울 때마다 Run 해서 "확인"에 적힌 대로 되는지 봅니다.
// 채울 코드는 번호 아래 주석에 있습니다. 주석 기호(//)를 떼고 쓰면 됩니다. 필요한 import 는 위에 다 있습니다.
// 단, "옮겨 씁니다"라고 적힌 번호는 주석을 그 자리에서 풀지 않고, 적힌 자리로 옮겨서 씁니다.
// 위의 import 가운데 회색으로 보이는 것은 아직 안 써서 그런 것이니 그대로 둡니다.

// [2] 인터넷에서 문제 목록을 받아 옵니다. 네트워크는 오래 걸릴 수 있어서 뒤쪽 스레드(Dispatchers.IO)에서 합니다
//     아래 주석을 풀고, 그 밑에 원래 있던 myFetchQuizzes 함수 세 줄(suspend fun 줄부터 닫는 } 까지)은 통째로 지웁니다
//
//     suspend fun myFetchQuizzes(): List<Quiz> = withContext(Dispatchers.IO) {
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
//     확인: 아직 화면은 그대로입니다. [3] 까지 해야 받아 온 문제가 보입니다
suspend fun myFetchQuizzes(): List<Quiz> {
    return listOf(Quiz("아직 [2] 를 안 채웠어요", "myFetchQuizzes 를 채웁니다"))
}

class MyQuizViewModel : ViewModel() {
    private val _state = MutableStateFlow<QuizState>(QuizState.Ready)
    val state: StateFlow<QuizState> = _state.asStateFlow()

    // 버튼이 눌리면 불립니다
    fun load() {
        // [1] 상태를 바꾸면 화면이 따라 바뀌는지 먼저 봅니다. 아래 한 줄을 씁니다
        //
        //     _state.value = QuizState.Success(Quiz("화면 그리기와 터치를 맡는 스레드는?", "메인 스레드"))
        //
        //     확인: "하나 받아 오기"를 누르면 문제가 뜨고, "정답 보기"를 누르면 답이 보입니다

        // [3] [1] 의 줄을 지우고, 받아 오는 중으로 바꾼 뒤 코루틴에서 받아 옵니다
        //
        //     _state.value = QuizState.Loading
        //     viewModelScope.launch {
        //         _state.value = QuizState.Success(myFetchQuizzes().random())
        //     }
        //
        //     확인: 누를 때마다 인터넷에서 받아 온 문제 가운데 하나가 나옵니다 (같은 문제가 또 나올 수도 있습니다).
        //     에뮬레이터를 비행기 모드로 바꾸고 누르면 앱이 꺼집니다. Logcat 에서 FATAL EXCEPTION 을 찾아봅니다

        // [4] 실패를 잡습니다. 아래 주석은 이 자리에서 풀지 않습니다.
        //     위 [3] 의 launch { } 안에 있는 한 줄(_state.value = QuizState.Success(...))을 지우고, 그 자리에 아래 일곱 줄을 옮겨 씁니다
        //
        //         _state.value = try {
        //             QuizState.Success(myFetchQuizzes().random())
        //         } catch (e: IOException) {
        //             QuizState.Error("인터넷 연결을 확인해 주세요")
        //         } catch (e: JSONException) {
        //             QuizState.Error("받은 내용을 읽지 못했습니다")
        //         }
        //
        //     확인: 비행기 모드에서 눌러도 앱이 안 꺼지고 빨간 글자가 나옵니다. 비행기 모드를 끄고 다시 누르면 문제가 나옵니다
    }
}
// 더 해 보기 1: [3] 의 myFetchQuizzes() 앞줄에 delay(1000) 을 넣으면 "받아 오는 중"이 1초 동안 보입니다 (import kotlinx.coroutines.delay)
// 더 해 보기 2: [2] 의 Dispatchers.IO 를 Dispatchers.Main 으로 바꾸면 어떻게 될까요? Logcat 에서 NetworkOnMainThreadException 을 찾아봅니다

class MyQuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val vm: MyQuizViewModel = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                QuizScreen(state = state, onLoad = vm::load)
            }
        }
    }
}
