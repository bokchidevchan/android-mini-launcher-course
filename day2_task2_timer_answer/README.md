# 오후 과제: 컵라면 타이머 만들기 (완성)

2일차 16:00~ | [전체 일정으로](../README.md) | 뼈대: [day2_task2_timer_practice](../day2_task2_timer_practice)

`day2_task2_timer_practice`에서 직접 만들어 본 뒤에 여는 완성 프로젝트입니다. Android Studio에서 이 폴더를 열고 Run 하면 컵라면 타이머 화면이 바로 뜹니다. 뼈대와 앱 ID가 같아서, 설치하면 에뮬레이터의 앱이 이 앱으로 바뀝니다.

`practice/RamenTimer.kt`의 같은 자리에 [1] [2] [3]이 채워져 있습니다.

## 단계별 정답

### [1] 알림을 띄우고 Foreground Service로 올리기

```kotlin
startForegroundWithNotification(total)
```

Foreground Service는 시작하자마자 알림을 띄워야 합니다. 안 띄우면 몇 초 뒤 시스템이 앱을 끕니다. 이 함수 안에서 알림 채널 만들기, 첫 알림 만들기, `ServiceCompat.startForeground`를 차례로 합니다(`RamenTimerScreen.kt`).

### [2] 1초마다 하나씩 세기

```kotlin
scope.launch {
    for (left in total downTo 1) {
        showLeft(left, total)       // 화면과 알림에 남은 시간
        delay(1000)                 // 1초 쉼
    }
    finishTimer(total, startId)     // "다 익었어요" 알림을 남기고 Service 를 끝냄
}
```

`onStartCommand`는 메인 스레드에서 불립니다. 여기서 10초를 직접 기다리면 앱이 멈추므로, 세는 일은 코루틴에 맡기고 `onStartCommand`는 바로 끝납니다. `showLeft`가 바꾸는 `RamenTimer.remaining`은 화면도 보고 있는 값이라 화면의 숫자도 같이 바뀝니다.

### [3] 세던 코루틴 멈추기

```kotlin
override fun onDestroy() {
    scope.cancel()
    super.onDestroy()
}
```

Service가 끝나도 코루틴은 저절로 멈추지 않습니다. 이 Service에서 시작한 코루틴 묶음(`scope`)을 취소해서 함께 멈춥니다.

## 파일

| 파일 | 내용 |
|---|---|
| `practice/RamenTimer.kt` | [1] [2] [3]. 시간을 세는 Service와 화면을 여는 Activity |
| `practice/RamenTimerScreen.kt` | 남은 시간, 알림, 과제에서 부르는 함수 세 개, 화면 (뼈대와 같음) |
