package com.example.minilauncher.practice

import android.util.Log

// 1일차 오전 Kotlin 문법 실습 12단계.
// MainActivity.kt 의 kotlinPractice(step = 1) 에서 숫자를 1부터 12까지 바꿔 가며 Run 합니다.
// Logcat 검색창에 tag:Kotlin 을 넣으면 이 파일이 남긴 줄만 보입니다.
// 직접 써 보고 싶으면 같은 폴더의 KotlinPractice_My.kt 에 옮겨 적습니다.

private const val TAG = "Kotlin"

// Logcat 에 한 줄 찍습니다. C++ 의 printf 자리.
// Log.d("태그", "내용") 의 태그는 내가 정하는 이름이고, Logcat 에서 tag:Kotlin 처럼 찾을 때 씁니다
private fun show(message: String) {
    Log.d(TAG, message)
}

// data class 는 값을 담으려고 만드는 클래스입니다. 앞에 data 를 붙이면
// 내용 비교(==), 보기 좋은 출력(toString), 복사(copy)를 컴파일러가 만들어 줍니다.
// C++ 로 치면 struct 에 operator== 와 출력 함수를 직접 써 주던 것을 한 단어로 끝내는 것입니다
data class App(val label: String, val packageName: String, val installed: Boolean = true)

// listOf(...) 는 값을 순서대로 담은 리스트를 만듭니다. C++ 의 std::vector 자리입니다.
// listOf 로 만든 리스트는 읽기만 되고, 넣고 빼려면 mutableListOf 로 만듭니다 (9단계)
val sampleApps = listOf(
    App("Settings", "com.android.settings"),
    App("Camera", "com.android.camera2"),
    App("Chrome", "com.android.chrome"),
    App("Maps", "com.google.android.apps.maps", installed = false),
)

