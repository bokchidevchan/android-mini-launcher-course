package com.example.minilauncher.practice

import android.util.Log

// 1일차 오전 Kotlin 문법 실습 10단계.
// MainActivity.kt 의 kotlinPractice(step = 1) 에서 숫자를 1부터 10까지 바꿔 가며 Run 합니다.
// Logcat 검색창에 tag:Kotlin 을 넣으면 이 파일이 남긴 줄만 보입니다.

private const val TAG = "Kotlin"

// Logcat 에 한 줄 찍습니다. C++ 의 printf 자리.
// Log.d("태그", "내용") 의 태그는 내가 정하는 이름이고, Logcat 에서 tag:Kotlin 처럼 찾을 때 씁니다
private fun show(message: String) {
    Log.d(TAG, message)
}

// data class 는 값을 담으려고 만드는 클래스입니다. 앞에 data 를 붙이면
// 내용 비교(==), 보기 좋은 출력(toString), 복사(copy)를 컴파일러가 만들어 줍니다.
// C++ 로 치면 struct 에 operator== 와 출력 함수를 직접 써 주던 것을 한 단어로 끝내는 것입니다 (7단계)
data class App(val label: String, val packageName: String, val installed: Boolean = true)

// listOf(...) 는 값을 순서대로 담은 리스트를 만듭니다. C++ 의 std::vector 자리입니다.
// listOf 로 만든 리스트는 읽기만 되고, 넣고 빼려면 mutableListOf 로 만듭니다 (5단계)
private val sampleApps = listOf(
    App("Settings", "com.android.settings"),
    App("Camera", "com.android.camera2"),
    App("Chrome", "com.android.chrome"),
    App("Maps", "com.google.android.apps.maps", installed = false),
)

