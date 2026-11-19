# 오전 과제: 즐겨찾기 서랍 만들기 (완성)

3일차 11:00~ | [전체 일정으로](../README.md) | 뼈대: [day3_task1_favorites_practice](../day3_task1_favorites_practice)

뼈대의 [1] [2] [3]을 채운 완성본입니다. File > Open으로 이 폴더를 열고 Run 합니다.

```kotlin
// practice/FavoritesViewModel.kt
init {
    viewModelScope.launch {                                       // [1] 처음 한 번, 코루틴으로
        apps = loadInstalledApps(getApplication())
    }
}

fun toggle(app: AppInfo) {                                        // [2] 있으면 빼고, 없으면 넣기
    favorites = if (app.packageName in favorites) favorites - app.packageName else favorites + app.packageName
}
```

```kotlin
// practice/FavoriteApps.kt
@Composable
fun FavoriteAppsScreen(vm: FavoritesViewModel = viewModel()) {
    val context = LocalContext.current
    FavoriteAppsLayout(
        apps = vm.apps,
        favorites = vm.favorites,
        onToggle = { app -> vm.toggle(app) },
        onOpen = { app -> launchApp(context, app) },               // [3] 앱 열기
    )
}
```

### [1] launch는 스레드를 새로 만드나

만들지 않습니다. `viewModelScope.launch`는 화면 스레드(main)에서 바로 시작하고(기본 디스패처가 `Dispatchers.Main.immediate`), 오래 걸리는 앱 목록 읽기만 `loadInstalledApps` 안의 `withContext(Dispatchers.IO)`가 미리 모아 둔 IO 일꾼 스레드에 맡깁니다. 끝나면 main으로 돌아와 `apps`에 넣습니다. 스레드 이름을 찍어 보면 이렇게 나옵니다.

```
D/Thread: 1. init: main
D/Thread: 2. launch 안: main
D/Thread: 5. launch 다음 줄: main
D/Thread: 3. withContext(IO) 안: DefaultDispatcher-worker-1
D/Thread: 4. 목록 받은 뒤: main, 20개
```

번호는 코드에 넣은 순서이고, 위는 실제로 찍힌 순서입니다. 이미 main이라 `launch` 안(2)이 바로 실행되고, `withContext`에서 멈출 때 `launch` 다음 줄(5)이 찍힙니다. 일꾼 스레드 번호는 실행할 때마다 달라질 수 있습니다.

### [2] 무엇이 "있으면" 빼나

`favorites`는 별을 누른 앱의 패키지 이름을 모은 목록입니다. 누른 앱이 이 목록에 들어 있으면 빼고, 없으면 넣습니다. 전등 스위치와 같습니다.

```
처음           []
Chrome 별      없음 -> 넣기 -> [chrome]
YouTube 별     없음 -> 넣기 -> [chrome, youtube]
Chrome 별 또   있음 -> 빼기 -> [youtube]
```

별 몇 개를 누르고 에뮬레이터를 가로로 돌려 봅니다. 즐겨찾기가 그대로 남습니다.
