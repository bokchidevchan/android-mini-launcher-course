package com.example.minilauncher.practice

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

// 채점하는 파일입니다. 고치지 않습니다.

// 문제에서 쓰는 함수들. 서버에서 받아 오는 데 시간이 걸린다고 칩니다
suspend fun loadA(): Int {
    delay(300)
    return 10
}

suspend fun loadB(): Int {
    delay(300)
    return 20
}

suspend fun loadOrFail(fail: Boolean): String {
    delay(100)
    if (fail) throw IllegalStateException("서버 응답 없음")
    return "앱 16개"
}

suspend fun loadName(id: Int): String {
    delay(300)
    return "앱 $id"
}

suspend fun loadSlow(): String {
    delay(1000)
    return "느린 결과"
}

// 문제의 모양. Day2Extra(내가 푸는 것)와 Day2Answers(정답)가 같은 모양을 따릅니다
interface Day2Problems {
    suspend fun slowDouble(x: Int): Int
    suspend fun loadBoth(): Int
    fun numbers(): Flow<Int>
    suspend fun threadName(): String
    suspend fun safeLoad(fail: Boolean): String
    suspend fun sum(numbers: Flow<Int>): Int
    fun evenTimesTen(numbers: Flow<Int>): Flow<Int>
    suspend fun loadAll(ids: List<Int>): List<String>
    suspend fun loadWithin(limitMs: Long): String?
    suspend fun countUp(state: MutableStateFlow<Int>, times: Int)
    fun batteryPercent(level: Int, scale: Int): Int
    fun batteryLabel(percent: Int, charging: Boolean): String
    fun networkLabel(connected: Boolean, wifi: Boolean): String
    fun progressText(done: Int, total: Int): String
    fun isLowBattery(percent: Int, charging: Boolean): Boolean
}

// block 이 null 을 돌려주면 O, 글자를 돌려주면 그 까닭과 함께 X
private suspend fun judge(number: Int, block: suspend () -> String?): String = try {
    val why = block()
    if (why == null) "문제 $number: O" else "문제 $number: X  $why"
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    "문제 $number: X  예외 ${e::class.simpleName}: ${e.message}"
}

private fun same(actual: Any?, expected: Any?): String? =
    if (actual == expected) null else "나온 값 $actual, 기대한 값 $expected"

private suspend fun <T> timed(block: suspend () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val value = block()
    return value to (System.currentTimeMillis() - start)
}

// 문제마다 "문제 1: O" 같은 줄을 만들어 돌려줍니다
suspend fun checkDay2(p: Day2Problems): List<String> = listOf(
    judge(1) {
        val (value, ms) = timed { p.slowDouble(21) }
        if (value == 42 && ms >= 90) null else "나온 값 $value, 걸린 시간 ${ms}ms (기대: 42, 0.1초쯤)"
    },
    judge(2) {
        val (value, ms) = timed { p.loadBoth() }
        if (value == 30 && ms < 500) null else "나온 값 $value, 걸린 시간 ${ms}ms (기대: 30, 0.3초쯤)"
    },
    judge(3) { same(p.numbers().toList(), listOf(1, 2, 3, 4, 5)) },
    judge(4) {
        val name = p.threadName()
        if (name.startsWith("DefaultDispatcher")) null else "나온 스레드 $name (기대: DefaultDispatcher-worker)"
    },
    judge(5) { same(listOf(p.safeLoad(fail = false), p.safeLoad(fail = true)), listOf("앱 16개", "실패")) },
    judge(6) { same(p.sum(flowOf(1, 2, 3, 4, 5)), 15) },
    judge(7) { same(p.evenTimesTen(flowOf(1, 2, 3, 4, 5, 6)).toList(), listOf(20, 40, 60)) },
    judge(8) {
        val (value, ms) = timed { p.loadAll(listOf(1, 2, 3)) }
        if (value == listOf("앱 1", "앱 2", "앱 3") && ms < 600) null
        else "나온 값 $value, 걸린 시간 ${ms}ms (기대: [앱 1, 앱 2, 앱 3], 0.3초쯤)"
    },
    judge(9) {
        val (fast, ms) = timed { p.loadWithin(300) }
        val slow = p.loadWithin(1500)
        if (fast == null && ms < 700 && slow == "느린 결과") null
        else "0.3초 제한: $fast (${ms}ms), 1.5초 제한: $slow (기대: null, 느린 결과)"
    },
    judge(10) {
        val state = MutableStateFlow(0)
        p.countUp(state, 5)
        same(state.value, 5)
    },
    judge(11) { same(listOf(p.batteryPercent(50, 200), p.batteryPercent(80, 100)), listOf(25, 80)) },
    judge(12) { same(listOf(p.batteryLabel(80, true), p.batteryLabel(15, false)), listOf("충전 중 80%", "15%")) },
    judge(13) {
        same(
            listOf(p.networkLabel(true, true), p.networkLabel(true, false), p.networkLabel(false, true)),
            listOf("Wi-Fi", "모바일 데이터", "오프라인"),
        )
    },
    judge(14) { same(p.progressText(3, 10), "3/10 (30%)") },
    judge(15) {
        same(listOf(p.isLowBattery(15, false), p.isLowBattery(15, true), p.isLowBattery(50, false)), listOf(true, false, false))
    },
)

// MainActivity 가 부릅니다. Logcat 에 tag:Extra
fun day2Extra(scope: CoroutineScope) {
    scope.launch {
        delay(1500)     // 화면이 뜬 뒤에 시작합니다
        val lines = checkDay2(Day2Extra)
        lines.forEach { Log.d("Extra", it) }
        Log.d("Extra", "맞은 문제 ${lines.count { it.endsWith(": O") }} / ${lines.size}")
    }
}