fun kotlinPractice(step: Int) {
    show("===== ${step}단계 =====")     // "${값}" 은 문자열 안에 값을 끼워 넣는 문법입니다 (1단계)
    when (step) {                       // when 은 C++ 의 switch 자리입니다 (3단계)
        1 -> step01Variables()
        2 -> step02NullSafety()
        3 -> step03Conditions()
        4 -> step04Functions()
        5 -> step05Lists()
        6 -> step06Lambdas()
        7 -> step07DataClass()
        8 -> step08Classes()
        9 -> step09EnumWhen()
        10 -> step10Search()
        else -> show("1부터 10까지의 숫자를 넣어 주세요")
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

// 2단계. null 안전
// null 이 될 수 있는 타입은 뒤에 ? 를 붙입니다. C++ 의 nullptr 검사를 컴파일러가 강제합니다
private fun step02NullSafety() {
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

// 3단계. if 와 when
// if 와 when 은 값을 돌려주는 식입니다. C++ 의 삼항 연산자, switch 자리
private fun step03Conditions() {
    val battery = 15
    val color = if (battery <= 20) "빨강" else "검정"
    val level = when {
        battery <= 20 -> "부족"
        battery <= 80 -> "보통"
        else -> "충분"
    }
    show("배터리 $battery%: 색은 $color, 상태는 $level")
    // 바꿔 보기: battery 를 50, 100 으로 바꿔 봅니다
}

// 4단계. 함수
// 기본값이 있는 인자는 생략할 수 있고, 이름을 붙여서 넘길 수도 있습니다
private fun describe(label: String, size: Int = 56, selected: Boolean = false): String {
    return "$label 아이콘 ${size}dp" + if (selected) " (선택됨)" else ""
}

private fun twice(x: Int) = x * 2      // 한 줄 함수는 = 로 씁니다

private fun step04Functions() {
    show(describe("Chrome"))
    show(describe("Chrome", 80))
    show(describe("Chrome", selected = true))
    show("56 의 두 배: ${twice(56)}")
    // 바꿔 보기: show(describe("Maps", selected = true, size = 96)) 을 한 줄 넣어 봅니다. 이름을 붙이면 순서를 바꿔도 됩니다
}

// 5단계. 리스트와 반복
// listOf 는 읽기 전용, mutableListOf 는 넣고 뺄 수 있습니다. C++ 의 std::vector 자리
private fun step05Lists() {
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

// 6단계. 람다
// { } 안이 람다입니다. 인자가 하나면 it 으로 부릅니다. C++ 의 [](auto& a){ } 자리
// 함수를 인자로 받을 수도 있습니다. (Int) -> Unit 은 C++ 의 std::function<void(int)> 자리
private fun runTwice(action: (Int) -> Unit) {
    action(1)
    action(2)
}

private fun step06Lambdas() {
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

// 7단계. data class
// 맨 위의 App 이 data class 입니다. equals, toString, copy 를 컴파일러가 만들어 줍니다
private fun step07DataClass() {
    val a = App("Chrome", "com.android.chrome")
    val b = App("Chrome", "com.android.chrome")
    show("출력: $a")
    // == 는 내용 비교(operator==), === 는 같은 객체인지 비교입니다
    show("내용 비교 ==: ${a == b}, 같은 객체인지 ===: ${a === b}")
    val d = a                                 // 복사가 아닙니다. C++ 의 auto d = a 와 달리 같은 객체를 가리킵니다
    show("d 는 a 와 같은 객체: ${a === d}")
    val c = a.copy(label = "Chrome Beta")     // 일부만 바꾼 복사본
    show("복사본: $c")
    val (label, pkg) = a                      // 구조 분해
    show("꺼낸 값: $label, $pkg")
    // 바꿔 보기: 맨 위 App 앞의 data 를 지우면 copy 줄과 구조 분해 줄에서 빌드 오류가 납니다.
    // 그 줄들(val c 부터 끝까지)을 주석으로 막고 Run 하면 출력과 == 결과가 어떻게 달라질까요?
}

// 8단계. 클래스
// 생성자 인자에 val 을 붙이면 그대로 멤버 변수가 됩니다. new 를 쓰지 않습니다
private class Counter(val name: String, start: Int = 0) {
    var value = start
        private set                    // 밖에서는 읽기만 됩니다

    fun increase() {
        value++
    }
}

// : 뒤가 부모입니다. 부모의 함수를 다시 쓸 때는 override 를 꼭 적습니다 (C++ 의 순수 가상 클래스 자리)
private interface Greeter {
    fun greet(): String
}

private class KoreanGreeter : Greeter {
    override fun greet() = "안녕하세요"
}

private fun step08Classes() {
    val counter = Counter("누른 횟수")
    counter.increase()
    counter.increase()
    show("${counter.name}: ${counter.value}")
    val greeter: Greeter = KoreanGreeter()
    show("인터페이스로 부르기: ${greeter.greet()}")
    // 바꿔 보기: counter.value = 10 을 넣으면 어떤 오류가 날까요?
}

// 9단계. enum class 와 when
// 경우를 하나라도 빠뜨리면 컴파일 오류가 납니다. 2일차의 sealed interface 도 같은 방식으로 when 에 경우를 모두 적습니다
private enum class Network { WIFI, MOBILE, OFFLINE }

private fun networkText(network: Network): String = when (network) {
    Network.WIFI -> "Wi-Fi"
    Network.MOBILE -> "모바일 데이터"
    Network.OFFLINE -> "오프라인"
}

private fun step09EnumWhen() {
    for (network in Network.entries) {
        show("$network: ${networkText(network)}")
    }
    // 바꿔 보기: Network.OFFLINE 줄을 지우면 어떤 오류가 날까요?
}

// 10단계. 종합: 앱 검색
// 3일차에 만드는 검색 기능의 축소판입니다. 함수, 리스트, 람다, null 안전을 한데 씁니다
private fun search(apps: List<App>, query: String): List<App> {
    if (query.isBlank()) return apps
    return apps.filter { it.label.contains(query, ignoreCase = true) }
}

private fun step10Search() {
    show("검색어 set: ${search(sampleApps, "set").map { it.label }}")
    show("검색어 c: ${search(sampleApps, "c").map { it.label }}")
    show("검색어 ma: ${search(sampleApps, "ma").map { it.label }}")
    show("빈 검색어: ${search(sampleApps, "").size}개")
    val first = search(sampleApps, "zzz").firstOrNull()
    show("없는 검색어: ${first?.label ?: "찾는 앱이 없습니다"}")
    // 바꿔 보기: 설치된 앱만 검색되게 고쳐 봅니다 (6단계의 filter). 검색어 ma 의 결과가 빈 목록이 되면 성공입니다
}
