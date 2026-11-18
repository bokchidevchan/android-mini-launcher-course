# 2-1 메인 스레드 (1교시: 스레드 실습)

2일차 09:00~10:00 | [전체 일정으로](../README.md) | 다음: [day2_step04_coroutine](../day2_step04_coroutine)

`day2_step04_thread` 폴더를 열고 시작합니다. 메인 스레드를 막으면 화면이 왜 멈추는지 직접 겪어 보고, 새 스레드와 코루틴으로 일을 옮겨 봅니다. 2교시부터는 `day2_step04_coroutine` 폴더를 씁니다.

## 스레드 실습 6단계

`MainActivity.kt`의 `threadPractice(this, step = 1)`에서 숫자를 1부터 6까지 바꿔 가며 Run 합니다. Logcat 검색칸에 `tag:Thread`를 넣습니다. 코드는 `practice/ThreadPractice.kt`에 있습니다. 직접 써 보려면 `practice/ThreadPractice_My.kt`의 빈 함수에 옮겨 적고 `myThreadPractice(this, step = 1)`로 바꿔 실행합니다.

| 단계 | 실습하는 것 |
|---|---|
| 1 | 지금 어느 스레드인가: 스레드 이름, pid와 tid |
| 2 | 메인 스레드의 MessageQueue: `Handler.post`는 큐에 넣기만 합니다 |
| 3 | 메인 스레드를 3초 막으면 화면이 멈춥니다 |
| 4 | 새 스레드에서 일하기: 메인 스레드는 기다리지 않습니다 |
| 5 | 일은 새 스레드에서, 화면은 `runOnUiThread`로 메인 스레드에서 |
| 6 | 같은 일을 코루틴으로 (`withContext(Dispatchers.IO)`) |

## 실험실: ANR 만들고 고치기

앱을 실행하면 앱 목록에 "실험실" 아이콘이 하나 더 생깁니다. "메인 스레드에서 20초 작업"을 누르면 화면이 멈추고, "코루틴으로 20초 작업"을 누르면 같은 일을 해도 화면이 멈추지 않습니다. 두 버튼의 차이는 `lab/LabActivity.kt`에서 `heavyWork()`를 `withContext(Dispatchers.Default)` 안에서 부르는지 하나입니다.

## async/await 실습 (ViewModel)

`MainActivity.kt`의 `asyncVm.run(step = 9)`, `blockVm.run(step = 1)`에서 숫자를 바꿔 가며 Run 합니다. 0이면 건너뜁니다. 코드는 `practice/` 폴더에 있습니다.

`AsyncPracticeViewModel.kt` (Logcat `tag:Async`)

| 단계 | 실습하는 것 |
|---|---|
| 1 | suspend 함수를 차례로 부르기: 1초 + 1초 = 2초 |
| 2 | `async`로 동시에 시작하고 `await`로 받기: 약 1초 |
| 3 | 흔한 실수: `async { }.await()`를 붙여 쓰면 다시 2초 |
| 4 | 여러 개를 `map { async { } }.awaitAll()`로 한꺼번에 |
| 5 | suspend는 `delay` 같은 중단 지점에서 멈추고 main 스레드를 양보합니다 |
| 6 | `Thread.sleep`은 멈추는 게 아니라 스레드를 막습니다 |
| 7 | `suspend`를 붙여도 안에 중단 함수가 없으면 한 번도 안 멈춥니다 |
| 8 | 계산만 하는 일은 `async(Dispatchers.Default)`로 스레드를 나눠야 동시에 돕니다 |
| 9 | 실제 화면처럼 API 다섯 개 섞기: 순서, 병렬, 기다렸다 한꺼번에, 오는 대로 띄우기 |

`AwaitBlockPracticeViewModel.kt` (Logcat `tag:Await`): await가 모든 걸 막는지 300ms 박동 로그로 확인합니다

| 단계 | 실습하는 것 |
|---|---|
| 1 | await 하는 동안에도 다른 코루틴(박동)은 계속 돕니다 |
| 2 | await는 launch 바깥 줄을 막지 않습니다 |
| 3 | async 안에서 `Thread.sleep`을 하면 main이 막혀 박동이 끊깁니다 |
| 4 | 같은 일을 `async(Dispatchers.IO)`로 보내면 박동이 안 끊깁니다 |
| 5 | 이미 끝난 결과를 await 하면 멈추지 않습니다 |

[전체 일정으로](../README.md)
