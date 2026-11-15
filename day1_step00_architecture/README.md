# 1일차 처음: 실무 앱 구조 (층과 모듈로 나눈 작은 런처)

[전체 일정으로](../README.md) | 다음: [1-1 Kotlin 핵심](../day1_step01_project)

앱 목록을 보여 주고 검색하는 화면 하나짜리 앱입니다. 기능은 작지만 실무 앱처럼 층과 모듈로 나눴습니다. Android Studio에서 이 폴더를 열어 Run 할 수 있습니다.

## 모듈 4개

```
app  ->  feature:home  ->  core:domain
app  ->  core:data     ->  core:domain
```

화살표는 "안다"는 뜻입니다.

| 모듈 | 층 | 하는 일 |
|---|---|---|
| `core:domain` | 규칙 | 앱 목록은 이름순으로 보여 주고 검색은 대소문자를 가리지 않는다는 규칙을 담습니다. 안드로이드를 모르는 순수 Kotlin 모듈입니다 |
| `core:data` | 데이터 | 기기에 설치된 앱을 PackageManager에서 읽습니다. `core:domain`의 `AppRepository` 인터페이스를 구현합니다 |
| `feature:home` | 화면 | Compose 화면과 ViewModel. `core:domain`만 압니다 |
| `app` | 조립 | 어떤 저장소를 꽂을지 정하는 한 곳입니다 |

## 왜 이렇게 나누나

- 화면과 데이터는 규칙을 알고, 규칙은 아무것도 모릅니다. 화면이나 저장소를 바꿔도 규칙은 그대로입니다.
- `feature:home`의 의존성에는 `core:data`가 없습니다. 화면에서 데이터 구현을 직접 부르려고 하면 빌드가 되지 않습니다. 층의 규칙을 빌드가 지켜 줍니다.
- 규칙이 안드로이드를 모르기 때문에 에뮬레이터 없이 내 컴퓨터에서 몇 초 만에 테스트할 수 있습니다.

## 테스트

| 종류 | 파일 | 실행 |
|---|---|---|
| 단위 테스트 5개 | `core/domain`의 `SearchAppsUseCaseTest`, `GetAppsUseCaseTest` | 테스트 옆 초록 화살표. 에뮬레이터가 필요 없습니다 |
| 단위 테스트 3개 | `feature/home`의 `HomeViewModelTest` | 가짜 저장소를 꽂아 ViewModel만 확인합니다 |
| E2E 테스트 1개 | `app/src/androidTest`의 `HomeE2ETest` | 에뮬레이터를 켜고 초록 화살표. 에뮬레이터 언어가 영어이고 Calendar, Settings 앱이 있다고 가정합니다. 앱을 띄워 검색창에 set을 넣고 Settings만 남는지 봅니다 |

## 미니 런처와 맞대어 보기

| 이 예제 | 미니 런처 |
|---|---|
| `core:domain`의 `SearchAppsUseCase` | `model/AppFilter.kt` |
| `core:domain`의 `AppRepository` | `data/AppRepository.kt` |
| `core:data`의 `InstalledAppRepository` | `data/InstalledAppRepository.kt` |
| `feature:home`의 `HomeViewModel` | `LauncherViewModel.kt` |

더 큰 예는 구글이 공개한 샘플 앱 Now in Android(github.com/android/nowinandroid)입니다.
