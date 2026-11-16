package com.example.minilauncher.practice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

// 2일차 시간이 남으면: 코루틴 문제 5개.
// TODO 가 적힌 줄을 고치고 Run 합니다. Logcat 검색창에 tag:Extra 를 넣으면 문제마다 O, X 가 찍힙니다.
// 채점은 앱이 뜨고 3초쯤 뒤에 찍힙니다. 정답은 같은 폴더의 Day2ExtraAnswers.kt 에 있습니다.
// 쓸 수 있는 함수: loadA(), loadB(), loadOrFail(fail) (Day2ExtraCheck.kt 에 있습니다)
object Day2Extra : Day2Problems {

    // 문제 1. 0.1초 기다린 뒤 x 의 두 배를 돌려줍니다. (힌트: delay)
    override suspend fun slowDouble(x: Int): Int {
        return 0             // TODO
    }

    // 문제 2. loadA() 와 loadB() 를 동시에 불러 합을 돌려줍니다. 지금은 차례로 불러서 0.6초가 걸립니다.
    // 0.3초쯤에 끝나게 고칩니다. (힌트: coroutineScope { }, async, await)
    override suspend fun loadBoth(): Int {
        return loadA() + loadB()   // TODO
    }

    // 문제 3. 1, 2, 3, 4, 5 를 차례로 내보내는 Flow 를 만듭니다. (힌트: flow { emit(...) })
    override fun numbers(): Flow<Int> {
        return emptyFlow()   // TODO
    }

    // 문제 4. Dispatchers.Default 로 옮겨 가서 그곳의 스레드 이름을 돌려줍니다. (힌트: withContext)
    override suspend fun threadName(): String {
        return Thread.currentThread().name   // TODO
    }

    // 문제 5. loadOrFail(fail) 의 결과를 돌려줍니다. 실패해서 예외가 나면 "실패" 를 돌려줍니다. (힌트: try, catch)
    override suspend fun safeLoad(fail: Boolean): String {
        return loadOrFail(fail)   // TODO
    }
}
