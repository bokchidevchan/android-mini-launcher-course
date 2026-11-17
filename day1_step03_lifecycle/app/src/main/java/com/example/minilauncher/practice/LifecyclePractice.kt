package com.example.minilauncher.practice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 오후 생명주기 실습. 계산기 화면을 열었다 닫으며, 화면이 없어질 때 계산하던 값이 어떻게 되는지 봅니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "생명주기 연습" 아이콘을 누릅니다. Logcat 검색창에는 tag:LifePractice 를 넣습니다.
//
// ===== 보여 주기 (앞 화면을 보며 따라옵니다) =====
//   [1] 계산기 열기를 누릅니다. 입구 화면은 onStop 까지만 찍히고 onDestroy 는 안 찍힙니다 (뒤에 깔려 있을 뿐입니다)
//   [2] 계산기에서 뒤로 가기를 누릅니다. 계산기 onDestroy 가 찍힙니다. 화면이 없어진 것입니다
//   [3] 12+3 을 누르고 화면을 돌립니다. 세 줄 가운데 무엇이 0 으로 돌아가는지 봅니다.
//       이번에는 뒤로 갔다가 계산기를 다시 엽니다. 세 줄이 모두 0 입니다
//
// ===== 같이 해 보기 (한 덩어리씩 복사합니다) =====
//   같은 폴더의 LifecyclePractice_My.kt 를 엽니다. 이 파일에서 [1], [2], [3] 표시가 붙은 곳을 차례로 가져가 붙입니다

private const val TAG = "LifePractice"

// 계산기 글자판. 지금 보이는 글자(text)와 눌린 키(key)를 받아, 다음에 보일 글자를 돌려줍니다
fun pressKey(text: String, key: String): String = when {
    key == "C" -> "0"
    key == "=" -> calculate(text)
    key in "+-×" -> text.trimEnd('+', '-', '×') + key       // 기호를 연달아 누르면 마지막 것으로 바꿉니다
    text.length >= 12 -> text                                // 너무 길면 더 받지 않습니다
    text == "0" -> key
    else -> text + key
}

// "12+3×2" 를 왼쪽부터 차례로 계산합니다 (곱하기를 먼저 하지 않는 단순한 계산기입니다)
private fun calculate(text: String): String {
    var total = 0L
    var number = ""
    var sign = '+'
    for (c in text.trimEnd('+', '-', '×') + "+") {           // 끝에 + 를 붙여 마지막 숫자까지 계산되게 합니다
        if (c.isDigit()) { number += c; continue }
        if (number.isNotEmpty()) {
            val n = number.toLong()
            total = when (sign) { '+' -> total + n; '-' -> total - n; else -> total * n }
            number = ""
        }
        sign = c
    }
    return total.toString()
}

// [3] 화면이 다시 만들어져도 남는 금고. 화면을 돌리면 Activity 는 새로 만들어지지만 이 객체는 그대로입니다
class CalculatorViewModel : ViewModel() {
    var text by mutableStateOf("0")
        private set

    fun press(key: String) { text = pressKey(text, key) }

    // 화면을 정말로 닫을 때(뒤로 가기, finish) 한 번 불립니다. 돌릴 때는 안 불립니다. 이때 금고도 같이 없어집니다
    override fun onCleared() { Log.d(TAG, "CalculatorViewModel onCleared") }
}

// 입구 화면. 계산기 화면으로 가는 단추만 있습니다
class LifecyclePracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "입구 onCreate")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                EntranceLayout(
                    // [1] 다른 화면 열기. Intent 는 "이 화면을 열어 주세요"라는 쪽지입니다
                    onOpen = {
                        val intent = Intent(this@LifecyclePracticeActivity, CalculatorActivity::class.java)
                        startActivity(intent)
                    },
                    onClose = { finish() },      // 이 화면을 닫습니다
                )
            }
        }
    }

    // 계산기를 열면 입구는 onStop 까지만 갑니다. 없어지지 않고 뒤에서 기다립니다
    override fun onStop() { super.onStop(); Log.d(TAG, "입구 onStop: 계산기에 가려짐") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "입구 onDestroy") }
}

