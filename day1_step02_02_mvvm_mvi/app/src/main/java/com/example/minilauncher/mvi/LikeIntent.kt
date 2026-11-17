package com.example.minilauncher.mvi

// MVI 의 I (Intent). 화면에서 일어난 일을 적은 "주문서"입니다.
// 화면을 여는 android.content.Intent 와 이름만 같고 다른 것입니다. "사용자가 하려는 일(의도)"이라는 뜻으로 붙은 이름입니다.
// 버튼을 하나 더 만들려면 여기에 종류를 하나 더합니다. 그러면 MviViewModel 의 when 에 빨간 줄이 생겨 빼먹은 곳을 알려 줍니다
// 주문서: 화면에서 일어날 수 있는 일의 목록입니다. 여기에 없는 일은 일어날 수 없습니다
sealed interface LikeIntent {
    data object Plus : LikeIntent
    data object Reset : LikeIntent
}
