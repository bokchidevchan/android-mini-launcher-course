package com.example.minilauncher.practice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

// 2일차 연습 문제: 코루틴 10개, 시스템 이벤트에 쓰는 함수 5개.
// TODO 가 적힌 줄을 고치고 Run 합니다. Logcat 검색창에 tag:Extra 를 넣으면 문제마다 O, X 가 찍힙니다.
// 채점은 앱이 뜨고 5초쯤 뒤에 찍힙니다. 정답은 같은 폴더의 Day2ExtraAnswers.kt 에 있습니다.
// 쓸 수 있는 함수: loadA(), loadB(), loadOrFail(fail), loadName(id), loadSlow() (Day2ExtraCheck.kt 에 있습니다)
object Day2Extra : Day2Problems {

    // ---------------- 코루틴 ----------------

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

    // 문제 6. Flow 에서 나오는 숫자를 모두 더합니다. (힌트: collect 로 받으며 더하기)
    override suspend fun sum(numbers: Flow<Int>): Int {
        return 0             // TODO
    }

    // 문제 7. 짝수만 골라 10 을 곱해 내보내는 Flow 로 바꿉니다. (힌트: filter, map)
    override fun evenTimesTen(numbers: Flow<Int>): Flow<Int> {
        return numbers       // TODO
    }

    // 문제 8. ids 마다 loadName(id) 를 불러 이름 목록을 만듭니다. 지금은 차례로 불러 하나에 0.3초씩 걸립니다.
    // 동시에 불러 0.3초쯤에 끝나게 고칩니다. (힌트: coroutineScope, ids.map { async { } }, awaitAll())
    override suspend fun loadAll(ids: List<Int>): List<String> {
        return ids.map { loadName(it) }   // TODO
    }

    // 문제 9. loadSlow() 는 1초가 걸립니다. limitMs 안에 끝나면 결과를, 못 끝나면 null 을 돌려줍니다.
    // (힌트: withTimeoutOrNull(limitMs) { })
    override suspend fun loadWithin(limitMs: Long): String? {
        return loadSlow()    // TODO
    }

    // 문제 10. state 의 값을 times 번, 한 번에 1씩 올립니다. (힌트: repeat, state.value += 1)
    override suspend fun countUp(state: MutableStateFlow<Int>, times: Int) {
        // TODO
    }

    // ---------------- 시스템 이벤트에 쓰는 함수 ----------------

    // 문제 11. 배터리 방송은 level 과 scale 두 숫자로 옵니다. 퍼센트로 바꿉니다. 예: 50, 200 은 25
    override fun batteryPercent(level: Int, scale: Int): Int {
        return 0             // TODO
    }

    // 문제 12. 충전 중이면 "충전 중 80%", 아니면 "80%"
    override fun batteryLabel(percent: Int, charging: Boolean): String {
        return ""            // TODO
    }

    // 문제 13. 연결이 없으면 "오프라인", 있으면 Wi-Fi 인지에 따라 "Wi-Fi" 나 "모바일 데이터"
    override fun networkLabel(connected: Boolean, wifi: Boolean): String {
        return ""            // TODO
    }

    // 문제 14. 동기화 진행을 "3/10 (30%)" 모양으로 만듭니다
    override fun progressText(done: Int, total: Int): String {
        return ""            // TODO
    }

    // 문제 15. 20% 이하이고 충전 중이 아닐 때만 true (배터리 부족 알림을 띄울지)
    override fun isLowBattery(percent: Int, charging: Boolean): Boolean {
        return false         // TODO
    }
}
