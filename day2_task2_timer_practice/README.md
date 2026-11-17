# 오후 과제: 컵라면 타이머 만들기 (뼈대)

2일차 16:00~ | [전체 일정으로](../README.md) | 완성: [day2_task2_timer_answer](../day2_task2_timer_answer)

버튼을 누르면 시간을 세는 타이머입니다. 홈으로 나가도 알림에서 계속 셉니다. 화면이 아니라 Service가 시간을 세기 때문입니다. 화면과 알림은 만들어져 있습니다.

File > Open으로 이 폴더를 열고 Run 하면 컵라면 타이머 화면이 뜹니다. 아래 [1]부터 차례로 채우고, 채울 때마다 Run 합니다. 채우는 곳은 `practice/RamenTimer.kt` 하나이고, 파일 안에 `[1] 여기를 채웁니다`처럼 자리가 표시되어 있습니다.

**주의**: [1]을 채우기 전에는 버튼을 누르지 않습니다. 알림 없이 Service를 시작하면 조금 뒤에 앱이 꺼집니다.

## [1] 알림을 띄우고 Foreground Service로 올리기

`onStartCommand()` 안의 [1] 자리에 한 줄을 씁니다.

```kotlin
startForegroundWithNotification(total)
```

Foreground Service는 알림을 꼭 띄워야 합니다. 이 함수는 `RamenTimerScreen.kt`에 만들어져 있고, 첫 알림을 띄우면서 Service를 Foreground로 올립니다.

확인: "10초 (맛보기)"를 누르고 화면 위쪽을 끌어내리면 "10초 남았어요" 알림이 있습니다. 알림 권한 창이 뜨면 허용을 누릅니다.

## [2] 1초마다 하나씩 세기

[2] 자리에 코루틴을 시작하고, 그 안에서 남은 시간을 하나씩 줄입니다.

```kotlin
scope.launch {
    for (left in total downTo 1) {     // total 부터 1까지 거꾸로
        // 여기: 남은 시간 보여 주기, 1초 쉬기
    }
    // 여기: 다 세면 끝내기
}
```

- 남은 시간 보여 주기: `showLeft(left, total)` (화면과 알림의 숫자를 바꿈)
- 1초 쉬기: `delay(1000)`
- 끝내기: `finishTimer(total, startId)` ("다 익었어요" 알림을 남기고 Service를 끝냄)

확인: 숫자가 1초마다 줄어듭니다. 홈으로 나가도 알림의 숫자가 계속 줄고, 다 세면 "다 익었어요"가 됩니다.

## [3] 세던 코루틴 멈추기

`onDestroy()` 안의 [3] 자리에 한 줄을 씁니다. Service가 끝날 때 이 Service에서 시작한 코루틴도 모두 멈춥니다.

```kotlin
scope.cancel()
```

확인: 눈에 보이는 차이는 없습니다.

## 그대로 쓰는 파일

`practice/RamenTimerScreen.kt`: 남은 시간(`RamenTimer.remaining`), 알림, 과제에서 부르는 함수 세 개(`startForegroundWithNotification`, `showLeft`, `finishTimer`), 화면이 들어 있습니다.

막히면 [day2_task2_timer_answer](../day2_task2_timer_answer)의 같은 파일을 봅니다. [1] [2] [3]이 같은 자리에 채워져 있습니다.
