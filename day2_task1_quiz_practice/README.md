# 오전 과제: 복습 퀴즈 앱 만들기 (뼈대)

2일차 11:00~ | [전체 일정으로](../README.md) | 완성: [day2_task1_quiz_answer](../day2_task1_quiz_answer)

버튼을 누르면 인터넷에서 문제를 받아 와 하나를 보여 주는 앱입니다. 화면은 만들어져 있습니다.

File > Open으로 이 폴더를 열고 Run 하면 복습 퀴즈 화면이 뜹니다. 아래 [1]부터 차례로 채우고, 채울 때마다 Run 합니다. 파일 안에 `[1] 여기를 채웁니다`처럼 자리가 표시되어 있습니다.

## [1] 문제 목록 받아 오기

`practice/QuizApp.kt`의 `fetchQuizzes()` 안. `emptyList()`를 지우고 두 줄을 씁니다.

- 주소의 글자를 모두 읽습니다: `URL(QUIZ_URL).readText()`
- 읽은 글자를 문제 목록으로 바꿔 돌려줍니다: `parseQuizzes(글자)`

확인: 화면은 아직 그대로입니다.

## [2] 버튼을 누르면 문제 보여 주기

같은 파일의 `load()` 안. 안내 줄(`아직 [2] 를 안 채웠어요`)을 지우고 씁니다.

1. 상태를 "받아 오는 중"으로 바꿉니다: `_state.value = QuizState.Loading`
2. 코루틴을 시작합니다: `viewModelScope.launch { }`
3. 그 안에서 `fetchQuizzes()`로 목록을 받고, 하나를 골라 상태에 넣습니다: `QuizState.Success(목록.random())`

확인: 누르면 잠깐 "받아 오는 중"이 보이고 문제가 뜹니다. "정답 보기"를 누르면 답이 보입니다.

## [3] 인터넷이 안 될 때

[2]에서 `launch { }` 안에 쓴 두 줄을 `try { } catch (e: Exception) { }`로 감쌉니다. `catch` 안에서는 실패 글자를 상태에 넣습니다: `QuizState.Error("인터넷 연결을 확인해 주세요")`

확인: 에뮬레이터의 Wi-Fi와 모바일 데이터를 끄고 누르면 실패 글자가 뜹니다.

## [4] 멈추는 화면 고치기

에뮬레이터의 앱 목록에서 "멈춤 고치기" 아이콘을 엽니다. "3초 걸리는 일"을 누르면 3초 동안 화면이 멈춥니다.

`practice/FreezeFix.kt`의 `result = slowWork()` 한 줄을 아래 네 줄로 바꿉니다. 오래 걸리는 일만 다른 스레드로 보내는 것입니다.

```kotlin
result = "일하는 중"
scope.launch {
    result = withContext(Dispatchers.Default) { slowWork() }
}
```

확인: "3초 걸리는 일"을 누른 뒤 "눌러 보기"를 누르면 숫자가 바로 오릅니다.

## 그대로 쓰는 파일

`practice/QuizScreen.kt`: 주소(`QUIZ_URL`), 문제(`Quiz`), 화면 상태(`QuizState`), 화면이 들어 있습니다.

막히면 [day2_task1_quiz_answer](../day2_task1_quiz_answer)의 같은 파일을 봅니다. [1] [2] [3]이 같은 자리에 채워져 있습니다.
