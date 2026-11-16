# 2일차 8교시: 앱 만들어 보기 (반응 속도 게임)

[전체 일정으로](../README.md)

7교시까지 마치고 시간이 남으면 만드는 앱입니다. 화면이 초록색으로 바뀌는 순간 얼마나 빨리 누르는지 재는 게임입니다. 기다리는 일은 코루틴이, 상태는 ViewModel과 StateFlow가 맡습니다. `day2_step06_service`와 같은 프로젝트에 이 앱의 뼈대를 더했습니다. 정답은 [day2_extra_answer](../day2_extra_answer) 폴더에 따로 있습니다.

## 요구사항

1. 화면을 누르면 빨간색이 되고, 1~4초 사이 무작위 시간이 지난 뒤 초록색으로 바뀝니다
2. 초록색일 때 누르면 걸린 시간(ms)을 보여 줍니다
3. 초록색이 되기 전에 누르면 "너무 빨랐어요"를 보여 주고, 그 뒤로 초록색으로 바뀌면 안 됩니다
4. 결과 화면을 누르면 다시 시작합니다
5. 도전: 가장 빨랐던 기록을 기억해 보여 줍니다
6. 도전: 게임 도중에 화면을 돌려도 이어지는지 확인합니다

## 만드는 방법

1. Android Studio에서 File > Open으로 이 폴더를 열고 Run 합니다.
2. 에뮬레이터의 앱 목록(홈 화면을 위로 밀어 올림)에서 아래 표의 연습 아이콘을 누릅니다.
3. 연습 파일을 고치고 다시 Run 합니다. 막히면 아래 힌트를 열고, 그래도 안 되면 `day2_extra_answer` 폴더의 정답 파일을 봅니다.

| 아이콘 | 연습 파일 | 정답 파일 |
|---|---|---|
| 반응 속도 게임 연습 | `practice/ReactionGame.kt`의 `ReactionViewModel` | `practice/ReactionAnswer.kt` |

## 막히면 보는 힌트

먼저 힌트 없이 해 보고, 막힐 때만 엽니다.

<details>
<summary>힌트 열기</summary>

- 지금 상태가 Ready, Result, TooEarly 면: Waiting 으로 바꾸고, 1~4초 가운데 아무 시간이나 기다린 뒤 Go 로 바꿉니다.
- 지금 상태가 Waiting 이면: 기다리던 코루틴을 취소하고 TooEarly 로 바꿉니다.
- 지금 상태가 Go 면: 초록색이 된 뒤 지금까지 걸린 시간을 재서 Result 로 바꿉니다.

```kotlin
// 아래 두 줄은 onTap 함수 밖, 클래스 안에 둡니다
private var job: Job? = null          // 초록색을 기다리는 코루틴
private var goAt = 0L                 // 초록색이 된 시각

when (_state.value) { GameState.Waiting -> ...; GameState.Go -> ...; else -> ... }
job = viewModelScope.launch { delay(Random.nextLong(1000, 4000)); goAt = System.currentTimeMillis(); _state.value = GameState.Go }
job?.cancel()
val ms = System.currentTimeMillis() - goAt
```

</details>
