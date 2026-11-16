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
import androidx.compose.runtime.mutableIntStateOf
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

// 1일차 오후 생명주기 실습.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "생명주기 연습" 아이콘을 누릅니다. Logcat 검색창에는 tag:LifePractice 를 넣습니다.
//
// ===== 보여 주기 (앞 화면을 보며 따라옵니다) =====
//   [1] 화면이 뜰 때, 홈으로 나갈 때, 돌아올 때, 돌릴 때 Logcat 에 어떤 줄이 찍히는지 봅니다
//   [2] +1 을 몇 번 누르고 화면을 돌립니다. 세 숫자 가운데 무엇이 0 으로 돌아가는지 봅니다
//   [3] 다음 화면 열기를 누릅니다. 숫자가 둘째 화면으로 넘어가는 것을 봅니다
//
// ===== 같이 해 보기 (한 덩어리씩 복사합니다) =====
//   같은 폴더의 LifecyclePractice_My.kt 를 엽니다. 이 파일에서 [1], [2], [3] 표시가 붙은 곳을 차례로 가져가 붙입니다

private const val TAG = "LifePractice"
const val EXTRA_COUNT = "count"      // 둘째 화면에 넘길 값의 이름표

// [2] 화면이 다시 만들어져도 남는 금고. 화면을 돌리면 Activity 는 새로 만들어지지만 이 객체는 그대로입니다
class CounterViewModel : ViewModel() {
    var count by mutableIntStateOf(0)
        private set

    fun add() { count++ }

    // 화면을 정말로 닫을 때(뒤로 가기, finish) 한 번 불립니다. 돌릴 때는 안 불립니다
    override fun onCleared() { Log.d(TAG, "CounterViewModel onCleared") }
}

class LifecyclePracticeActivity : ComponentActivity() {

    // [1] 시스템이 때마다 불러 주는 함수들. 내가 부르는 것이 아닙니다. 불릴 때마다 한 줄씩 찍습니다
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // savedInstanceState 가 null 이 아니면 "방금 전 화면을 되살리는 중"입니다 (화면을 돌렸을 때)
        Log.d(TAG, "onCreate (되살리는 중인가: ${savedInstanceState != null})")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                // [2] 같은 숫자를 세 가지 방법으로 기억합니다
                var remembered by remember { mutableIntStateOf(0) }            // 화면을 돌리면 사라짐
                var saved by rememberSaveable { mutableIntStateOf(0) }         // 화면을 돌려도 남음
                val vm: CounterViewModel = viewModel()                         // 화면을 돌려도 남음
                LifecycleLayout(
                    remembered = remembered,
                    saved = saved,
                    inViewModel = vm.count,
                    onAdd = { remembered++; saved++; vm.add() },
                    // [3] 다른 화면 열기. Intent 는 "이 화면을 열어 주세요"라는 쪽지이고, putExtra 로 값을 같이 실어 보냅니다
                    onOpen = {
                        val intent = Intent(this@LifecyclePracticeActivity, LifecycleSecondActivity::class.java)
                        intent.putExtra(EXTRA_COUNT, vm.count)
                        startActivity(intent)
                    },
                    onClose = { finish() },      // 이 화면을 닫습니다
                )
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart: 화면이 보이기 시작") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume: 터치를 받기 시작") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause: 터치를 못 받게 됨") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop: 화면이 안 보이게 됨") }
    override fun onDestroy() {
        super.onDestroy()
        // isChangingConfigurations 가 true 면 화면을 돌려서 다시 만드는 중입니다
        Log.d(TAG, "onDestroy (돌리는 중인가: $isChangingConfigurations)")
    }
    // 바꿔 보기: onRestart 도 같은 모양으로 더해 봅니다. 홈으로 나갔다 돌아올 때 onStart 앞에 찍힙니다
}

// [3] 둘째 화면. 첫 화면이 보낸 값을 꺼내 보여 줍니다
class LifecycleSecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "둘째 화면 onCreate")
        enableEdgeToEdge()
        val received = intent.getIntExtra(EXTRA_COUNT, 0)      // 못 받았으면 0
        setContent {
            MiniLauncherTheme {
                Column(
                    Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("둘째 화면", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("받은 값: $received", style = MaterialTheme.typography.headlineSmall)
                    Button(onClick = { finish() }) { Text("돌아가기") }
                }
            }
        }
    }

    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "둘째 화면 onDestroy") }
}

// 화면 모양. 받은 값을 그리고, 눌린 것을 알리기만 합니다. 연습 파일도 이 모양을 그대로 씁니다
@Composable
fun LifecycleLayout(
    remembered: Int, saved: Int, inViewModel: Int,
    onAdd: () -> Unit, onOpen: () -> Unit, onClose: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("생명주기 연습", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("remember: $remembered", style = MaterialTheme.typography.titleLarge)
        Text("rememberSaveable: $saved", style = MaterialTheme.typography.titleLarge)
        Text("ViewModel: $inViewModel", style = MaterialTheme.typography.titleLarge)
        Button(onClick = onAdd, modifier = Modifier.fillMaxWidth()) { Text("+1") }
        FilledTonalButton(onClick = onOpen, modifier = Modifier.fillMaxWidth()) { Text("다음 화면 열기") }
        FilledTonalButton(onClick = onClose, modifier = Modifier.fillMaxWidth()) { Text("이 화면 닫기") }
        Text("+1 을 몇 번 누른 뒤 화면을 돌려 보세요")
    }
}
