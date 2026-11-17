# 오전 과제: 즐겨찾기 서랍 만들기 (완성)

3일차 11:00~ | [전체 일정으로](../README.md) | 뼈대: [day3_task1_favorites_practice](../day3_task1_favorites_practice)

`day3_task1_favorites_practice`에서 직접 만들어 본 뒤에 여는 완성 프로젝트입니다. Android Studio에서 이 폴더를 열고 Run 하면 즐겨찾기 서랍 화면이 바로 뜹니다. 뼈대와 앱 ID가 같아서, 설치하면 에뮬레이터의 앱이 이 앱으로 바뀝니다.

`practice/FavoriteApps.kt`의 같은 자리에 [1] [2] [3] [4]가 채워져 있습니다.

## 단계별 정답

### [1] 화면이 뜰 때 설치된 앱 목록 읽기

```kotlin
LaunchedEffect(Unit) {
    apps = InstalledAppRepository(context.applicationContext).loadApps()
}
```

`loadApps()`는 오래 걸릴 수 있는 `suspend` 함수라 코루틴 안에서만 부를 수 있습니다. `LaunchedEffect`가 화면이 뜰 때 코루틴을 한 번 시작해 주고, 화면이 사라지면 같이 멈춥니다.

### [2] 별을 누르면 즐겨찾기에 넣거나 빼기

```kotlin
onToggle = { app ->
    favorites = if (app.packageName in favorites) favorites - app.packageName else favorites + app.packageName
},
```

목록 자체를 고치지 않고, 하나를 넣거나 뺀 새 목록을 `favorites`에 넣습니다. 상태에 새 값이 들어가야 화면이 다시 그려집니다.

### [3] 앱을 누르면 그 앱 열기

```kotlin
onOpen = { app ->
    launchApp(context, app)
},
```

`launchApp`은 앱의 패키지 이름과 화면 이름을 적은 명시적 Intent로 그 앱을 엽니다(2교시).

### [4] 화면을 돌려도 즐겨찾기가 남게 하기

```kotlin
var favorites by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
```

화면을 돌리면 Activity가 다시 만들어져서 `remember`의 값은 처음으로 돌아갑니다. `rememberSaveable`은 값을 저장해 뒀다가 다시 만들어진 화면에 돌려줍니다. `apps`는 다시 읽으면 되니 `remember`로 둡니다.

## 파일

| 파일 | 내용 |
|---|---|
| `practice/FavoriteApps.kt` | [1] [2] [3] [4]. 상태와 동작, 화면을 여는 Activity |
| `practice/FavoriteAppsLayout.kt` | 화면 (뼈대와 같음) |
| `data/`, `model/`, `AppLauncher.kt` | 설치된 앱 목록 읽기, 앱 정보, 앱 열기 (뼈대와 같음) |
