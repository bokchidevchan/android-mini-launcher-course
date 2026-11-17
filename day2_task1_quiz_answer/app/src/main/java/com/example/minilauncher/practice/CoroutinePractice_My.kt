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

// 직접 써 보는 파일입니다.
// CoroutinePractice.kt 의 단계별 코드를 보고, 같은 단계의 빈 함수 안에 옮겨 적거나 복사해서 붙입니다.
// MainActivity.kt 의 coroutinePractice(lifecycleScope, step = 1) 을 myCoroutinePractice(lifecycleScope, step = 1) 로 바꾸고 Run 합니다.
// Logcat 검색창에 tag:MyCoroutine 을 넣으면 이 파일이 남긴 줄만 보입니다.
// loadLabel, loadOrFail, LoadState 처럼 CoroutinePractice.kt 에 있는 것은 여기서도 그대로 쓸 수 있습니다.

private const val TAG = "MyCoroutine"
private var startedAt = 0L

// 시작한 뒤 몇 ms 가 지났는지와 스레드 이름을 같이 찍습니다
private fun show(message: String) {
    val elapsed = System.currentTimeMillis() - startedAt
    Log.d(TAG, "${elapsed}ms [${Thread.currentThread().name}] $message")
}

fun myCoroutinePractice(scope: CoroutineScope, step: Int) {
    scope.launch {
        delay(1500)                 // 화면이 뜬 뒤에 시작합니다
        startedAt = System.currentTimeMillis()
        show("===== 내가 쓴 ${step}단계 =====")
        when (step) {
            1 -> myCoStep01(scope)
            2 -> myCoStep02(scope)
            3 -> myCoStep03(scope)
            4 -> myCoStep04(scope)
            5 -> myCoStep05(scope)
            6 -> myCoStep06(scope)
            7 -> myCoStep07(scope)
            8 -> myCoStep08(scope)
            9 -> myCoStep09(scope)
            10 -> myCoStep10(scope)
            11 -> myCoStep11(scope)
            else -> show("1부터 11까지의 숫자를 넣어 주세요")
        }
    }
}

// 1단계. launch (CoroutinePractice.kt 의 step01Launch)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep01(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 2단계. delay 는 스레드를 막지 않습니다 (CoroutinePractice.kt 의 step02Delay)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep02(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 3단계. suspend 함수 (CoroutinePractice.kt 의 step03Suspend)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep03(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 4단계. async 와 await (CoroutinePractice.kt 의 step04Async)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep04(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 5단계. withContext 로 스레드 바꾸기 (CoroutinePractice.kt 의 step05WithContext)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep05(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 6단계. 취소 (CoroutinePractice.kt 의 step06Cancel)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep06(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 7단계. 예외 처리 (CoroutinePractice.kt 의 step07Exception)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep07(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 8단계. Flow (CoroutinePractice.kt 의 step08Flow)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep08(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 9단계. StateFlow (CoroutinePractice.kt 의 step09StateFlow)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep09(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 10단계. 종합: 불러오는 중, 성공, 실패 (CoroutinePractice.kt 의 step10LoadingState)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep10(scope: CoroutineScope) {
    // 여기에 씁니다
}

// 11단계. 생명주기와 함께 취소되는 코루틴 (CoroutinePractice.kt 의 step11Lifecycle)
@Suppress("UNUSED_PARAMETER")
private fun myCoStep11(scope: CoroutineScope) {
    // 여기에 씁니다
}
