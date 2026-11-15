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

## 폴더 구조

```
day1_step00_architecture/
├─ settings.gradle.kts          모듈 4개 목록
├─ gradle/libs.versions.toml    라이브러리 버전 목록
├─ core/
│  ├─ domain/                   규칙 (순수 Kotlin, 아는 모듈 없음)
│  │  ├─ model/AppInfo.kt              앱 하나의 모양
│  │  ├─ repository/AppRepository.kt   "앱 목록을 달라"는 약속(인터페이스)
│  │  └─ usecase/                      GetAppsUseCase(이름순), SearchAppsUseCase(검색)
│  └─ data/                     데이터 (core:domain 을 앎)
│     └─ InstalledAppRepository.kt     기기에 설치된 앱을 읽어 약속을 구현
├─ feature/
│  └─ home/                     화면 (core:domain 을 앎)
│     └─ HomeScreen.kt, HomeViewModel.kt, HomeUiState.kt
└─ app/                         조립 (전부 앎)
   └─ MainActivity.kt                  저장소를 만들어 ViewModel 에 넣어 줌
```

단위 테스트는 `core/domain`과 `feature/home`의 `src/test`에, E2E 테스트는 `app/src/androidTest`에 있습니다.

## 클린 아키텍처로 설계하는 순서

1. 규칙부터 정합니다. 화면도 저장소도 없이 순수 Kotlin으로 씁니다. (`GetAppsUseCase`, `SearchAppsUseCase`)
2. 바깥에서 받아 올 것은 인터페이스로만 적어 둡니다. (`AppRepository`)
3. 데이터 층이 그 인터페이스를 구현합니다. (`InstalledAppRepository`)
4. 화면은 유스케이스만 부르고 상태를 그립니다. (`HomeViewModel`, `HomeUiState`)
5. 맨 바깥에서 한 번 조립합니다. (`MainActivity`)

안쪽(규칙)은 바깥(화면, 데이터)을 모르고, 바깥이 안쪽을 압니다. 바뀌기 쉬운 것을 바깥에 두는 설계입니다.

검색창에 set을 치면 `HomeScreen` > `HomeViewModel.onQueryChange` > `SearchAppsUseCase` > 새 `HomeUiState` 순서로 흐르고 화면이 다시 그려집니다.

## 안다는 것과 모른다는 것

A가 B를 안다는 것은 A의 코드에 B의 이름이 나온다는 뜻입니다. import 줄을 보면 알 수 있습니다. 아는 쪽은 상대가 바뀌면 같이 고쳐야 하고, 모르는 쪽은 상대가 바뀌어도 그대로입니다.

화면 디자인과 저장 방법은 자주 바뀌고 규칙은 오래 갑니다. 그래서 규칙을 아무것도 모르게 둡니다. 모듈마다 있는 `build.gradle.kts`의 dependencies를 보면 `core/domain`에는 다른 모듈이 하나도 없고, `feature/home`과 `core/data`에는 `core:domain`만 있습니다. 화면 모듈에서 데이터 모듈의 클래스를 쓰려고 하면 빌드 오류가 납니다.

## 왜 인터페이스와 구현을 나누나

`AppRepository`(인터페이스)는 "무엇을 해 달라"는 약속이고, `InstalledAppRepository`(구현)는 "어떻게 하는지"입니다.

- 규칙이 구현을 직접 부르면 규칙이 안드로이드와 데이터 모듈을 알게 됩니다. 약속만 알면 몰라도 됩니다.
- 구현을 바꿔 끼울 수 있습니다. 테스트에서는 가짜 구현(`GetAppsUseCaseTest`의 `FakeAppRepository`)을 꽂고, 나중에는 서버나 데이터베이스에서 읽는 구현으로 바꿀 수 있습니다.
- 약속만 먼저 정하면 화면을 만드는 사람과 데이터를 만드는 사람이 동시에 일할 수 있습니다.

C++의 순수 가상 클래스와 그것을 상속한 구현 클래스와 같습니다. 구현 이름은 `InstalledAppRepository`처럼 어떻게 구현했는지를 적거나 끝에 Impl을 붙입니다.

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
