# 오전 과제: 복습 퀴즈 앱 만들기 (완성)

2일차 11:00~ | [전체 일정으로](../README.md) | 뼈대: [day2_task1_quiz_practice](../day2_task1_quiz_practice)

`day2_task1_quiz_practice`에서 직접 만들어 본 뒤에 여는 완성 프로젝트입니다. Android Studio에서 이 폴더를 열고 Run 하면 복습 퀴즈 화면이 바로 뜹니다. 뼈대와 앱 ID가 같아서, 설치하면 에뮬레이터의 앱이 이 앱으로 바뀝니다.

`practice/QuizApp.kt`의 같은 자리에 [1] [2] [3]이, `practice/FreezeFix.kt`에 [4]가 채워져 있습니다.

## 단계별 정답

### [1] 인터넷에서 문제 목록 받아 오기

```kotlin
suspend fun fetchQuizzes(): List<Quiz> = withContext(Dispatchers.IO) {
    val text = URL(QUIZ_URL).readText()     // 주소에 있는 글자를 모두 읽어 옴
    parseQuizzes(text)                      // 글자를 문제 목록으로 바꿈 (마지막 줄이 결과)
}
```

인터넷은 오래 걸릴 수 있어서 IO 스레드(`Dispatchers.IO`)에서 읽습니다. 메인 스레드에서 읽으면 화면이 멈추고, Android는 아예 오류(`NetworkOnMainThreadException`)를 냅니다.

### [2] 버튼을 누르면 받아 와서 보여 주기

```kotlin
fun load() {
    _state.value = QuizState.Loading                          // 화면을 "받아 오는 중"으로
    viewModelScope.launch {                                   // 코루틴 시작
        val quizzes = fetchQuizzes()                          // 받아 오는 동안 기다림 (화면은 안 멈춤)
        _state.value = QuizState.Success(quizzes.random())    // 하나를 골라 보여 줌
    }
}
```

상태를 바꾸기만 하면 화면이 따라 바뀝니다. 화면이 상태를 `collectAsStateWithLifecycle()`로 보고 있기 때문입니다. `viewModelScope`는 화면을 완전히 닫으면 이 코루틴을 알아서 멈춥니다.

### [3] 인터넷이 안 될 때 안내하기

```kotlin
    viewModelScope.launch {
        try {
            val quizzes = fetchQuizzes()
            _state.value = QuizState.Success(quizzes.random())
        } catch (e: Exception) {
            _state.value = QuizState.Error("받아 오지 못했습니다. 인터넷 연결을 확인해 주세요")
        }
    }
```

인터넷이 끊겨 있으면 `readText()`에서 예외가 납니다. 잡지 않으면 앱이 꺼지므로 `try`, `catch`로 잡아 실패 상태를 보여 줍니다.

### [4] 멈추는 화면 고치기

```kotlin
result = "일하는 중"
scope.launch {
    result = withContext(Dispatchers.Default) { slowWork() }   // 3초 걸리는 일만 다른 스레드에서
}
```

버튼을 누른 메인 스레드는 코루틴을 시작만 하고 바로 돌아가서, 그동안 화면 그리기와 터치를 계속 처리합니다. 3초 뒤 결과만 받아 `result`에 넣습니다.

## 파일

| 파일 | 내용 |
|---|---|
| `practice/QuizApp.kt` | [1] [2] [3]. 받아 오는 함수, ViewModel, 화면을 여는 Activity |
| `practice/QuizScreen.kt` | 주소, 문제, 화면 상태, 화면 (뼈대와 같음) |
| `practice/FreezeFix.kt` | [4]. 3초 걸리는 일을 다른 스레드로 옮긴 화면. 앱 목록의 "멈춤 고치기" 아이콘으로 엶 |
