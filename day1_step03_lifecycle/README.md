# 1-4 Activity 생명주기와 화면 이동

1일차 15:30~17:00 | [전체 일정으로](../README.md)

`day1_step03_lifecycle` 폴더를 열고 시작합니다.

앞 모듈에서 화면을 돌리면 선택이 사라졌습니다. 왜 그런지 계산기 화면 하나로 확인합니다. 이 폴더의 앱은 런처 대신 계산기가 뜹니다. 화면은 XML 레이아웃(`res/layout/activity_main.xml`)으로 만들었고, ViewModel을 쓰는 계산기(`MainActivity`)와 안 쓰는 계산기(`MainActivity2`)를 나란히 두고, 화면을 돌렸을 때 어느 쪽 값이 남는지 봅니다.

## 따라 하기

### 1. 생명주기 로그 보기

1. 앱을 Run으로 실행합니다.
2. Logcat 검색칸에 `tag:Lifecycle`을 넣습니다.

3. 홈 버튼, 다시 열기, 가로로 돌리기를 해 보며 동작마다 어떤 콜백이 찍히는지 봅니다. 가로로 돌렸다가 다시 세로로 돌리면 이렇게 찍힙니다.

```
D/Lifecycle: onCreate (복원 데이터 없음)
D/Lifecycle: CalculatorViewModel 생성
D/Lifecycle: onStart
D/Lifecycle: onResume
D/Lifecycle: onPause
D/Lifecycle: onStop
D/Lifecycle: onDestroy (회전 때문이면 true: true)
D/Lifecycle: onCreate (복원 데이터 있음)
D/Lifecycle: onStart
D/Lifecycle: onResume
D/Lifecycle: onPause
D/Lifecycle: onStop
D/Lifecycle: onDestroy (회전 때문이면 true: true)
D/Lifecycle: onCreate (복원 데이터 있음)
```

`onDestroy` 줄 끝이 `true`인 것은 화면 회전 때문에 없어졌다는 뜻입니다. 그 뒤에 `onCreate`가 다시 찍힙니다. 화면을 돌리면 Activity가 새로 만들어집니다. 1-3(step02)에서 선택이 사라진 이유입니다.

### 2. 화면을 돌려 두 계산기 비교하기

계산기가 두 개 있습니다. 화면 모양(`activity_main.xml`)은 같고, 값을 어디에 두느냐만 다릅니다.

| 화면 | 여는 방법 | 값을 두는 곳 | 화면을 돌리면 |
|---|---|---|---|
| `MainActivity` | Run 하면 뜨는 화면 | `CalculatorViewModel` | 그대로 |
| `MainActivity2` | 앱 목록의 "계산기 ViewModel 없음" 아이콘 | Activity의 변수 `text` | 0으로 돌아감 |

1. `MainActivity2`를 열고 `12+3`을 누른 뒤 화면을 가로로 돌립니다. 표시창이 `0`으로 돌아갑니다.
2. `MainActivity`에서 똑같이 해 봅니다. `12+3`이 남아 있습니다.

`MainActivity2.kt`의 `text`는 Activity의 변수입니다. 화면을 돌리면 Activity가 새로 만들어지니 변수도 처음 값 `"0"`으로 다시 시작합니다. `MainActivity.kt`의 `vm`은 `by viewModels()`로 받은 `CalculatorViewModel`입니다. 새로 만들어진 Activity가 같은 ViewModel을 다시 받기 때문에 값이 남습니다. 로그에 `CalculatorViewModel 생성`이 처음 한 번만 찍히는 것도 봅니다.

뒤로 가기로 닫았다가 다시 열면 두 계산기 모두 `0`입니다. `MainActivity`는 이때 로그에 `CalculatorViewModel onCleared`가 찍힙니다. 사용자가 끝낸 화면이라 ViewModel도 같이 없어집니다.

## 생명주기는 언제 중요한가

앱은 켜져 있는 동안에도 계속 화면 뒤로 갔다가 돌아옵니다. 앱을 실행해 두고 아래 상황을 차례로 해 보며 Logcat(`tag:Lifecycle`)을 확인합니다.

| 상황 | 찍히는 로그 | 그래서 할 일 |
|---|---|---|
| 홈 버튼으로 나감, 화면을 끔, 다른 앱이 위로 옴 | onPause, onStop | 안 보이는 동안은 화면 갱신과 센서를 멈춥니다. 안 멈추면 배터리가 닳습니다 |
| 다시 돌아옴 | onStart, onResume | 그동안 바뀐 값(시각, 배터리)을 다시 받아 옵니다 |
| 화면을 돌림 | onPause, onStop, onDestroy, onCreate(복원 데이터 있음), onStart, onResume | 화면이 새로 만들어집니다. 남길 값은 ViewModel에 둡니다 |
| 뒤로 가기로 닫음 | onPause, onStop, onDestroy. 다시 열면 onCreate(복원 데이터 없음) | 사용자가 끝낸 것입니다. 처음 상태로 다시 시작합니다 |
| 뒤에 있는 동안 시스템이 메모리를 비우려고 끝냄 | 로그 없음. 다시 열면 onCreate(복원 데이터 있음)과 CalculatorViewModel 생성 | ViewModel의 값도 사라집니다. 작은 값은 onSaveInstanceState로 넘겨 두었다 되살리고, 꼭 남길 것은 파일에 저장합니다 |

전화를 받거나 알림을 눌러 다른 화면에 다녀오는 것도 같은 흐름입니다. 쓰던 글자와 보던 위치는 그대로여야 합니다.

## 막히면

- Logcat에 아무것도 안 보임: 위쪽 기기 선택이 지금 쓰는 에뮬레이터인지, 검색칸 글자가 맞는지 봅니다.

## 더 해 보기

`onSaveInstanceState`에서 `MainActivity2`의 `text`를 `outState.putString`으로 담고, `onCreate`의 `savedInstanceState`에서 꺼내 봅니다. ViewModel 없이도 화면을 돌렸을 때 값이 남는지 확인합니다.

다음: [2-1 메인 스레드와 Coroutine, Flow](../day2_step04_coroutine)
