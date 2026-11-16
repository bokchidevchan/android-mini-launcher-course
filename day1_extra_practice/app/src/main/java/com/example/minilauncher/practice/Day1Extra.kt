package com.example.minilauncher.practice

// 1일차 연습 문제: Kotlin 17개.
// TODO 가 적힌 줄을 고치고 Run 합니다. Logcat 검색창에 tag:Extra 를 넣으면 문제마다 O, X 가 찍힙니다.
// 정답은 같은 폴더의 Day1ExtraAnswers.kt 에 있습니다. 먼저 풀어 보고 엽니다.
object Day1Extra : Day1Problems {

    // ---------------- 리스트와 람다 ----------------

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

    // 문제 6. 짝수만 더합니다. (힌트: filter, sum)
    override fun sumOfEven(numbers: List<Int>): Int {
        return 0             // TODO
    }

    // 문제 7. 이름의 첫 글자만 모아 한 문자열로 만듭니다. 예: "SCC..." (힌트: map, first(), joinToString(""))
    override fun initials(apps: List<String>): String {
        return ""            // TODO
    }

    // 문제 8. 글자 수가 min 이상인 이름이 몇 개인지 셉니다. (힌트: count)
    override fun countLong(apps: List<String>, min: Int): Int {
        return 0             // TODO
    }

    // ---------------- 문자열, null, data class ----------------

    // 문제 9. 초를 "3분 5초" 모양으로 바꿉니다. (힌트: /, %, 문자열 안의 ${ })
    override fun timeText(seconds: Int): String {
        return ""            // TODO
    }

    // 문제 10. a 를 b 로 나눈 몫을 돌려줍니다. b 가 0 이면 null. (힌트: Int? 는 null 을 돌려줄 수 있음)
    override fun safeDivide(a: Int, b: Int): Int? {
        return 0             // TODO
    }

    // 문제 11. 모든 곡의 길이를 더합니다. (힌트: sumOf)
    override fun totalSeconds(songs: List<Song>): Int {
        return 0             // TODO
    }

    // 문제 12. 짧은 곡부터 제목만 차례로 돌려줍니다. (힌트: sortedBy, map)
    override fun titlesShortFirst(songs: List<Song>): List<String> {
        return emptyList()   // TODO
    }

    // 문제 13. 제목만 바꾼 새 곡을 돌려줍니다. 길이는 그대로. (힌트: copy)
    override fun rename(song: Song, title: String): Song {
        return song          // TODO
    }

    // ---------------- 묶기와 세기, 상태 ----------------

    // 문제 14. 글자 수가 같은 이름끼리 묶습니다. 예: 8 은 [Settings, Calendar] (힌트: groupBy)
    override fun groupByLength(apps: List<String>): Map<Int, List<String>> {
        return emptyMap()    // TODO
    }

    // 문제 15. 이름마다 몇 번 눌렸는지 셉니다. 예: [Chrome, Maps, Chrome] 은 Chrome 2, Maps 1 (힌트: groupingBy { it }.eachCount())
    override fun pressCounts(presses: List<String>): Map<String, Int> {
        return emptyMap()    // TODO
    }

    // 문제 16. 아이콘 선택. 지금 고른 것(current)을 다시 누르면 선택이 풀려 null, 다른 것을 누르면 그것이 선택됩니다.
    override fun select(current: String?, clicked: String): String? {
        return null          // TODO
    }

    // 문제 17. 검색어가 들어 있는 이름만 고릅니다(대소문자 무시). onlyLong 이 true 면 그중 글자 수 6 이상만.
    // (힌트: contains(query, ignoreCase = true), &&, ||)
    override fun visible(apps: List<String>, query: String, onlyLong: Boolean): List<String> {
        return emptyList()   // TODO
    }
}
