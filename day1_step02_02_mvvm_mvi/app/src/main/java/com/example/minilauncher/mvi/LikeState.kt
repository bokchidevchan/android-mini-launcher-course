package com.example.minilauncher.mvi

// MVI 의 M (Model). 화면의 상태입니다
// 상태: 화면에 필요한 값을 한 덩어리로 묶습니다. 화면은 이것 하나만 보고 그립니다
data class LikeState(
    val count: Int = 0,
    val message: String = "눌러 보세요",
)
