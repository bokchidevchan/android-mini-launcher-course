package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

// 2일차 오전: 네트워크에서 받아 오는 앱 "아재 개그".
// Run 한 뒤 에뮬레이터의 앱 목록에서 "아재 개그" 아이콘을 누릅니다.
// 버튼을 누르면 인터넷에서 개그 목록을 받아 와 하나를 보여 줍니다.
// 읽는 순서: 1. 받아 오는 함수  2. 화면 상태  3. ViewModel  4. 화면
// 직접 써 보고 싶으면 같은 폴더의 JokeApp_My.kt 를 엽니다.

// 개그 목록이 있는 주소. 브라우저에 넣으면 받아 올 글자(JSON)가 그대로 보입니다
const val JOKE_URL = "https://raw.githubusercontent.com/bokchidevchan/android-mini-launcher-course/main/docs/api/jokes.json"

data class Joke(val question: String, val answer: String)

// ---------------------------------------------------------------- 1. 받아 오는 함수
// 네트워크는 오래 걸릴 수 있어서 메인 스레드에서 하면 안 됩니다. withContext(Dispatchers.IO) 로 뒤쪽 스레드에 보냅니다.
// suspend 함수라서 부르는 쪽은 결과가 올 때까지 "기다리지만", 그동안 메인 스레드는 화면을 계속 그립니다
suspend fun fetchJokes(): List<Joke> = withContext(Dispatchers.IO) {
    val connection = URL(JOKE_URL).openConnection() as HttpURLConnection
    connection.connectTimeout = 5000        // 5초 안에 연결이 안 되면 실패로 봅니다
    connection.readTimeout = 5000
    try {
        val text = connection.inputStream.bufferedReader().readText()    // 서버가 준 글자 전체
        parseJokes(text)
    } finally {
        connection.disconnect()             // 성공하든 실패하든 연결을 닫습니다
    }
    // 바꿔 보기: 맨 위의 Dispatchers.IO 를 Dispatchers.Main 으로 바꾸면 버튼을 누를 때 앱이 꺼집니다.
    // Logcat 에서 NetworkOnMainThreadException 을 찾아봅니다. 확인했으면 되돌립니다
}

// 받은 글자를 목록으로 바꿉니다. 글자는 [{"q": "질문", "a": "답"}, ...] 모양입니다
fun parseJokes(text: String): List<Joke> {
    val array = JSONArray(text)
    return List(array.length()) { i ->
        val item = array.getJSONObject(i)
        Joke(question = item.getString("q"), answer = item.getString("a"))
    }
}

// ---------------------------------------------------------------- 2. 화면 상태
// 화면은 넷 중 하나입니다. 동시에 둘일 수 없습니다
sealed interface JokeState {
    data object Ready : JokeState                         // 아직 아무것도 안 함
    data object Loading : JokeState                       // 받아 오는 중
    data class Success(val joke: Joke) : JokeState        // 받아 옴
    data class Error(val message: String) : JokeState     // 못 받아 옴
}

// ---------------------------------------------------------------- 3. ViewModel
class JokeViewModel : ViewModel() {
    private val _state = MutableStateFlow<JokeState>(JokeState.Ready)
    val state: StateFlow<JokeState> = _state.asStateFlow()

    fun load() {
        _state.value = JokeState.Loading                   // 먼저 "받아 오는 중"으로 바꿉니다
        viewModelScope.launch {                            // 화면이 사라지면 같이 취소됩니다
            _state.value = try {
                JokeState.Success(fetchJokes().random())   // 받아 온 것 가운데 하나를 고릅니다
            } catch (e: IOException) {                     // 인터넷이 안 될 때
                JokeState.Error("인터넷 연결을 확인해 주세요")
            } catch (e: JSONException) {                   // 받은 글자가 약속한 모양이 아닐 때
                JokeState.Error("받은 내용을 읽지 못했습니다")
            }
        }
        // 바꿔 보기: 에뮬레이터를 비행기 모드로 바꾸고 버튼을 누르면 어떤 화면이 나올까요?
    }
}

class JokeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val vm: JokeViewModel = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                JokeScreen(state = state, onLoad = vm::load)
            }
        }
    }
}

// ---------------------------------------------------------------- 4. 화면
// 상태를 받아서 그리기만 합니다. 버튼이 눌리면 onLoad 로 알립니다
@Composable
fun JokeScreen(state: JokeState, onLoad: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("아재 개그", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            when (state) {
                JokeState.Ready -> Text("버튼을 눌러 하나 받아 오세요")
                JokeState.Loading -> {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(12.dp))
                    Text("받아 오는 중")
                }
                is JokeState.Success -> JokeCard(state.joke)
                is JokeState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
            }
        }
        Button(onClick = onLoad, enabled = state != JokeState.Loading, modifier = Modifier.fillMaxWidth()) {
            Text("하나 받아 오기")
        }
    }
}

@Composable
private fun JokeCard(joke: Joke) {
    // 개그가 바뀌면 답을 다시 가립니다
    var opened by remember(joke) { mutableStateOf(false) }
    Text(joke.question, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
    Spacer(Modifier.height(20.dp))
    if (opened) {
        Text(joke.answer, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
    } else {
        FilledTonalButton(onClick = { opened = true }) { Text("정답 보기") }
    }
}
