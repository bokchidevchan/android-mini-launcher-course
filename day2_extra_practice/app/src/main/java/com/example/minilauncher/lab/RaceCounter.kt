package com.example.minilauncher.lab

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// race condition 예제: 코루틴 2개가 같은 변수를 동시에 +1 합니다.
// count++ 는 "읽기, 더하기, 쓰기" 세 단계라 중간에 끼어들면 값이 사라집니다 (C++ 의 data race 와 같음)
object RaceCounter {
    // 횟수가 적으면 빠른 PC 에서는 두 코루틴이 안 겹쳐서 우연히 맞게 나올 수 있어 100만 번으로 둡니다
    const val TIMES = 1_000_000

    suspend fun unsafe(dispatcher: CoroutineDispatcher): Int {
        var count = 0
        coroutineScope {
            repeat(2) {
                launch(dispatcher) { repeat(TIMES) { count++ } }
            }
        }
        return count
    }

    // Mutex 는 C++ std::mutex 와 같은 역할. 다만 기다리는 동안 스레드를 막지 않고 코루틴만 멈춥니다
    suspend fun withMutex(dispatcher: CoroutineDispatcher): Int {
        var count = 0
        val mutex = Mutex()
        coroutineScope {
            repeat(2) {
                launch(dispatcher) { repeat(TIMES) { mutex.withLock { count++ } } }
            }
        }
        return count
    }
}
