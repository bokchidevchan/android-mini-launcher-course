# 3일차 8교시: 앱 만들어 보기 (앱 룰렛)

[전체 일정으로](../README.md)

7교시까지 마치고 시간이 남으면 만드는 앱입니다. 설치된 앱 가운데 하나를 룰렛처럼 뽑아서 열어 주는 앱입니다. 3일 동안 배운 것(상태, 코루틴, 앱 목록, Intent)을 한 화면에 모읍니다. `day3_step08_final`와 같은 프로젝트에 이 앱의 뼈대를 더했습니다. 정답은 [day3_extra_answer](../day3_extra_answer) 폴더에 따로 있습니다.

## 요구사항

1. 화면이 뜨면 설치된 앱이 몇 개인지 보여 줍니다
2. 돌리기를 누르면 앱 이름이 빠르게 바뀌다가 하나에서 멈춥니다
3. 돌아가는 동안에는 버튼이 눌리지 않습니다
4. 이 앱 열기를 누르면 멈춘 앱이 열립니다
5. 도전: 멈출 때가 가까울수록 천천히 바뀌게 합니다
6. 도전: 방금 나온 앱은 다음 번에 나오지 않게 합니다

## 만드는 방법

1. Android Studio에서 File > Open으로 이 폴더를 열고 Run 합니다.
2. 에뮬레이터의 앱 목록(홈 화면을 위로 밀어 올림)에서 아래 표의 연습 아이콘을 누릅니다.
3. 연습 파일을 고치고 다시 Run 합니다. 막히면 아래 힌트를 열고, 그래도 안 되면 `day3_extra_answer` 폴더의 정답 파일을 봅니다.

| 아이콘 | 연습 파일 | 정답 파일 |
|---|---|---|
| 앱 룰렛 연습 | `practice/AppRoulette.kt`의 `AppRouletteScreen` | `practice/AppRouletteAnswer.kt` |

Mini Launcher를 기본 홈 앱으로 바꿨다면 아이콘이 안 보입니다. Settings > Apps > Default apps > Home app에서 원래 런처로 되돌린 뒤 찾습니다.

## 막히면 보는 힌트

먼저 힌트 없이 해 보고, 막힐 때만 엽니다.

<details>
<summary>힌트 열기</summary>

```kotlin
var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }     // 설치된 앱 목록
var current by remember { mutableStateOf<AppInfo?>(null) }              // 지금 보이는 앱
var spinning by remember { mutableStateOf(false) }                      // 돌아가는 중인지
val context = LocalContext.current
val scope = rememberCoroutineScope()

// 화면이 뜨면 한 번, 설치된 앱 목록 읽기
LaunchedEffect(Unit) { apps = InstalledAppRepository(context.applicationContext).loadApps() }

// 돌리기: 0.08초마다 아무 앱으로 15번 바꾸고 멈춤
scope.launch { spinning = true; repeat(15) { current = apps.random(); delay(80) }; spinning = false }

// 이 앱 열기: current 는 null 일 수 있어서 먼저 확인
val app = current
if (app != null) launchApp(context, app)

// 화면: Column 안에 Text 두 개와 Button 두 개.
// 돌아가는 동안, 그리고 목록이 비어 있을 때 못 누르게 하려면 Button(enabled = !spinning && apps.isNotEmpty())
```

</details>
