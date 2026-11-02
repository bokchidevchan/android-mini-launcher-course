# 3-2 Logcat 로그 분석

3일차 13:00~15:00 | [전체 일정으로](../README.md)

`step07x_bughunt` 폴더를 열고 시작합니다.

이 폴더는 [step07_launcher](../step07_launcher)에 버그 세 개를 일부러 넣은 버전입니다. 코드를 먼저 읽지 않고, 앱을 실행해서 Logcat만 보고 원인을 찾습니다.

## 준비

1. 이 폴더를 Android Studio로 열고 Run 합니다.
2. Logcat 검색칸에 `package:mine`을 넣어 이 앱 로그만 봅니다.

## 버그 1: 앱 아이콘을 길게 누르면 앱이 꺼집니다

- 아무 앱이나 길게 눌러 봅니다.
- Logcat에서 `FATAL EXCEPTION`을 찾습니다. 빨간 줄 가운데 `com.example.minilauncher`가 들어간 첫 줄을 누르면 그 코드로 이동합니다.
- 찾을 것: 값이 왜 null이었는지. 값을 넣는 쪽과 꺼내는 쪽을 같이 봅니다.

이런 줄이 보이면 제대로 찾은 것입니다.

```
E/AndroidRuntime: FATAL EXCEPTION: main
E/AndroidRuntime: Caused by: java.lang.NullPointerException
```

## 버그 2: 화면을 돌리면 빨간 경고가 찍힙니다

- 검색칸에 `leaked`를 넣고, 에뮬레이터를 가로, 세로로 몇 번 돌립니다.
- 이런 줄이 찍힙니다. `... has leaked IntentReceiver ... Are you missing a call to unregisterReceiver()?`
- 찾을 것: `has leaked IntentReceiver`가 무슨 뜻인지. 경고 아래에서 이 앱 코드 줄(등록한 곳)을 찾고, 2일차의 "등록과 해제는 짝"과 비교합니다.
- 참고: Activity가 사라질 때 시스템이 대신 정리해서 앱이 꺼지지는 않습니다. Application Context로 등록하면 앱이 살아 있는 동안 계속 남습니다.

## 버그 3: 앱은 멀쩡한데 경고가 찍힙니다

- 검색칸에 `StrictMode`를 넣고 앱을 다시 실행합니다.
- 이런 줄이 찍힙니다. `StrictMode policy violation; ~duration=166 ms: android.os.strictmode.DiskReadViolation`
- 찾을 것: 어떤 Violation이 찍히는지, 스택에서 이 앱 코드 줄은 어디인지, 메인 스레드에서 하면 안 되는 일이 무엇인지.

## 고치는 방향 (원인을 찾은 뒤에 봅니다)

| 버그 | 고치는 방향 | 고쳤는지 확인 |
|---|---|---|
| 1 | 값을 넣는 쪽과 꺼내는 쪽이 같은 키를 쓰게 맞춥니다. `!!` 대신 값이 없을 때의 처리를 넣습니다 | 아이콘을 길게 눌러 상세 화면이 열리고, `FATAL EXCEPTION`이 더 찍히지 않습니다 |
| 2 | 등록한 Receiver를 화면이 사라질 때 해제합니다 | 화면을 몇 번 돌려도 `leaked`가 더 찍히지 않습니다 |
| 3 | 메인 스레드에서 하던 읽기를 2-1에서 배운 `withContext(Dispatchers.IO)`로 옮깁니다 | 앱을 다시 실행해도 `DiskReadViolation`이 더 찍히지 않습니다 |

버그 1, 2의 정답은 [step07_launcher](../step07_launcher)의 같은 파일입니다. 버그 3은 step07_launcher에도 같은 읽기가 남아 있어서(StrictMode를 켜지 않아 경고만 안 보일 뿐입니다), `MainActivity.kt`의 부팅 시각 읽기를 아래처럼 바꿉니다.

```kotlin
// 바꾸기 전: 메인 스레드에서 SharedPreferences 를 읽음
val boot = remember { BootReceiver.lastBoot(context) }

// 바꾼 뒤: 읽기는 IO 스레드에서, 결과만 화면에
val boot by produceState<String?>(null) {
    value = withContext(Dispatchers.IO) { BootReceiver.lastBoot(context) }
}
```

import에 `androidx.compose.runtime.produceState`, `kotlinx.coroutines.Dispatchers`, `kotlinx.coroutines.withContext`를 더합니다. 다시 실행하면 `DiskReadViolation`이 더 찍히지 않습니다.

다음: [3-3 미니 런처 완성과 코드 리뷰](../step08_final)
