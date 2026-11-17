package com.example.minilauncher.practice

import android.os.Handler
import android.os.Looper
import android.os.Process
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

// 직접 써 보는 파일입니다.
// ThreadPractice.kt 의 단계별 코드를 보고, 같은 단계의 빈 함수 안에 옮겨 적거나 복사해서 붙입니다.
// MainActivity.kt 의 threadPractice(this, step = 1) 을 myThreadPractice(this, step = 1) 로 바꾸고 Run 합니다.
// Logcat 검색창에 tag:MyThread 를 넣으면 이 파일이 남긴 줄만 보입니다 (tag:Thread 로도 같이 보입니다).
// 위의 import 가운데 회색으로 보이는 것은 아직 안 써서 그런 것이니 그대로 둡니다.

private const val TAG = "MyThread"
private var startedAt = 0L

private fun show(message: String) {
    val elapsed = System.currentTimeMillis() - startedAt
    Log.d(TAG, "${elapsed}ms [${Thread.currentThread().name}] $message")
}

fun myThreadPractice(activity: ComponentActivity, step: Int) {
    if (step == 0) return
    startedAt = System.currentTimeMillis()
    show("===== 내가 쓴 ${step}단계 =====")
    when (step) {
        1 -> myThStep01()
        2 -> myThStep02()
        3 -> myThStep03()
        4 -> myThStep04()
        5 -> myThStep05(activity)
        6 -> myThStep06(activity)
        else -> show("0부터 6까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 지금 어느 스레드인가 (ThreadPractice.kt 의 step01WhoAmI)
private fun myThStep01() {
    // 여기에 씁니다
}

// 2단계. 메인 스레드의 MessageQueue (ThreadPractice.kt 의 step02MessageQueue)
private fun myThStep02() {
    // 여기에 씁니다
}

// 3단계. 메인 스레드를 막으면 (ThreadPractice.kt 의 step03BlockMain)
private fun myThStep03() {
    // 여기에 씁니다
}

// 4단계. 새 스레드에서 일하기 (ThreadPractice.kt 의 step04NewThread)
private fun myThStep04() {
    // 여기에 씁니다
}

// 5단계. 일은 새 스레드에서, 화면은 메인 스레드에서 (ThreadPractice.kt 의 step05BackToMain)
private fun myThStep05(activity: ComponentActivity) {
    // 여기에 씁니다
}

// 6단계. 같은 일을 코루틴으로 (ThreadPractice.kt 의 step06Coroutine)
private fun myThStep06(activity: ComponentActivity) {
    // 여기에 씁니다
}
