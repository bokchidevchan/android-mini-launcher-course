package com.example.minilauncher.practice

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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

// 문제 5개의 모양. Day2Extra(내가 푸는 것)와 Day2Answers(정답)가 같은 모양을 따릅니다
interface Day2Problems {
    suspend fun slowDouble(x: Int): Int
    suspend fun loadBoth(): Int
    fun numbers(): Flow<Int>
    suspend fun threadName(): String
    suspend fun safeLoad(fail: Boolean): String
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
    judge(3) {
        val list = p.numbers().toList()
        if (list == listOf(1, 2, 3, 4, 5)) null else "나온 값 $list, 기대한 값 [1, 2, 3, 4, 5]"
    },
    judge(4) {
        val name = p.threadName()
        if (name.startsWith("DefaultDispatcher")) null else "나온 스레드 $name (기대: DefaultDispatcher-worker)"
    },
    judge(5) {
        val result = listOf(p.safeLoad(fail = false), p.safeLoad(fail = true))
        if (result == listOf("앱 16개", "실패")) null else "나온 값 $result, 기대한 값 [앱 16개, 실패]"
    },
)

// MainActivity 가 부릅니다. Logcat 에 tag:Extra
fun day2Extra(scope: CoroutineScope) {
    scope.launch {
        delay(1500)     // 화면이 뜬 뒤에 시작합니다
        checkDay2(Day2Extra).forEach { Log.d("Extra", it) }
    }
}
