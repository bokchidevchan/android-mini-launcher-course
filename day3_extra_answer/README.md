# 3일차 8교시: 앱 룰렛 만들기 (완성)

3일차 8교시 | [전체 일정으로](../README.md) | 뼈대: [day3_extra_practice](../day3_extra_practice)

`day3_extra_practice`에서 직접 만들어 본 뒤에 여는 완성 프로젝트입니다. Android Studio에서 이 폴더를 열고 Run 하면 앱 룰렛 화면이 바로 뜹니다. 뼈대와 앱 ID가 같아서, 설치하면 에뮬레이터의 앱이 이 앱으로 바뀝니다.

`practice/AppRoulette.kt`의 같은 자리에 [1] [2] [3]이 채워져 있습니다.

## 단계별 정답

### [1] 화면이 뜰 때 설치된 앱 목록 읽기

```kotlin
LaunchedEffect(Unit) {
    apps = InstalledAppRepository(context.applicationContext).loadApps()
}
```

`loadApps()`는 `suspend` 함수라 코루틴 안에서 부릅니다. `LaunchedEffect`가 화면이 뜰 때 코루틴을 한 번 시작해 줍니다.

### [2] 돌리기

```kotlin
scope.launch {
    spinning = true
    repeat(15) {
        current = apps.random()
        delay(80)
    }
    spinning = false
}
```

`delay`로 쉬는 동안에도 메인 스레드는 막히지 않아서, 바뀐 이름이 그때그때 화면에 그려집니다.

### [3] 이 앱 열기

```kotlin
current?.let { launchApp(context, it) }
```

`current`가 비어 있으면 아무것도 하지 않고, 있으면 그 앱을 엽니다.

## 파일

| 파일 | 내용 |
|---|---|
| `practice/AppRoulette.kt` | [1] [2] [3]. 화면과 동작, 화면을 여는 Activity |
| `data/`, `model/`, `AppLauncher.kt` | 설치된 앱 목록 읽기, 앱 정보, 앱 열기 (뼈대와 같음) |
