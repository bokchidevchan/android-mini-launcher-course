package com.example.minilauncher.practice

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// 2일차 오전 코루틴 실습 11단계.
// MainActivity.kt 의 coroutinePractice(lifecycleScope, step = 1) 에서 숫자를 1부터
// 11까지 바꿔 가며 Run 합니다.
// Logcat 검색창에 tag:Coroutine 을 넣습니다. 맨 앞의 "loadApps 시작" 한 줄은 앱 목록을
// 불러오는 코드가 찍은 것이라 넘어갑니다.
// 줄마다 [스레드 이름] 이 붙습니다. 어느 스레드에서 도는지 같이 봅니다.

private const val TAG = "Coroutine"
private var startedAt = 0L

// 시작한 뒤 몇 ms 가 지났는지와 스레드 이름을 같이 찍습니다
private fun show(message: String) {
    val elapsed = System.currentTimeMillis() - startedAt
    Log.d(TAG, "${elapsed}ms [${Thread.currentThread().name}] $message")
}

fun coroutinePractice(scope: CoroutineScope, step: Int) {
    scope.launch {
        delay(1500)                 // 앱이 뜨느라 바쁜 동안은 시간이 어긋나서, 화면이 뜬 뒤에 시작합니다
        startedAt = System.currentTimeMillis()
        show("===== ${step}단계 =====")
        when (step) {
            1 -> step01Launch(scope)
            2 -> step02Delay(scope)
            3 -> step03Suspend(scope)
            4 -> step04Async(scope)
            5 -> step05WithContext(scope)
            6 -> step06Cancel(scope)
            7 -> step07Exception(scope)
            8 -> step08Flow(scope)
            9 -> step09StateFlow(scope)
            10 -> step10LoadingState(scope)
            11 -> step11Lifecycle(scope)
            else -> show("1부터 11까지의 숫자를 넣어 주세요")
        }
    }
}

// 1단계. launch
// launch { } 는 코루틴 하나를 시작하고, 끝나기를 기다리지 않고 바로 다음 줄로 갑니다
private fun step01Launch(scope: CoroutineScope) {
    show("1. launch 앞")
    scope.launch {
        delay(100)
        show("3. 코루틴 안 (0.1초 뒤)")
    }
    show("2. launch 뒤. 기다리지 않고 바로 지나왔습니다")
    // 바꿔 보면: delay(100) 을 delay(1000) 으로 바꾸면 3번 줄이 0.1초가 아니라 1초 뒤에
    // 찍힙니다. 1번과 2번은 그대로 바로 찍힙니다
}

// 2단계. delay 는 스레드를 막지 않습니다
// 코루틴 두 개가 같은 main 스레드에서 번갈아 돕니다. delay 하는 동안 스레드를 비켜 주기
// 때문입니다
private fun step02Delay(scope: CoroutineScope) {
    scope.launch {
        repeat(3) { i ->
            show("A $i")
            delay(200)
        }
    }
    scope.launch {
        repeat(3) { i ->
            show("B $i")
            delay(200)
        }
    }
    // 바꿔 보면: A 쪽의 delay(200) 을 Thread.sleep(200) 으로 바꾸면 A 0, A 1, A 2 가
    // 먼저 다 찍힌 뒤에 B 가 찍힙니다. sleep 은 스레드를 비켜 주지 않기 때문입니다
}

// 3단계. suspend 함수
// suspend 가 붙은 함수는 중간에 멈췄다가 이어질 수 있습니다. 부르는 쪽은 보통 함수처럼
// 위에서 아래로 읽습니다
suspend fun loadLabel(name: String): String {
    delay(300)                     // 서버에서 받아 오는 데 0.3초 걸린다고 칩니다
    return "$name 이름"
}

private fun step03Suspend(scope: CoroutineScope) {
    scope.launch {
        val first = loadLabel("첫 번째")
        show("받음: $first")
        val second = loadLabel("두 번째")
        show("받음: $second (차례로 받아서 모두 0.6초쯤)")
    }
    // 바꿔 보면: loadLabel 앞의 suspend 를 지우면 그 안의 delay 줄에 빨간 줄이 생깁니다.
    // delay 같은 suspend 함수는 suspend 함수나 코루틴 안에서만 부를 수 있기 때문입니다
}

