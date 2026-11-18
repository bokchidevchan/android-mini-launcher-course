# 오전 과제: 즐겨찾기 서랍 만들기 (뼈대)

3일차 11:00~ | [전체 일정으로](../README.md) | 완성: [day3_task1_favorites_answer](../day3_task1_favorites_answer)

설치된 앱 목록에서 별을 누르면 위쪽 줄에 담기고, 앱을 누르면 그 앱이 열리는 앱입니다. 화면은 만들어져 있습니다.

File > Open으로 이 폴더를 열고 Run 하면 즐겨찾기 서랍 화면이 바로 뜹니다. 아래 [1]부터 차례로 채우고, 채울 때마다 Run 합니다. 파일 안에 `[1] 여기를 채웁니다`처럼 자리와 힌트가 적혀 있습니다.

## 누가 무엇을 하나

| 파일 | 하는 일 |
|---|---|
| `practice/FavoritesViewModel.kt` | 화면 뒤의 사무실. 설치된 앱 목록(`apps`)과 즐겨찾기(`favorites`)를 들고 있음. 화면을 돌려도 그대로 남음 |
| `practice/FavoriteApps.kt` | 화면. ViewModel의 값을 그리고, 누른 일은 ViewModel에 알림 |
| `practice/FavoriteAppsLayout.kt` | 목록과 별 모양을 그림. 고치지 않음 |

```
ViewModel 이 만들어짐 → [1] 설치된 앱 목록을 읽어 apps 에 넣음 → 화면에 목록이 보임
별을 누름 → [2] favorites 에 넣거나 뺌 → 위쪽 줄이 바뀜
앱을 누름 → [3] 그 앱을 엶
```

## [1] 처음 한 번 설치된 앱 목록 읽기 (`FavoritesViewModel.kt`의 `init`)

```kotlin
init {
    viewModelScope.launch {                                    // 오래 걸릴 수 있어 코루틴으로
        apps = InstalledAppRepository(getApplication()).______()
    }
}
```

힌트: 1교시에 본 `InstalledAppRepository`의 `loadApps()`. 확인: 화면에 "설치된 앱 ○○개"와 목록이 보입니다.

## [2] 별을 누르면 넣거나 빼기 (`FavoritesViewModel.kt`의 `toggle`)

힌트: `favorites = if (app.packageName in favorites) favorites - app.packageName else favorites + app.packageName`. 확인: ☆이 ★로 바뀌고 위쪽 줄에 담깁니다.

## [3] 앱을 누르면 열기 (`FavoriteApps.kt`의 `onOpen`)

힌트: 2교시의 `launchApp(context, app)` 한 줄. 확인: 앱을 누르면 그 앱이 열립니다.

## 다 했으면

별 몇 개를 누르고 에뮬레이터를 가로로 돌려 봅니다. 값이 ViewModel에 있어서 즐겨찾기가 그대로 남습니다.

막히면 [day3_task1_favorites_answer](../day3_task1_favorites_answer)를 엽니다.
