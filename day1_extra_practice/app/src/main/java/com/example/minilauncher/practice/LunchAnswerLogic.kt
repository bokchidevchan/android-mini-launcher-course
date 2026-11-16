package com.example.minilauncher.practice

// 정답 화면이 쓰는 함수 두 개.

// 방금 뽑은 메뉴(last)는 빼고 아무거나 하나 고릅니다. 그래서 같은 메뉴가 연달아 나오지 않습니다
fun pickLunchAnswer(menus: List<String>, last: String?): String {
    return menus.filter { it != last }.random()
}

// 가장 많이 뽑힌 메뉴와 횟수. 기록이 비어 있으면 null
fun mostPickedAnswer(history: List<String>): Pair<String, Int>? {
    return history.groupingBy { it }.eachCount().maxByOrNull { it.value }?.toPair()
}
