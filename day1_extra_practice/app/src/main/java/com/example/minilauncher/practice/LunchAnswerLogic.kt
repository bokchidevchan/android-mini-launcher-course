package com.example.minilauncher.practice

// 정답 화면 두 개(XML, Compose)가 같이 쓰는 뽑기 함수.
// 방금 뽑은 메뉴(last)는 빼고 아무거나 하나 고릅니다. 그래서 같은 메뉴가 연달아 나오지 않습니다
fun pickLunchAnswer(menus: List<String>, last: String?): String {
    return menus.filter { it != last }.random()
}
