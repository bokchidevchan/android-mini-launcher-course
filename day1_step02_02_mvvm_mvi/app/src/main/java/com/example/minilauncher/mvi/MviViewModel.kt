package com.example.minilauncher.mvi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// 비교 코드 2: MVI 의 상태, 주문서, ViewModel. 화면(Compose)은 MviActivity.kt 입니다.
// 같은 "좋아요" 화면을 MVVM 으로 만든 것은 옆 폴더 mvvm 입니다.
//
// MVI 는 Model, View, Intent 의 앞 글자입니다.
//   Model    화면의 상태 한 덩어리 (아래 LikeState)
//   View     화면. 상태를 받아 그리기만 합니다 (MviActivity)
//   Intent   화면에서 일어난 일을 적은 "주문서" (아래 LikeIntent). 화면을 여는 android.content.Intent 와는 다른 것입니다
// 화면이 ViewModel 에 말하는 방법: 들어오는 문이 하나입니다 (onIntent). 무슨 일이든 주문서로 적어서 냅니다
// 비유: 창구가 하나인 은행입니다. 무슨 일이든 신청서를 써서 한 창구에 내고, 결과는 통장 한 권에 찍힙니다.
// C++ 로 치면 메시지(enum, struct)를 큐 하나에 넣고 switch 로 처리하는 방식
//
// MVVM 보다 한 걸음 더 가는 이유
//   1. 버튼이 많아지면 "지금 값이 왜 이렇게 됐지?"를 찾기 어렵습니다. 문이 하나면 그 문에 로그 한 줄만 찍어도 일어난 일이 순서대로 다 보입니다
//   2. 상태가 한 덩어리면 count 는 바꿨는데 message 는 안 바꾸는 실수가 줄어듭니다
//   3. 주문서 목록에 없는 일은 일어날 수 없습니다. 종류를 더하면 when 에 빨간 줄이 생겨 빼먹은 곳을 알려 줍니다
// 그 대신 적을 것이 많습니다. 작은 화면은 MVVM 으로 충분하고, 버튼과 상태가 많은 화면에서 MVI 가 편해집니다

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

class MviViewModel : ViewModel() {
    // Compose 가 지켜보는 값입니다. 바뀌면 이 값을 읽는 화면이 다시 그려집니다
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
        Log.d("Arch", "MVI: 받은 주문 $intent, ${old.count} -> ${state.count}")
    }
}
