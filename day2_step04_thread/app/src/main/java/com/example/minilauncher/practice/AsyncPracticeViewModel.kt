package com.example.minilauncher.practice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// async/await 와 suspend 실습 9단계. ViewModel 의 viewModelScope 안에서 돌립니다.
// MainActivity.kt 의 asyncVm.run(step = 1) 에서 숫자를 1부터 9까지 바꿔 가며 Run 합니다.
// Logcat 검색창에 tag:Async 를 넣습니다. 줄마다 지난 시간과 [스레드 이름] 이 붙습니다.
//
// 먼저 알아 둘 것
// - viewModelScope 는 Dispatchers.Main.immediate 에서 시작합니다. 그래서 따로 정하지 않으면 [main] 으로 찍힙니다.
// - suspend 함수는 "멈출 수도 있는" 함수입니다. 실제로 멈추는 곳은 delay, await, withContext 같은
//   다른 중단 함수를 부르는 줄뿐입니다. 멈춘 동안 스레드는 비어서 다른 일을 합니다.

private const val TAG = "Async"

class AsyncPracticeViewModel : ViewModel() {

    private var startedAt = 0L

    private fun show(message: String) {
        val elapsed = System.currentTimeMillis() - startedAt
        Log.d(TAG, "${elapsed}ms [${Thread.currentThread().name}] $message")
    }

    fun run(step: Int) {
        if (step == 0) return
        startedAt = System.currentTimeMillis()
        show("===== ${step}단계 =====")
        when (step) {
            1 -> step01Sequential()
            2 -> step02AsyncAwait()
            3 -> step03AwaitTooEarly()
            4 -> step04AwaitAll()
            5 -> step05SuspendYields()
            6 -> step06SleepBlocks()
            7 -> step07SuspendWithoutPause()
            8 -> step08ParallelThreads()
            9 -> step09RealScreen()
            else -> show("0부터 9까지의 숫자를 넣어 주세요")
        }
    }

    // 서버에서 받아 오는 척하는 함수 둘. delay 에서 1초 동안 멈춥니다
    private suspend fun fetchUser(): String {
        show("  fetchUser 시작")
        delay(1000)          // 여기서 멈춤. 스레드는 놓아 줍니다
        show("  fetchUser 끝")
        return "찬"
    }

    private suspend fun fetchApps(): List<String> {
        show("  fetchApps 시작")
        delay(1000)
        show("  fetchApps 끝")
        return listOf("카메라", "메모", "지도")
    }

    // 1단계. 그냥 차례로 부르기: 1초 + 1초 = 2초
    // suspend 함수는 부른 줄에서 끝날 때까지 기다렸다가 다음 줄로 갑니다
    private fun step01Sequential() {
        viewModelScope.launch {
            val user = fetchUser()
            val apps = fetchApps()
            show("결과: $user, $apps (약 2000ms)")
        }
    }

    // 2단계. async 로 둘을 동시에 시작하고, await 로 결과를 받기: 약 1초
    // async { } 는 일을 시작만 하고 바로 다음 줄로 갑니다. 돌려받는 Deferred 는 "나중에 줄 결과표"입니다.
    // await() 는 결과표를 내밀고 결과가 나올 때까지 기다립니다(멈춥니다)
    private fun step02AsyncAwait() {
        viewModelScope.launch {
            val userJob = async { fetchUser() }
            val appsJob = async { fetchApps() }
            show("async 두 개를 시작만 하고 여기까지 바로 옴")
            val user = userJob.await()
            val apps = appsJob.await()
            show("결과: $user, $apps (약 1000ms)")
        }
    }

    // 3단계. 흔한 실수: async 바로 뒤에 await 를 붙이면 다시 2초
    // 첫 번째 await 에서 멈추는 동안 두 번째 async 는 아직 시작도 안 했습니다
    private fun step03AwaitTooEarly() {
        viewModelScope.launch {
            val user = async { fetchUser() }.await()
            val apps = async { fetchApps() }.await()
            show("결과: $user, $apps (약 2000ms, 병렬이 아님)")
        }
    }

    // 4단계. 여러 개를 한꺼번에: map 으로 async 를 만들고 awaitAll
    // 아이콘 5개를 각각 1초씩 받아도 전체는 약 1초입니다
    private fun step04AwaitAll() {
        viewModelScope.launch {
            val names = listOf("카메라", "메모", "지도", "음악", "설정")
            val icons = names.map { name ->
                async {
                    show("  $name 아이콘 받기 시작")
                    delay(1000)
                    "$name.png"
                }
            }.awaitAll()
            show("아이콘 ${icons.size}개: $icons (약 1000ms)")
        }
    }

    // 5단계. suspend 는 어디서 멈추나: delay 같은 중단 지점에서 멈추고 스레드를 양보합니다
    // A 와 B 둘 다 main 스레드 하나에서 도는데도 번갈아 찍힙니다.
    // A 가 delay 에서 멈춘 사이에 main 스레드가 B 를 돌리기 때문입니다
    private fun step05SuspendYields() {
        viewModelScope.launch {
            repeat(3) { i ->
                show("A$i")
                delay(500)   // 멈춤 → main 스레드가 비어서 B 차례
            }
        }
        viewModelScope.launch {
            repeat(3) { i ->
                show("B$i")
                delay(500)
            }
        }
        show("launch 두 개 걸어 놓고 지나옴")
        // A0, B0 가 이 줄보다 먼저 찍힙니다. viewModelScope 는 Main.immediate 라서, 이미 main 스레드면
        // launch 안쪽을 첫 멈춤(delay)까지 그 자리에서 바로 돌리기 때문입니다
    }

