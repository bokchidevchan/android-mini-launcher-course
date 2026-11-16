package com.example.minilauncher.practice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

// 2일차 연습 문제: 정답. 먼저 Day2Extra.kt 를 풀어 보고 엽니다.
object Day2Answers : Day2Problems {

    override suspend fun slowDouble(x: Int): Int {
        delay(100)
        return x * 2
    }

    override suspend fun loadBoth(): Int = coroutineScope {
        val a = async { loadA() }
        val b = async { loadB() }
        a.await() + b.await()
    }

    override fun numbers(): Flow<Int> = flow {
        for (i in 1..5) emit(i)
    }

    override suspend fun threadName(): String = withContext(Dispatchers.Default) {
        Thread.currentThread().name
    }

    override suspend fun safeLoad(fail: Boolean): String {
        return try {
            loadOrFail(fail)
        } catch (e: IllegalStateException) {
            "실패"
        }
    }

    override suspend fun sum(numbers: Flow<Int>): Int {
        var total = 0
        numbers.collect { total += it }
        return total
    }

    override fun evenTimesTen(numbers: Flow<Int>): Flow<Int> = numbers.filter { it % 2 == 0 }.map { it * 10 }

    override suspend fun loadAll(ids: List<Int>): List<String> = coroutineScope {
        ids.map { id -> async { loadName(id) } }.awaitAll()
    }

    override suspend fun loadWithin(limitMs: Long): String? = withTimeoutOrNull(limitMs) { loadSlow() }

    override suspend fun countUp(state: MutableStateFlow<Int>, times: Int) {
        repeat(times) { state.value += 1 }
    }

    override fun batteryPercent(level: Int, scale: Int): Int = level * 100 / scale

    override fun batteryLabel(percent: Int, charging: Boolean): String = if (charging) "충전 중 $percent%" else "$percent%"

    override fun networkLabel(connected: Boolean, wifi: Boolean): String = when {
        !connected -> "오프라인"
        wifi -> "Wi-Fi"
        else -> "모바일 데이터"
    }

    override fun progressText(done: Int, total: Int): String = "$done/$total (${done * 100 / total}%)"

    override fun isLowBattery(percent: Int, charging: Boolean): Boolean = percent <= 20 && !charging
}
