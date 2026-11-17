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

[전체 일정으로](../README.md)
