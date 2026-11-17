# 오후 과제: 컵라면 타이머 만들기 (뼈대)

2일차 16:00~ | [전체 일정으로](../README.md) | 완성: [day2_task2_timer_answer](../day2_task2_timer_answer)

버튼을 누르면 시간을 세는 타이머입니다. 홈으로 나가도 알림에서 계속 셉니다. 화면이 아니라 Service가 시간을 세기 때문입니다. 화면과 알림은 만들어져 있습니다.

File > Open으로 이 폴더를 열고 Run 하면 컵라면 타이머 화면이 뜹니다. 아래 [1]부터 차례로 채우고, 채울 때마다 Run 합니다. 채우는 곳은 `practice/RamenTimer.kt` 하나이고, 파일 안에 `[1] 여기를 채웁니다`처럼 자리가 표시되어 있습니다.

전체 흐름은 이렇습니다.

```
버튼 누름 → Service 시작(onStartCommand) → [1] 알림을 띄움 → [2] 1초마다 하나씩 셈 → 다 세면 끝 → [3] 정리
```

**주의**: [1]을 채우기 전에는 버튼을 누르지 않습니다. 알림 없이 Service를 시작하면 조금 뒤에 앱이 꺼집니다.

## [1] 알림을 띄우고 Foreground Service로 올리기

**하는 일**: 화면을 나가도 계속 도는 Service(Foreground Service)는 "지금 일하는 중"이라는 알림을 꼭 띄워야 합니다. 시작하자마자 알림을 띄웁니다.

**채울 곳**: `onStartCommand()` 안의 `[1] 여기를 채웁니다` 아래에 한 줄을 씁니다.

```kotlin
startForegroundWithNotification(total)
```

- `total`은 셀 시간(초)입니다. 바로 위 줄에서 버튼이 넘겨준 값(10 또는 180)을 받아 둡니다.
- `startForegroundWithNotification`은 `RamenTimerScreen.kt`에 만들어져 있습니다. 알림 채널을 만들고, "10초 남았어요" 알림을 띄우면서 Service를 Foreground로 올립니다.

**확인**: "10초 (맛보기)"를 누르고 화면 위쪽을 끌어내리면 "10초 남았어요" 알림이 있습니다. 알림 권한 창이 뜨면 허용을 누릅니다. 숫자는 아직 안 줄어듭니다.

## [2] 1초마다 하나씩 세기

**하는 일**: 코루틴을 시작해서, 남은 시간을 1초마다 하나씩 줄입니다. 다 세면 "다 익었어요"로 바꾸고 Service를 끝냅니다. 코루틴에서 세기 때문에 기다리는 동안 다른 일이 막히지 않습니다.

**채울 곳**: `[2] 여기를 채웁니다` 아래. 아래 모양으로 쓰고 빈칸을 채웁니다.

```kotlin
scope.launch {                          // 코루틴 시작
    for (left in total downTo 1) {      // left 가 total, total-1, ..., 1 로 바뀌며 반복
        ______________                  // ① 남은 시간을 화면과 알림에 보여 줌
        ______________                  // ② 1초 쉼
    }
    ______________                      // ③ 다 세면 끝냄
}
```

- ①에 쓸 것: `showLeft(left, total)`. 화면의 숫자와 알림의 "○초 남았어요"를 함께 바꿉니다.
- ②에 쓸 것: `delay(1000)`. 1000은 1000분의 1초 단위라 1초입니다.
- ③에 쓸 것: `finishTimer(total, startId)`. "다 익었어요" 알림을 남기고 Service를 끝냅니다.
- `showLeft`, `finishTimer`도 `RamenTimerScreen.kt`에 만들어져 있습니다.

**확인**: 숫자가 1초마다 줄어듭니다. 홈으로 나가서 화면 위쪽을 끌어내려도 알림의 숫자가 계속 줄고, 다 세면 "다 익었어요"가 됩니다.

## [3] 세던 코루틴 멈추기

**하는 일**: Service가 끝날 때, 이 Service에서 시작한 코루틴도 같이 멈추게 합니다. 안 하면 Service가 사라진 뒤에도 코루틴이 남아 돌 수 있습니다.

**채울 곳**: `onDestroy()` 안의 `[3] 여기를 채웁니다` 아래에 한 줄을 씁니다.

```kotlin
scope.cancel()
```

- `scope`는 파일 위쪽에 만들어 둔 "이 Service의 코루틴 묶음"입니다. `cancel()`하면 그 안의 코루틴이 모두 멈춥니다.

**확인**: 눈에 보이는 차이는 없습니다. 빨간 줄 없이 Run 되면 됩니다.

## 그대로 쓰는 파일

`practice/RamenTimerScreen.kt`: 남은 시간(`RamenTimer.remaining`), 알림, 과제에서 부르는 함수 세 개(`startForegroundWithNotification`, `showLeft`, `finishTimer`), 화면이 들어 있습니다.

막히면 [day2_task2_timer_answer](../day2_task2_timer_answer)를 엽니다. 같은 파일의 같은 자리에 [1] [2] [3]이 채워져 있고, README에 단계별 정답이 있습니다.
