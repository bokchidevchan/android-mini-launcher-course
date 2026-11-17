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

// 같이 해 보기 파일입니다. 완성본은 같은 폴더의 MvvmVsMvi.kt 입니다.
// 양쪽에 "-1" 버튼을 넣어 봅니다. 한 덩어리를 붙일 때마다 Run 하고 "MVVM과 MVI 비교 직접" 아이콘으로 확인합니다.
// Logcat 검색창에는 tag:MyArch 를 넣습니다. 필요한 import 는 위에 다 있습니다.
//
// ----- MVVM 쪽 (두 군데) -----
// [1] MyLikeMvvmViewModel 안, reset() 아래에 함수를 하나 더합니다
//         fun minus() {
//             Log.d(TAG, "MVVM: minus() 호출")
//             count--
//             message = "좋아요 ${count}개"
//         }
// [2] 화면의 위쪽 LikePanel 에서 onMinus = { } 를 아래로 바꿉니다
//         onMinus = { mvvm.minus() },
//
// ----- MVI 쪽 (세 군데) -----
// [3] MyLikeIntent 안에 주문서 종류를 하나 더합니다. 붙이자마자 onIntent 의 when 에 빨간 줄이 생깁니다.
//     "Minus 가 왔을 때 어떻게 할지 안 적었다"고 알려 주는 것입니다. MVVM 에는 없던 안전장치입니다
//         data object Minus : MyLikeIntent
// [4] onIntent 의 when 안에 가지를 하나 더합니다. 빨간 줄이 사라집니다
//         MyLikeIntent.Minus -> old.copy(count = old.count - 1, message = "좋아요 ${old.count - 1}개")
// [5] 화면의 아래쪽 LikePanel 에서 onMinus = { } 를 아래로 바꿉니다
//         onMinus = { mvi.onIntent(MyLikeIntent.Minus) },
//
// 다 했으면: Logcat 에서 양쪽 로그를 비교합니다. MVVM 은 함수마다 로그를 적어야 했고, MVI 는 [4]에 로그를 안 적었는데도 찍힙니다
private const val TAG = "MyArch"

class MyLikeMvvmViewModel : ViewModel() {
    var count by mutableIntStateOf(0)
        private set
    var message by mutableStateOf("눌러 보세요")
        private set

    fun plus() {
        Log.d(TAG, "MVVM: plus() 호출")
        count++
        message = "좋아요 ${count}개"
    }

    fun reset() {
        Log.d(TAG, "MVVM: reset() 호출")
        count = 0
        message = "처음으로 돌아왔습니다"
    }

    // [1] 을 여기에 붙입니다
}

data class MyLikeState(
    val count: Int = 0,
    val message: String = "눌러 보세요",
)

sealed interface MyLikeIntent {
    data object Plus : MyLikeIntent
    data object Reset : MyLikeIntent
    // [3] 을 여기에 붙입니다
}

class MyLikeMviViewModel : ViewModel() {
    var state by mutableStateOf(MyLikeState())
        private set

    fun onIntent(intent: MyLikeIntent) {
        val old = state
        state = when (intent) {
            MyLikeIntent.Plus -> old.copy(count = old.count + 1, message = "좋아요 ${old.count + 1}개")
            MyLikeIntent.Reset -> MyLikeState(message = "처음으로 돌아왔습니다")
            // [4] 를 여기에 붙입니다
        }
        Log.d(TAG, "MVI: 받은 주문 $intent, ${old.count} -> ${state.count}")
    }
}

class MyMvvmVsMviActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val mvvm: MyLikeMvvmViewModel = viewModel()
                val mvi: MyLikeMviViewModel = viewModel()
                Column(Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    LikePanel(
                        title = "MVVM",
                        count = mvvm.count,
                        message = mvvm.message,
                        onPlus = { mvvm.plus() },
                        onReset = { mvvm.reset() },
                        onMinus = { },                    // [2] 로 바꿉니다
                    )
                    HorizontalDivider()
                    LikePanel(
                        title = "MVI",
                        count = mvi.state.count,
                        message = mvi.state.message,
                        onPlus = { mvi.onIntent(MyLikeIntent.Plus) },
                        onReset = { mvi.onIntent(MyLikeIntent.Reset) },
                        onMinus = { },                    // [5] 로 바꿉니다
                    )
                }
            }
        }
    }
}