// 4단계. async 와 await
// 두 가지를 동시에 시작하고, 둘 다 끝나면 결과를 받습니다. 3단계의 절반 시간이 걸립니다
private fun step04Async(scope: CoroutineScope) {
    scope.launch {
        val first = async { loadLabel("첫 번째") }
        val second = async { loadLabel("두 번째") }
        show("받음: ${first.await()}, ${second.await()} (동시에 받아서 0.3초쯤)")
    }
    // 바꿔 보기: 세 번째 async 를 하나 더 넣어도 걸리는 시간이 그대로인지 봅니다
}

// 5단계. withContext 로 스레드 바꾸기
// 무거운 계산은 Dispatchers.Default 로 보내고, 결과만 main 으로 갖고 돌아옵니다
private fun step05WithContext(scope: CoroutineScope) {
    scope.launch {
        show("시작")
        val sum = withContext(Dispatchers.Default) {
            show("계산하는 중")
            (1..1_000_000).sumOf { it.toLong() }
        }
        show("결과 $sum")
    }
    // 바꿔 보기: Dispatchers.Default 를 Dispatchers.IO 로 바꿔도 스레드 이름은
    // DefaultDispatcher-worker 그대로입니다.
    // 둘은 스레드 묶음을 같이 쓰고, 동시에 돌릴 수 있는 개수만 다릅니다
}

// 6단계. 취소
// cancel() 을 부르면 코루틴은 delay 같은 중단 지점에서 멈춥니다. 지금처럼 delay 로
// 기다리던 중이면 그 자리에서 바로 멈춥니다.
// 화면이 사라질 때 작업을 같이 멈추는 원리입니다
private fun step06Cancel(scope: CoroutineScope) {
    val job = scope.launch {
        repeat(10) { i ->
            show("일하는 중 $i")
            delay(100)
        }
        show("이 줄은 찍히지 않습니다")
    }
    scope.launch {
        delay(350)
        job.cancel()
        show("취소했습니다")
    }
    // 바꿔 보면: delay(350) 을 delay(650) 으로 바꾸면 일하는 중 6 까지 찍힙니다.
    // 0.1초마다 하나씩 찍다가 0.65초에 취소되기 때문입니다
}

// 7단계. 예외 처리
// 코루틴 안에서도 try, catch 로 잡습니다. 잡지 않으면 앱이 꺼집니다
suspend fun loadOrFail(fail: Boolean): String {
    delay(200)
    if (fail) throw IllegalStateException("서버 응답 없음")
    return "앱 16개"
}

private fun step07Exception(scope: CoroutineScope) {
    scope.launch {
        try {
            show("성공하는 경우: ${loadOrFail(fail = false)}")
            show("실패하는 경우: ${loadOrFail(fail = true)}")
        } catch (e: IllegalStateException) {
            show("실패를 잡았습니다: ${e.message}")
        }
    }
    // 바꿔 보기: try 와 catch 를 지우고 Run 합니다. 앱이 꺼지면 Logcat 을 package:mine
    // 으로 바꿔 FATAL EXCEPTION 을 찾습니다.
    // 확인했으면 Edit > Undo 로 되돌립니다. 되돌리지 않으면 앱을 열 때마다 꺼집니다
}

// 8단계. Flow
// Flow 는 값이 여러 번 흘러나오는 통로입니다. collect 로 받기 시작해야 흐르기
// 시작합니다
private fun step08Flow(scope: CoroutineScope) {
    val numbers = flow {
        for (i in 1..3) {
            delay(200)
            emit(i)                // 값 하나를 내보냅니다
        }
    }
    show("flow 를 만들었지만 아직 아무 일도 안 일어납니다")
    scope.launch {
        numbers.collect { show("받음: $it") }
        show("다 받았습니다")
    }
    // 바꿔 보기: 1..3 을 1..5 로 바꿔 봅니다
}

