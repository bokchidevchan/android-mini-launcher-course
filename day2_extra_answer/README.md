# 2일차 8교시: 반응 속도 게임 만들기 (완성)

2일차 8교시 | [전체 일정으로](../README.md) | 뼈대: [day2_extra_practice](../day2_extra_practice)

`day2_extra_practice`에서 직접 만들어 본 뒤에 여는 완성 프로젝트입니다. Android Studio에서 이 폴더를 열고 Run 하면 게임 화면이 바로 뜹니다. 뼈대와 앱 ID가 같아서, 설치하면 에뮬레이터의 앱이 이 앱으로 바뀝니다.

`practice/ReactionGame.kt`의 같은 자리에 [1] [2] [3]이 채워져 있습니다.

## 단계별 정답

### [1] 누르면 기다렸다가 초록색으로 바꾸기

```kotlin
screen = "기다림"
waiting = scope.launch {
    delay(Random.nextLong(1000, 4000))       // 1~4초 쉼
    goTime = System.currentTimeMillis()      // 초록색이 된 시각
    screen = "지금"
}
```

`delay`는 쉬는 동안 메인 스레드를 막지 않습니다. 그래서 빨간 화면에서도 누르는 것이 바로 처리됩니다. 코루틴을 `waiting`에 담아 두는 것은 [2]에서 취소하기 위해서입니다.

### [2] 너무 일찍 누르면 멈추기

```kotlin
waiting?.cancel()
screen = "너무 빠름"
```

`cancel()`을 하면 `delay`에서 쉬던 코루틴이 그 자리에서 끝나서, 뒤의 `screen = "지금"`이 실행되지 않습니다.

### [3] 제때 누르면 걸린 시간 보여 주기

```kotlin
result = System.currentTimeMillis() - goTime
screen = "결과"
```

누른 시각에서 초록색이 된 시각을 빼면 걸린 시간(ms)입니다.

## 파일

| 파일 | 내용 |
|---|---|
| `practice/ReactionGame.kt` | [1] [2] [3]. 화면과 동작, 화면을 여는 Activity |
