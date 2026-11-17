package com.example.minilauncher.practice

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
import com.example.minilauncher.ui.theme.LauncherBackground
import org.json.JSONArray

// 2일차 오전 과제 "복습 퀴즈" 앱에서 고치지 않고 그대로 쓰는 부분입니다.
// 주소, 문제 한 개, 받은 글자를 목록으로 바꾸기, 화면 상태, 화면이 들어 있습니다.

// 문제 목록이 있는 주소. 브라우저에 넣으면 받아 올 글자(JSON)가 그대로 보입니다
const val QUIZ_URL = "https://raw.githubusercontent.com/bokchidevchan/android-mini-launcher-course/main/docs/api/quiz.json"

data class Quiz(val question: String, val answer: String)

// 받은 글자를 목록으로 바꿉니다. 글자는 [{"q": "질문", "a": "답"}, ...] 모양입니다
fun parseQuizzes(text: String): List<Quiz> {
    val array = JSONArray(text)
    return List(array.length()) { i ->
        val item = array.getJSONObject(i)
        Quiz(question = item.getString("q"), answer = item.getString("a"))
    }
}

// 화면은 넷 중 하나입니다. 동시에 둘일 수 없습니다
sealed interface QuizState {
    data object Ready : QuizState                         // 아직 아무것도 안 함
    data object Loading : QuizState                       // 받아 오는 중
    data class Success(val quiz: Quiz) : QuizState        // 받아 옴
    data class Error(val message: String) : QuizState     // 못 받아 옴
}

// 상태를 받아서 그리기만 합니다. 버튼이 눌리면 onLoad 로 알립니다
@Composable
fun QuizScreen(state: QuizState, onLoad: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("복습 퀴즈", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            when (state) {
                QuizState.Ready -> Text("버튼을 눌러 하나 받아 오세요")
                QuizState.Loading -> {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(12.dp))
                    Text("받아 오는 중")
                }
                is QuizState.Success -> QuizCard(state.quiz)
                is QuizState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
            }
        }
        Button(onClick = onLoad, enabled = state != QuizState.Loading, modifier = Modifier.fillMaxWidth()) {
            Text("하나 받아 오기")
        }
    }
}

@Composable
private fun QuizCard(quiz: Quiz) {
    // 문제가 바뀌면 답을 다시 가립니다
    var opened by remember(quiz) { mutableStateOf(false) }
    Text(quiz.question, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
    Spacer(Modifier.height(20.dp))
    if (opened) {
        Text(quiz.answer, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
    } else {
        FilledTonalButton(onClick = { opened = true }) { Text("정답 보기") }
    }
}