    // 6단계. Thread.sleep 은 멈추는 게 아니라 스레드를 막습니다
    // 5단계와 같은 모양인데 delay 를 Thread.sleep 으로만 바꿨습니다.
    // A 가 main 스레드를 쥔 채로 자서 B 는 A 가 다 끝날 때까지 못 들어옵니다. 그동안 화면도 멈춥니다
    private fun step06SleepBlocks() {
        viewModelScope.launch {
            repeat(3) { i ->
                show("A$i")
                Thread.sleep(500)   // 막음 → main 스레드가 아무것도 못 함
            }
        }
        viewModelScope.launch {
            repeat(3) { i ->
                show("B$i")
                Thread.sleep(500)
            }
        }
    }

    // 7단계. suspend 를 붙였다고 멈추지 않습니다
    // countSlowly 는 suspend 지만 안에 중단 함수가 하나도 없어서 한 번도 멈추지 않습니다.
    // 그래서 B 는 countSlowly 가 끝날 때까지 끼어들지 못합니다
    private suspend fun countSlowly(): Long {
        var sum = 0L
        for (i in 1..300_000_000L) sum += i   // 그냥 계산. 멈추는 곳이 없음
        return sum
    }

    private fun step07SuspendWithoutPause() {
        viewModelScope.launch {
            show("A: countSlowly 시작")
            val sum = countSlowly()
            show("A: countSlowly 끝 sum=$sum")
        }
        viewModelScope.launch {
            show("B: 저는 A 가 다 끝난 뒤에야 찍힙니다")
        }
        // 바꿔 보기: countSlowly 의 for 안에 if (i % 50_000_000L == 0L) yield() 를 넣으면
        // (import kotlinx.coroutines.yield) 그 줄이 중단 지점이 되어 B 가 중간에 끼어듭니다
    }

    // 8단계. 진짜로 다른 스레드에서 동시에: async(Dispatchers.Default)
    // 2단계의 async 는 둘 다 main 스레드 하나에서 번갈아 돈 것입니다 (delay 로 기다리는 일이라 충분했음).
    // 7단계처럼 계산만 하는 일은 멈추는 곳이 없으니, 스레드를 나눠야 동시에 돕니다
    private fun step08ParallelThreads() {
        viewModelScope.launch {
            val a = async(Dispatchers.Default) {
                show("  계산 A 시작")
                countSlowly().also { show("  계산 A 끝") }
            }
            val b = async(Dispatchers.Default) {
                show("  계산 B 시작")
                countSlowly().also { show("  계산 B 끝") }
            }
            show("main 은 기다리는 동안 비어 있습니다. 화면도 안 멈춥니다")
            val total = a.await() + b.await()
            show("결과 받음: $total (계산 하나 걸린 시간과 비슷)")
        }
    }

    // 9단계. 실제 화면처럼 API 여러 개 섞어 부르기
    // 홈 화면 하나를 열 때 API 다섯 개를 부른다고 해 봅니다. 기준은 "누가 누구를 기다려야 하나" 하나입니다.
    //
    //   login      500ms   토큰이 있어야 나머지를 부를 수 있음 → 맨 먼저, 끝날 때까지 기다림 (순서)
    //   profile    700ms  ┐ 둘 다 와야 홈 화면을 그릴 수 있음 → async 두 개, await 두 번 (병렬 + 한꺼번에)
    //   apps      1200ms  ┘
    //   recommend  400ms   apps 결과가 있어야 부를 수 있음 → apps 를 받은 뒤에 부름 (순서)
    //   notice     300ms   오는 대로 바로 띄우면 됨 → launch 로 따로 (병렬, 안 기다림)
    //   banner    2000ms   늦게 와도 상관없음 → launch 로 따로 (병렬, 안 기다림)
    //
    // 다 순서대로 부르면 500+700+1200+400+300+2000 = 5100ms 입니다.
    // 아래처럼 나누면 홈 화면은 약 1700ms, 추천은 약 2100ms, 배너는 약 2500ms 에 뜹니다
    private suspend fun callApi(name: String, millis: Long): String {
        show("  -> $name 요청")
        delay(millis)
        show("  <- $name 응답 (${millis}ms 걸림)")
        return "$name 결과"
    }

    private fun step09RealScreen() {
        viewModelScope.launch {
            // ① 순서: 토큰이 없으면 아무것도 못 부르니 여기서 기다립니다
            val token = callApi("login", 500)

            // ② 안 기다리는 것: launch 는 시작만 하고 바로 다음 줄로 갑니다. 결과는 각자 알아서 띄웁니다
            launch { show("화면: 공지 띄움 [${callApi("notice", 300)}]") }
            launch { show("화면: 배너 띄움 [${callApi("banner", 2000)}]") }

            // ③ 병렬 + 한꺼번에: 둘을 동시에 시작해 두고
            val profileJob = async { callApi("profile", 700) }
            val appsJob = async { callApi("apps", 1200) }
            // 둘 다 올 때까지 기다렸다가 한 번에 그립니다. 반쪽짜리 화면이 안 보입니다
            val profile = profileJob.await()
            val apps = appsJob.await()
            show("화면: 홈 그림 [$token, $profile, $apps]")

            // ④ 순서: apps 결과를 넣어야 부를 수 있는 API 는 그 뒤에 부릅니다
            val recommend = callApi("recommend", 400)
            show("화면: 추천 띄움 [$recommend]")
        }
        // 바꿔 보기: ③ 의 두 async 를 val profile = callApi("profile", 700) 처럼 바로 부르게 바꾸면
        // 홈 화면이 약 1700ms 가 아니라 약 2400ms 에 그려집니다. 공지와 배너 시간은 그대로입니다
    }
}
