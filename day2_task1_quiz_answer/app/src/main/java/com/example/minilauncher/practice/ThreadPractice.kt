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

// 2일차 오전 스레드 실습 6단계. 이론 장의 코드가 단계마다 하나씩 들어 있습니다.
// MainActivity.kt 의 threadPractice(this, step = 1) 에서 숫자를 1부터 6까지 바꿔 가며
// Run 합니다. 0 이면 아무것도 안 합니다.
// Logcat 검색창에 tag:Thread 를 넣습니다. 줄마다 지난 시간과 [스레드 이름] 이 붙습니다.
// 직접 써 보는 파일은 같은 폴더의 ThreadPractice_My.kt 입니다.

private const val TAG = "Thread"
private var startedAt = 0L

private fun show(message: String) {
    val elapsed = System.currentTimeMillis() - startedAt
    Log.d(TAG, "${elapsed}ms [${Thread.currentThread().name}] $message")
}

fun threadPractice(activity: ComponentActivity, step: Int) {
    if (step == 0) return
    startedAt = System.currentTimeMillis()
    show("===== ${step}단계 =====")
    when (step) {
        1 -> step01WhoAmI()
        2 -> step02MessageQueue()
        3 -> step03BlockMain()
        4 -> step04NewThread()
        5 -> step05BackToMain(activity)
        6 -> step06Coroutine(activity)
        else -> show("0부터 6까지의 숫자를 넣어 주세요")
    }
}

// 1단계. 지금 어느 스레드인가
// onCreate 는 메인 스레드에서 불립니다. 프로세스 번호(pid)와 스레드 번호(tid)가 같으면
// 메인 스레드입니다
private fun step01WhoAmI() {
    show("스레드 이름: ${Thread.currentThread().name}")
    show("pid ${Process.myPid()}, tid ${Process.myTid()}")
    show("메인 스레드인가: ${Looper.myLooper() == Looper.getMainLooper()}")
    // 바꿔 보기: 위 세 줄을 thread { } 로 감싸면 이름과 tid 가 어떻게 바뀔까요?
}

// 2단계. 메인 스레드의 MessageQueue
// post 는 Message 를 큐에 넣기만 합니다. 지금 처리 중인 Message(onCreate)가
// 끝나야 차례가 옵니다. 큐는 실행 시각 순서입니다
private fun step02MessageQueue() {
    val handler = Handler(Looper.getMainLooper())
    show("1. post 앞")
    handler.post { show("3. 큐에 넣어 둔 일. onCreate 가 끝난 뒤에 실행") }
    handler.postDelayed({ show("4. 1초 뒤에 실행되도록 넣어 둔 일") }, 1000)
    show("2. post 뒤. 넣기만 하고 바로 지나왔습니다")
    // 바꿔 보기: 1000 을 3000 으로 바꾸면 4번 줄의 시간이 어떻게 될까요?
}

// 3단계. 메인 스레드를 막으면
// Message 하나가 3초 걸리면 그동안 화면 그리기와 터치 처리가 모두 큐에서 기다립니다
private fun step03BlockMain() {
    Handler(Looper.getMainLooper()).postDelayed({
        show("3초 걸리는 일 시작. 지금 앱 아이콘을 눌러 봅니다")
        Thread.sleep(3000)
        show("끝. 이제야 화면이 반응합니다")
    }, 1500)    // 화면이 뜬 뒤(1.5초 뒤)에 시작합니다
    // 바꿔 보기: 3000 을 10000 으로 바꾸고 그동안 화면을 여러 번 누릅니다.
    // Logcat 검색창을 모두 지우고 ANR in 을 찾아봅니다. 확인했으면 되돌립니다
}

// 4단계. 새 스레드에서 일하기
// 오래 걸리는 일을 새 스레드로 보내면 메인 스레드는 기다리지 않고 다음 줄로 갑니다
private fun step04NewThread() {
    show("1. thread 앞")
    thread {
        show("3. 새 스레드에서 3초 걸리는 일 시작")
        Thread.sleep(3000)
        show("4. 새 스레드의 일 끝")
    }
    show("2. thread 뒤. 바로 지나왔습니다. 화면도 안 멈춥니다")
    // 바꿔 보기: thread { } 를 하나 더 만들면 스레드 이름이 어떻게 붙을까요?
}

// 5단계. 일은 새 스레드에서, 화면은 메인 스레드에서
// 화면은 메인 스레드만 고칠 수 있습니다. 결과가 나오면 runOnUiThread 로 메인 스레드에
// 돌아옵니다
private fun step05BackToMain(activity: ComponentActivity) {
    thread {
        show("새 스레드에서 받아 오는 중")
        Thread.sleep(1000)
        val text = "받아 온 글자"
        activity.runOnUiThread {
            show("메인 스레드로 돌아옴")
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
        }
    }
    // 바꿔 보기: Toast 줄을 runOnUiThread { } 밖(바로 위)으로 옮기면 앱이 꺼집니다.
    // 새 스레드에는 Looper 가 없어서입니다 (Logcat 의 Looper.prepare 가 든 줄).
    // View 를 직접 고치면 CalledFromWrongThreadException 이 납니다. 확인했으면 되돌립니다
}

// 6단계. 같은 일을 코루틴으로
// 5단계와 하는 일이 같습니다. 콜백이 안으로 깊어지지 않고 위에서 아래로 읽힙니다
private fun step06Coroutine(activity: ComponentActivity) {
    activity.lifecycleScope.launch {
        show("코루틴 시작")
        val text = withContext(Dispatchers.IO) {
            show("IO 스레드에서 받아 오는 중")
            Thread.sleep(1000)
            "받아 온 글자"
        }
        show("다음 줄에서 바로 받음")
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
    // 바꿔 보기: Dispatchers.IO 를 Dispatchers.Default 로 바꿔도 스레드 이름은
    // DefaultDispatcher-worker 그대로입니다
}
