package com.example.minilauncher.practice

// 1일차 시간이 남으면: Kotlin 문제 5개.
// TODO 가 적힌 줄을 고치고 Run 합니다. Logcat 검색창에 tag:Extra 를 넣으면 문제마다 O, X 가 찍힙니다.
// 정답은 같은 폴더의 Day1ExtraAnswers.kt 에 있습니다. 먼저 풀어 보고 엽니다.
object Day1Extra : Day1Problems {

    // 문제 1. 글자 수가 6 이상인 이름만 고릅니다. (힌트: filter, length)
    override fun longNames(apps: List<String>): List<String> {
        return emptyList()   // TODO
    }

    // 문제 2. 이름을 모두 대문자로 바꿉니다. (힌트: map, uppercase())
    override fun upper(apps: List<String>): List<String> {
        return emptyList()   // TODO
    }

    // 문제 3. name 과 같은 이름을 찾아 글자 수를 돌려줍니다. 없으면 0. (힌트: find, ?., ?:)
    override fun lengthOf(apps: List<String>, name: String): Int {
        return -1            // TODO
    }

    // 문제 4. 가장 긴 곡의 제목을 돌려줍니다. 목록이 비어 있으면 "없음". (힌트: maxByOrNull)
    override fun longestTitle(songs: List<Song>): String {
        return ""            // TODO
    }

    // 문제 5. 20 이하면 "부족", 80 이하면 "보통", 나머지는 "충분". (힌트: when)
    override fun batteryText(percent: Int): String {
        return ""            // TODO
    }
}
