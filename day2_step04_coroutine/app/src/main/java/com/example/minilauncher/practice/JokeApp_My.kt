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

// 직접 만들어 보는 파일입니다. 완성본은 같은 폴더의 JokeApp.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "아재 개그 연습" 아이콘을 누릅니다.
// 화면(JokeScreen)과 상태(JokeState), 주소(JOKE_URL), parseJokes 는 JokeApp.kt 의 것을 그대로 씁니다.
// 아래 1번과 2번, 두 군데만 채우면 완성본과 똑같이 움직입니다. 필요한 import 는 위에 다 있습니다.

// 1. 인터넷에서 개그 목록을 받아 옵니다
//    - withContext(Dispatchers.IO) { } 안에서 합니다
//    - URL(JOKE_URL).openConnection() as HttpURLConnection 으로 연결을 만듭니다
//    - connection.inputStream.bufferedReader().readText() 로 글자를 읽고 parseJokes(글자) 로 목록을 만듭니다
//    - 끝나면 connection.disconnect()
suspend fun myFetchJokes(): List<Joke> {
    // 여기에 씁니다. 지금은 빈 목록을 돌려줍니다
    return emptyList()
}

class MyJokeViewModel : ViewModel() {
    private val _state = MutableStateFlow<JokeState>(JokeState.Ready)
    val state: StateFlow<JokeState> = _state.asStateFlow()

    // 2. 버튼이 눌리면 불립니다
    //    - 먼저 _state.value 를 JokeState.Loading 으로 바꿉니다
    //    - viewModelScope.launch { } 안에서 myFetchJokes() 를 부릅니다
    //    - 성공하면 JokeState.Success(목록.random()), IOException 이 나면 JokeState.Error("...") 를 넣습니다
    fun load() {
        // 여기에 씁니다
    }
}

class MyJokeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val vm: MyJokeViewModel = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                JokeScreen(state = state, onLoad = vm::load)
            }
        }
    }
}