// 9단계. StateFlow
// StateFlow 는 항상 최신 값 하나를 들고 있습니다. 같은 값을 다시 넣으면 알리지 않습니다
private fun step09StateFlow(scope: CoroutineScope) {
    val count = MutableStateFlow(0)
    // collect 는 StateFlow 를 끝없이 지켜보는 suspend 함수라 스스로 끝나지 않습니다.
    // 그래서 지켜보는 일은 launch 하나에 따로 맡기고, 값을 바꾸는 일은 아래 launch 가 합니다.
    // 한 launch 안에 collect 와 count.value = 1 을 같이 쓰면 collect 에서 멈춰 0 만 찍힙니다
    val job = scope.launch {
        count.collect { show("화면에 보여 줄 값: $it") }
    }
    scope.launch {
        delay(200); count.value = 1
        delay(200); count.value = 1        // 같은 값이라 알리지 않습니다
        delay(200); count.value = 2
        delay(200)
        show("지금 들고 있는 값: ${count.value}")
        job.cancel()                       // 지켜보기는 혼자 안 끝나서 직접 멈춥니다. 화면에서는 lifecycle 이 대신 멈춰 줍니다
    }
    // 바꿔 보면: 두 번째 count.value = 1 을 count.value = 5 로 바꾸면 화면에 보여 줄
    // 값: 5 가 한 줄 더 찍힙니다. 값이 달라졌기 때문입니다
}

// 10단계. 종합: 불러오는 중, 성공, 실패
// 이 모듈 뒤쪽에서 볼 LauncherViewModel 의 축소판입니다. 화면 상태를 셋 중 하나로 묶어
// StateFlow 로 내보냅니다
sealed interface LoadState {
    data object Loading : LoadState
    data class Success(val apps: String) : LoadState
    data class Error(val message: String) : LoadState
}

private fun step10LoadingState(scope: CoroutineScope) {
    val state = MutableStateFlow<LoadState>(LoadState.Loading)
    val job = scope.launch {
        state.collect { current ->
            val text = when (current) {          // 경우를 하나라도 빠뜨리면 빌드 오류가 납니다
                LoadState.Loading -> "불러오는 중"
                is LoadState.Success -> "성공: ${current.apps}"
                is LoadState.Error -> "실패: ${current.message}"
            }
            show("화면: $text")
        }
    }
    scope.launch {
        state.value = try {
            LoadState.Success(loadOrFail(fail = false))
        } catch (e: IllegalStateException) {
            LoadState.Error(e.message ?: "알 수 없는 오류")
        }
        delay(100)
        job.cancel()
    }
    // 바꿔 보기: fail = false 를 fail = true 로 바꿔 실패 화면을 확인합니다
}

// 11단계. 생명주기와 함께 취소되는 코루틴
// 여기로 넘어온 scope 는 MainActivity 의 lifecycleScope 입니다. Activity 의 생명주기에
// 묶여 있어서, 화면이 끝나면(onDestroy) 이 scope 에서 시작한 코루틴이 모두 취소됩니다.
// 직접 cancel 을 부르지 않아도 됩니다.
// ViewModel 안에서는 viewModelScope 가 같은 일을 합니다. viewModelScope 는 화면을
// 돌려도 살아 있고, 화면을 완전히 닫을 때 취소됩니다.
// Run 한 뒤 뒤로 가기로 화면을 닫으면 "취소됐습니다" 가 찍히고 숫자가 더 안 올라갑니다.
// 화면을 돌리면 옛 화면의 코루틴이 취소되고, 새 화면에서 0부터 다시 시작합니다
private fun step11Lifecycle(scope: CoroutineScope) {
    scope.launch {
        try {
            var count = 0
            while (true) {
                show("화면이 살아 있는 동안 일하는 중 $count")
                count++
                delay(1000)
            }
        } finally {
            // 코루틴이 취소되면 여기로 옵니다
            show("화면이 끝나서 코루틴도 취소됐습니다")
        }
    }
}
