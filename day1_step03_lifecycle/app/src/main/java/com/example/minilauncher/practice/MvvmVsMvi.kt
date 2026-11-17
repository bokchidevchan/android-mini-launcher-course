package com.example.minilauncher.practice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 비교 코드: MVVM 과 MVI 비교. 같은 "좋아요" 화면을 위(MVVM)와 아래(MVI)에 두 번 만들었습니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "MVVM과 MVI 비교" 아이콘을 누릅니다. Logcat 검색창에는 tag:Arch 를 넣습니다.
//
// 둘 다 화면(View)과 ViewModel 로 나누는 것은 같습니다. 다른 것은 "화면이 ViewModel 에 말하는 방법"과 "상태를 담는 방법"입니다.
//   MVVM   할 일마다 함수가 따로 있습니다 (plus(), reset()). 상태도 값마다 따로 둘 수 있습니다 (count, message)
//   MVI    들어오는 문은 하나입니다 (onIntent). 할 일은 "주문서"(Intent)로 적어서 냅니다. 상태는 한 덩어리(State)입니다
//
// 비유: MVVM 은 리모컨입니다. 버튼마다 하는 일이 따로 있습니다.
//       MVI 는 창구가 하나인 은행입니다. 무슨 일이든 신청서를 써서 한 창구에 내고, 결과는 통장 한 권에 찍힙니다.
// C++ 로 치면: MVVM 은 멤버 함수를 여러 개 부르는 방식, MVI 는 메시지(enum, struct)를 큐 하나에 넣고 switch 로 처리하는 방식
//
// 왜 이렇게 나누나
//   1. 화면에서 계산을 빼기 위해서입니다. 화면은 그리기만 하고, 값을 바꾸는 규칙은 ViewModel 에 둡니다.
//      그래야 화면 없이 ViewModel 만 테스트할 수 있고, 화면을 돌려도 값이 남습니다 (오후에 본 것)
//   2. MVI 가 한 걸음 더 가는 이유: 버튼이 많아지면 "지금 값이 왜 이렇게 됐지?"를 찾기 어렵습니다.
//      문이 하나면 그 문에 로그 한 줄만 찍어도 일어난 일이 순서대로 다 보입니다.
//      상태가 한 덩어리면 count 는 바꿨는데 message 는 안 바꾸는 실수가 줄어듭니다
//   3. 그 대신 MVI 는 적을 것이 많습니다. 버튼 하나를 더하려면 Intent 종류와 when 가지를 같이 더해야 합니다.
//      작은 화면은 MVVM 으로 충분하고, 버튼과 상태가 많은 화면에서 MVI 가 편해집니다
//
// Compose 는 MVI 만, XML 은 MVVM 만 쓰는 것은 아닙니다. MVVM 은 XML 시절에 퍼졌고 MVI 는 Compose 와 잘 맞지만,
// Compose 에서도 MVVM 을 많이 씁니다. 이 프로젝트의 LauncherViewModel 이 "Compose + MVVM" 입니다
//
// ===== 보여 주기 =====
//   1. 위쪽(MVVM)의 +1, 처음으로를 누릅니다. Logcat 에 "MVVM: plus() 호출" 처럼 함수 이름이 찍힙니다
//   2. 아래쪽(MVI)의 +1, 처음으로를 누릅니다. Logcat 에 "MVI: 받은 주문 Plus, 0 -> 1" 처럼 한 곳에서 다 찍힙니다
//   3. 아래 두 ViewModel 의 코드 길이와 모양을 비교합니다
// ===== 같이 해 보기 (한 덩어리씩 복사합니다) =====
//   같은 폴더의 MvvmVsMvi_My.kt 에서 양쪽에 "-1" 버튼을 넣어 봅니다. 고칠 곳이 몇 군데인지 세어 봅니다
private const val TAG = "Arch"

// ================================================================ MVVM
// 상태가 값마다 따로 있고, 할 일마다 함수가 따로 있습니다
class LikeMvvmViewModel : ViewModel() {
    var count by mutableIntStateOf(0)
        private set
    var message by mutableStateOf("눌러 보세요")
        private set

    fun plus() {
        Log.d(TAG, "MVVM: plus() 호출")
        count++
        message = "좋아요 ${count}개"       // 이 줄을 빼먹어도 빨간 줄이 안 생깁니다. 값 두 개를 내가 맞춰야 합니다
    }

    fun reset() {
        Log.d(TAG, "MVVM: reset() 호출")
        count = 0
        message = "처음으로 돌아왔습니다"
    }
}

// ================================================================ MVI
// 상태: 화면에 필요한 값을 한 덩어리로 묶습니다. 화면은 이것 하나만 보고 그립니다
data class LikeState(
    val count: Int = 0,
    val message: String = "눌러 보세요",
)

// 주문서: 화면에서 일어날 수 있는 일의 목록입니다. 여기에 없는 일은 일어날 수 없습니다
sealed interface LikeIntent {
    data object Plus : LikeIntent
    data object Reset : LikeIntent
}

class LikeMviViewModel : ViewModel() {
    var state by mutableStateOf(LikeState())
        private set

    // 들어오는 문은 이 함수 하나입니다. "지금 상태 + 주문 = 새 상태" 를 한곳에서 정합니다
    fun onIntent(intent: LikeIntent) {
        val old = state
        state = when (intent) {
            LikeIntent.Plus -> old.copy(count = old.count + 1, message = "좋아요 ${old.count + 1}개")
            LikeIntent.Reset -> LikeState(message = "처음으로 돌아왔습니다")
        }
        // 문이 하나라서 로그도 한 줄이면 됩니다
        Log.d(TAG, "MVI: 받은 주문 $intent, ${old.count} -> ${state.count}")
    }
}

// ================================================================ 화면
class MvvmVsMviActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val mvvm: LikeMvvmViewModel = viewModel()
                val mvi: LikeMviViewModel = viewModel()
                Column(Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    // MVVM: 값을 하나씩 꺼내 쓰고, 버튼마다 다른 함수를 부릅니다
                    LikePanel(
                        title = "MVVM",
                        count = mvvm.count,
                        message = mvvm.message,
                        onPlus = { mvvm.plus() },
                        onReset = { mvvm.reset() },
                    )
                    HorizontalDivider()
                    // MVI: 상태 한 덩어리에서 꺼내 쓰고, 버튼은 모두 같은 함수에 주문서만 바꿔서 냅니다
                    LikePanel(
                        title = "MVI",
                        count = mvi.state.count,
                        message = mvi.state.message,
                        onPlus = { mvi.onIntent(LikeIntent.Plus) },
                        onReset = { mvi.onIntent(LikeIntent.Reset) },
                    )
                }
            }
        }
    }
}

// 화면 모양. 상태를 갖지 않고 받은 값만 그립니다. MVVM 이든 MVI 든 이 함수는 똑같이 씁니다
@Composable
fun LikePanel(title: String, count: Int, message: String, onPlus: () -> Unit, onReset: () -> Unit, onMinus: (() -> Unit)? = null) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text("$count", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Text(message, fontSize = 16.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onPlus) { Text("+1") }
            if (onMinus != null) OutlinedButton(onClick = onMinus) { Text("-1") }
            OutlinedButton(onClick = onReset) { Text("처음으로") }
        }
    }
}
