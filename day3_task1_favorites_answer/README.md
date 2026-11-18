# 오전 과제: 즐겨찾기 서랍 만들기 (완성)

3일차 11:00~ | [전체 일정으로](../README.md) | 뼈대: [day3_task1_favorites_practice](../day3_task1_favorites_practice)

뼈대의 [1] [2] [3]을 채운 완성본입니다. File > Open으로 이 폴더를 열고 Run 합니다.

```kotlin
// practice/FavoritesViewModel.kt
init {
    viewModelScope.launch {                                       // [1] 처음 한 번, 코루틴으로
        apps = InstalledAppRepository(getApplication()).loadApps()
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
