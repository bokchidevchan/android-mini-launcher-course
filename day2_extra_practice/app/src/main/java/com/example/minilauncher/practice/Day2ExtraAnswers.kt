package com.example.minilauncher.practice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

// 2일차 시간이 남으면: 정답. 먼저 Day2Extra.kt 를 풀어 보고 엽니다.
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
}