fun kotlinPractice(step: Int) {
    show("===== ${step}단계 =====")     // "${값}" 은 문자열 안에 값을 끼워 넣는 문법입니다 (1단계)
    when (step) {                       // when 은 C++ 의 switch 자리입니다 (5단계)
        1 -> step01Variables()
        2 -> step02Numbers()
        3 -> step03Strings()
        4 -> step04IfElse()
        5 -> step05When()
        6 -> step06For()
        7 -> step07While()
        8 -> step08Functions()
        9 -> step09Lists()
        10 -> step10NullSafety()
        11 -> step11Lambdas()
        12 -> step12Search()
        else -> show("1부터 12까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 변수와 문자열
// val 은 다시 대입할 수 없고 var 는 할 수 있습니다. 타입은 적지 않아도 됩니다 (C++ 의 auto)
private fun step01Variables() {
    val appName = "Mini Launcher"      // String
    var count = 3                      // Int
    count = count + 1
    val version: Double = 1.0          // 타입을 직접 적을 수도 있습니다
    show("앱 이름: $appName")           // 문자열 안에 $변수 를 바로 넣습니다
    show("앱 개수: $count")
    show("글자 수: ${appName.length}, 버전: $version")
    println("println 으로도 찍을 수 있습니다")   // Logcat 에서는 tag:System.out 으로 찾습니다
    // 바꿔 보기: 아래에 appName = "Other" 를 한 줄 넣으면 어떤 오류가 날까요?
}

// 2단계. 숫자 계산
// 정수끼리 나누면 소수점 아래를 버립니다. C++ 과 같습니다
private fun step02Numbers() {
    val apps = 17
    val perRow = 4
    show("더하기: ${apps + 3}, 빼기: ${apps - 3}, 곱하기: ${apps * 2}")
    show("나누기: ${apps / perRow} (정수끼리 나누면 소수점 아래는 버립니다)")
    show("나머지: ${apps % perRow}")
    show("소수로 나누기: ${apps / 4.0}")
    val rows = (apps + perRow - 1) / perRow
    show("한 줄에 ${perRow}개씩 놓으면 ${rows}줄")
    show("비교: ${apps > 10}, ${apps == 17}, ${apps != 17}")
    // 바꿔 보기: apps 를 16 으로 바꾸면 줄 수가 어떻게 될까요?
}

// 3단계. 문자열 다루기
// 문자열에 점을 찍으면 쓸 수 있는 기능이 나옵니다. Android Studio 에서 name. 까지만 치면 목록이 뜹니다
private fun step03Strings() {
    val name = "Mini Launcher"
    show("글자 수: ${name.length}")
    show("대문자: ${name.uppercase()}, 소문자: ${name.lowercase()}")
    show("Mini 로 시작하나: ${name.startsWith("Mini")}")
    show("launch 가 들어 있나: ${name.contains("launch", ignoreCase = true)}")
    show("앞 4글자: ${name.take(4)}, 첫 글자: ${name.first()}")
    show("이어 붙이기: " + name + " 1.0")
    show("바꾸기: ${name.replace("Mini", "My")}")
    // 바꿔 보기: contains 의 ignoreCase = true 를 지우면 결과가 어떻게 될까요?
}

// 4단계. if 와 else
// 조건이 맞으면 위쪽, 아니면 else 쪽이 실행됩니다. && 는 "그리고", || 는 "또는", ! 는 "아니다"
private fun step04IfElse() {
    val battery = 15
    if (battery <= 20) {
        show("배터리 부족")
    } else {
        show("배터리 충분")
    }

    // 경우가 셋이면 else if 를 이어 씁니다
    if (battery <= 20) {
        show("빨강")
    } else if (battery <= 80) {
        show("검정")
    } else {
        show("초록")
    }

    // if 는 값을 돌려줍니다. C++ 의 삼항 연산자(조건 ? a : b) 자리
    val color = if (battery <= 20) "빨강" else "검정"
    show("배터리 $battery%: 색은 $color")

    val charging = false
    if (battery <= 20 && !charging) {
        show("충전기를 꽂으세요")
    }
    // 바꿔 보기: battery 를 50, 100 으로 바꿔 봅니다. charging 을 true 로 바꾸면 마지막 줄이 어떻게 될까요?
}

// 5단계. when
// 경우가 여럿일 때 if 를 길게 잇는 대신 씁니다. C++ 의 switch 자리이고, 범위(in)도 쓸 수 있습니다
private fun step05When() {
    val battery = 15
    val level = when {
        battery <= 20 -> "부족"
        battery <= 80 -> "보통"
        else -> "충분"
    }
    show("상태: $level")

    val day = 3
    val dayName = when (day) {
        1 -> "월"
        2 -> "화"
        3 -> "수"
        4 -> "목"
        5 -> "금"
        6, 7 -> "주말"
        else -> "모름"
    }
    show("${day}번째 요일: $dayName")

    val size = 96
    val sizeText = when (size) {
        in 0..48 -> "작음"
        in 49..72 -> "보통"
        else -> "큼"
    }
    show("아이콘 ${size}dp: $sizeText")
    // 바꿔 보기: day 를 6, 9 로 바꿔 봅니다. size 를 56 으로 바꿔 봅니다
}

// 6단계. for 와 범위
// 1..3 은 1, 2, 3 입니다. until 은 끝을 빼고, downTo 는 거꾸로, step 은 몇 칸씩 갈지입니다
private fun step06For() {
    for (i in 1..3) {
        show("1..3: $i")
    }
    for (i in 0 until 3) {
        show("0 until 3: $i")
    }
    for (i in 10 downTo 1 step 3) {
        show("10 downTo 1 step 3: $i")
    }
    var sum = 0
    for (i in 1..10) {
        sum += i
    }
    show("1부터 10까지의 합: $sum")
    // 바꿔 보기: 1..10 을 1..100 으로 바꿔 봅니다. 짝수만 더하려면 step 을 어떻게 쓰면 될까요?
}

// 7단계. while
// 조건이 맞는 동안 되풀이합니다. break 는 그 자리에서 되풀이를 끝냅니다
private fun step07While() {
    var battery = 3
    while (battery > 0) {
        show("배터리 $battery")
        battery--
    }
    show("꺼졌습니다")

    var n = 1
    while (true) {
        if (n * n > 50) break
        n++
    }
    show("제곱이 50 을 넘는 첫 수: $n")
    // 바꿔 보기: 50 을 1000 으로 바꿔 봅니다
}

// 8단계. 함수
// 기본값이 있는 인자는 생략할 수 있고, 이름을 붙여서 넘길 수도 있습니다
fun describe(label: String, size: Int = 56, selected: Boolean = false): String {
    return "$label 아이콘 ${size}dp" + if (selected) " (선택됨)" else ""
}

fun twice(x: Int) = x * 2      // 한 줄 함수는 = 로 씁니다

private fun step08Functions() {
    show(describe("Chrome"))
    show(describe("Chrome", 80))
    show(describe("Chrome", selected = true))
    show("56 의 두 배: ${twice(56)}")
    // 바꿔 보기: show(describe("Maps", selected = true, size = 96)) 을 한 줄 넣어 봅니다. 이름을 붙이면 순서를 바꿔도 됩니다
}

// 9단계. 리스트
// listOf 는 읽기 전용, mutableListOf 는 넣고 뺄 수 있습니다. C++ 의 std::vector 자리
private fun step09Lists() {
    val labels = listOf("Settings", "Camera", "Chrome")
    show("개수 ${labels.size}, 첫 번째 ${labels[0]}, 마지막 ${labels.last()}")
    for (label in labels) {            // C++ 의 범위 기반 for 와 같습니다
        show("for: $label")
    }
    for (i in labels.indices) {        // labels.indices 는 0, 1, 2 같은 번호 범위입니다
        show("${i}번: ${labels[i]}")
    }
    // val 은 변수를 다른 리스트로 바꾸지 못한다는 뜻입니다. 리스트 안의 내용은 바꿀 수 있습니다 (C++ 의 T* const 와 비슷합니다)
    val editable = mutableListOf("Phone")
    editable.add("Messages")
    show("넣은 뒤: $editable")
    // 바꿔 보기: labels.add("Maps") 를 넣으면 어떤 오류가 날까요? (읽기 전용 리스트에는 add 함수가 아예 없습니다)
}

// 10단계. null 안전
// null 이 될 수 있는 타입은 뒤에 ? 를 붙입니다. C++ 의 nullptr 검사를 컴파일러가 강제합니다
private fun step10NullSafety() {
    // find { 조건 } 은 조건에 맞는 첫 번째 값을 돌려주고, 없으면 null 을 돌려줍니다
    val found: App? = sampleApps.find { it.label == "Chrome" }
    val missing: App? = sampleApps.find { it.label == "Netflix" }
    show("찾은 앱: ${found?.label}")                  // ?. 는 null 이면 뒤를 부르지 않고 null 이 됩니다
    show("없는 앱: ${missing?.label}")
    show("없으면 대신: ${missing?.label ?: "없음"}")    // ?: 는 null 일 때 쓸 값
    if (found != null) {
        show("null 검사를 지나면 그냥 씁니다: ${found.packageName}")
    }
    // 바꿔 보기: "없는 앱" 줄의 missing?.label 을 missing!!.label 로 바꾸고 Run 합니다.
    // 앱이 꺼지면 Logcat 검색창을 package:mine 으로 바꿔 FATAL EXCEPTION 과 NullPointerException 을 찾습니다
}

// 11단계. 람다
// { } 안이 람다입니다. 인자가 하나면 it 으로 부릅니다. C++ 의 [](auto& a){ } 자리
// 함수를 인자로 받을 수도 있습니다. (Int) -> Unit 은 C++ 의 std::function<void(int)> 자리
fun runTwice(action: (Int) -> Unit) {
    action(1)
    action(2)
}

private fun step11Lambdas() {
    // filter 와 map 은 원래 리스트를 고치지 않고 새 리스트를 돌려줍니다
    val installed = sampleApps.filter { it.installed }       // 조건에 맞는 것만 남깁니다
    val labels = installed.map { it.label }                  // 하나씩 바꿉니다
    show("설치된 앱: $labels")
    show("글자 수 순서: ${labels.sortedBy { it.length }}")
    show("C 로 시작: ${labels.filter { name -> name.startsWith("C") }}")   // it 대신 이름을 붙여도 됩니다
    show("모두 설치됨: ${sampleApps.all { it.installed }}, 하나라도 설치됨: ${sampleApps.any { it.installed }}")
    runTwice { show("${it}번째 호출") }     // 마지막 인자가 람다면 괄호 밖으로 뺍니다. 화면을 만드는 Compose 가 이 모양을 씁니다
    // 바꿔 보기: startsWith("C") 를 startsWith("S") 로 바꿔 봅니다
}

// 12단계. 종합: 앱 검색
// 3일차에 만드는 검색 기능의 축소판입니다. 함수, 리스트, 람다, null 안전을 한데 씁니다
fun search(apps: List<App>, query: String): List<App> {
    if (query.isBlank()) return apps
    return apps.filter { it.label.contains(query, ignoreCase = true) }
}

private fun step12Search() {
    show("검색어 set: ${search(sampleApps, "set").map { it.label }}")
    show("검색어 c: ${search(sampleApps, "c").map { it.label }}")
    show("검색어 ma: ${search(sampleApps, "ma").map { it.label }}")
    show("빈 검색어: ${search(sampleApps, "").size}개")
    val first = search(sampleApps, "zzz").firstOrNull()
    show("없는 검색어: ${first?.label ?: "찾는 앱이 없습니다"}")
    // 바꿔 보기: 설치된 앱만 검색되게 고쳐 봅니다 (11단계의 filter). 검색어 ma 의 결과가 빈 목록이 되면 성공입니다
}