// 계산기 화면. 뒤로 가기로 닫으면 없어지고, 다시 열면 처음부터 새로 만들어집니다
class CalculatorActivity : ComponentActivity() {

    // [2] 시스템이 때마다 불러 주는 함수들. 내가 부르는 것이 아닙니다. 불릴 때마다 한 줄씩 찍습니다
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // savedInstanceState 가 null 이 아니면 "방금 전 화면을 되살리는 중"입니다 (화면을 돌렸을 때)
        Log.d(TAG, "계산기 onCreate (되살리는 중인가: ${savedInstanceState != null})")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                // [3] 같은 계산을 세 가지 방법으로 기억합니다
                var remembered by remember { mutableStateOf("0") }            // 화면을 돌리면 사라짐
                var saved by rememberSaveable { mutableStateOf("0") }         // 화면을 돌려도 남음
                val vm: CalculatorViewModel = viewModel()                     // 화면을 돌려도 남음
                // 뒤로 가기로 화면을 닫으면 셋 다 사라집니다. 사용자가 끝낸 화면이라 남길 이유가 없기 때문입니다
                CalculatorLayout(
                    remembered = remembered,
                    saved = saved,
                    inViewModel = vm.text,
                    onKey = { key ->
                        remembered = pressKey(remembered, key)
                        saved = pressKey(saved, key)
                        vm.press(key)
                    },
                    onClose = { finish() },      // 뒤로 가기와 같습니다
                )
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "계산기 onStart: 화면이 보이기 시작") }
    override fun onResume() { super.onResume(); Log.d(TAG, "계산기 onResume: 터치를 받기 시작") }
    override fun onPause() { super.onPause(); Log.d(TAG, "계산기 onPause: 터치를 못 받게 됨") }
    override fun onStop() { super.onStop(); Log.d(TAG, "계산기 onStop: 화면이 안 보이게 됨") }
    override fun onDestroy() {
        super.onDestroy()
        // isChangingConfigurations 가 true 면 화면을 돌려서 다시 만드는 중이고, false 면 정말로 닫는 중입니다
        Log.d(TAG, "계산기 onDestroy (돌리는 중인가: $isChangingConfigurations)")
    }
    // 바꿔 보기: onRestart 도 같은 모양으로 더해 봅니다. 홈으로 나갔다 돌아올 때 onStart 앞에 찍힙니다
}

// 입구 화면 모양. 연습 파일도 이 모양을 그대로 씁니다
@Composable
fun EntranceLayout(onOpen: () -> Unit, onClose: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("생명주기 연습", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Button(onClick = onOpen, modifier = Modifier.fillMaxWidth()) { Text("계산기 열기") }
        FilledTonalButton(onClick = onClose, modifier = Modifier.fillMaxWidth()) { Text("이 화면 닫기") }
        Text("계산기에서 계산한 뒤 뒤로 왔다가 다시 열어 보세요")
    }
}

// 계산기 화면 모양. 받은 값을 그리고, 눌린 키를 알리기만 합니다. 연습 파일도 이 모양을 그대로 씁니다
@Composable
fun CalculatorLayout(
    remembered: String, saved: String, inViewModel: String,
    onKey: (String) -> Unit, onClose: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("계산기", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("remember: $remembered", style = MaterialTheme.typography.titleLarge)
        Text("rememberSaveable: $saved", style = MaterialTheme.typography.titleLarge)
        Text("ViewModel: $inViewModel", style = MaterialTheme.typography.titleLarge)
        val rows = listOf(
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("C", "0", "="),
        )
        for (row in rows) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (key in row) {
                    Button(onClick = { onKey(key) }, modifier = Modifier.weight(1f)) { Text(key) }
                }
            }
        }
        FilledTonalButton(onClick = onClose, modifier = Modifier.fillMaxWidth()) { Text("뒤로 가기") }
        Text("12+3 을 누른 뒤 화면을 돌려 보고, 뒤로 갔다가 다시 열어 보세요")
    }
}
