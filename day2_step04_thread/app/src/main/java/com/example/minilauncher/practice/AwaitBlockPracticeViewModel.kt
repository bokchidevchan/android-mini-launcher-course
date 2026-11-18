package com.example.minilauncher.practice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// "async 로 돌리고 await 하면 앱 전체가 멈추나?" 를 확인하는 실습 5단계.
// MainActivity.kt 의 blockVm.run(step = 1) 에서 숫자를 1부터 5까지 바꿔 가며 Run 합니다.
// Logcat 검색창에 tag:Await 를 넣습니다.
//
// 단계마다 "심장박동" 코루틴을 하나 같이 띄웁니다. main 스레드에서 300ms 마다 박동 n 을 찍습니다.
// 박동이 300ms 간격으로 계속 찍히면 main 스레드는 살아 있는 것(화면도 안 멈춤)이고,
// 박동이 끊기면 main 스레드가 막힌 것(화면도 멈춤)입니다.
//
// 결론 먼저
// - await 는 await 를 부른 코루틴 하나만 멈춥니다. 다른 코루틴과 main 스레드는 계속 돕니다.
// - 모든 걸 막는 건 await 가 아니라 Thread.sleep 이나 긴 계산처럼 스레드를 쥐고 안 놓는 코드입니다.
//   async 로 감싸도 그 async 가 main 에서 돌면 main 이 막힙니다.

private const val TAG = "Await"

class AwaitBlockPracticeViewModel : ViewModel() {

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
            1 -> step01AwaitOnlyPausesCaller()
            2 -> step02OutsideLaunchGoesOn()
            3 -> step03SleepInsideAsyncBlocksMain()
            4 -> step04MoveBlockingWorkOffMain()
            5 -> step05AwaitWhenAlreadyDone()
            else -> show("0부터 5까지의 숫자를 넣어 주세요")
        }
    }

    // main 스레드에서 300ms 마다 박동을 찍습니다. count 번 찍고 끝납니다
    private fun startHeartbeat(count: Int = 8): Job = viewModelScope.launch {
        repeat(count) { i ->
            show("    박동 $i")
            delay(300)
        }
    }

    // 1단계. await 하는 동안에도 다른 코루틴은 계속 돈다
    // 아래 코루틴은 await 에서 1.5초 멈춰 있습니다. 그동안 박동은 300ms 마다 끊기지 않고 찍힙니다.
    // 멈춘 건 "await 를 부른 코루틴" 하나뿐이고 main 스레드는 비어 있다는 뜻입니다
    private fun step01AwaitOnlyPausesCaller() {
        startHeartbeat()
        viewModelScope.launch {
            val data = async {
                delay(1500)
                "데이터"
            }
            show("await 앞. 여기서 이 코루틴만 멈춥니다")
            val result = data.await()
            show("await 뒤. 결과=$result (약 1500ms). 그동안 박동은 계속 찍혔습니다")
        }
    }

    // 2단계. await 는 launch 바깥 줄을 막지 않는다
    // run() 함수는 launch 를 걸어 놓고 바로 끝납니다. await 를 기다리는 건 launch 안쪽 아랫줄뿐입니다
    private fun step02OutsideLaunchGoesOn() {
        viewModelScope.launch {
            show("launch 안: await 앞")
            val result = async { delay(1000); "데이터" }.await()
            show("launch 안: await 뒤 결과=$result (약 1000ms)")
        }
        show("launch 바깥 다음 줄: await 를 안 기다리고 바로 찍힘")
    }

    // 3단계. async 안에서 Thread.sleep 을 하면 main 이 막힌다
    // async 는 "다른 스레드에서 돌려 줘" 가 아닙니다. 그냥 코루틴을 하나 더 만들 뿐이고,
    // viewModelScope 에서 만들면 main 스레드에서 돕니다. 그 안에서 스레드를 쥐고 자면 main 이 1.5초 막힙니다.
    // 로그를 보면 박동이 1.5초 동안 끊깁니다. 그동안 화면을 누르면 반응이 없습니다
    private fun step03SleepInsideAsyncBlocksMain() {
        startHeartbeat()
        viewModelScope.launch {
            delay(400)   // 박동이 몇 번 찍힌 뒤에 시작합니다
            val data = async {
                show("async 안: Thread.sleep 1500 시작. main 을 쥐고 잡니다")
                Thread.sleep(1500)
                show("async 안: 깼음")
                "데이터"
            }
            show("await 결과=${data.await()}. 박동이 1.5초 끊긴 걸 확인합니다")
        }
    }

    // 4단계. 막는 일은 다른 스레드로 보내면 main 은 안 막힌다
    // 3단계와 같은데 async(Dispatchers.IO) 하나만 바꿨습니다. 잠드는 건 IO 스레드라 박동이 안 끊깁니다.
    // (Thread.sleep 자리에 실제로는 파일 읽기, 오래된 블로킹 라이브러리 호출 같은 것이 옵니다)
    private fun step04MoveBlockingWorkOffMain() {
        startHeartbeat()
        viewModelScope.launch {
            delay(400)
            val data = async(Dispatchers.IO) {
                show("async(IO) 안: Thread.sleep 1500 시작. IO 스레드를 쥐고 잡니다")
                Thread.sleep(1500)
                show("async(IO) 안: 깼음")
                "데이터"
            }
            show("await 결과=${data.await()}. 박동은 계속 찍혔습니다")
        }
    }

    // 5단계. await 가 항상 멈추는 건 아니다
    // 결과가 아직 없을 때만 멈춥니다. 이미 끝난 Deferred 를 await 하면 바로 값을 꺼내고 지나갑니다.
    // 같은 Deferred 를 여러 번 await 해도 일은 한 번만 합니다
    private fun step05AwaitWhenAlreadyDone() {
        viewModelScope.launch {
            val data = async {
                show("  async 안: 일 시작 (한 번만 찍힘)")
                delay(500)
                "데이터"
            }
            show("첫 await: 아직 결과 없음 → 멈춤")
            show("첫 await 결과=${data.await()} (약 500ms)")
            delay(1000)   // 그사이 다른 일을 했다고 칩니다
            show("두 번째 await: 이미 끝난 결과 → 안 멈춤")
            show("두 번째 await 결과=${data.await()} (바로 나옴)")
        }
    }
}
