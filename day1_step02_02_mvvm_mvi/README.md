# 비교 코드: MVVM과 MVI

1일차 7교시, ViewModel을 배운 뒤 | [전체 일정으로](../README.md)

`day1_step02_02_mvvm_mvi` 폴더를 열고 Run 한 다음, 에뮬레이터의 앱 목록에서 "MVVM과 MVI 비교" 아이콘을 누릅니다. Logcat 검색창에는 `tag:Arch`를 넣습니다.

같은 "좋아요" 화면을 위쪽은 MVVM으로, 아래쪽은 MVI로 만들었습니다. 둘 다 화면은 그리기만 하고 값을 바꾸는 규칙은 ViewModel에 둡니다. 다른 것은 화면이 ViewModel에 말하는 방법과 상태를 담는 방법입니다.

| | MVVM | MVI |
|---|---|---|
| 화면이 ViewModel에 말하는 법 | 할 일마다 함수가 따로 (`plus()`, `reset()`) | 문이 하나 (`onIntent(주문서)`) |
| 상태 | 값마다 따로 둘 수 있음 | 한 덩어리 (`State`) |
| 좋은 점 | 적을 것이 적음 | 일어난 일이 한곳에 순서대로 남음 |
| 아쉬운 점 | 버튼이 많아지면 값이 왜 바뀌었는지 찾기 어려움 | 적을 것이 많음 |

| 파일 | 내용 |
|---|---|
| `practice/MvvmVsMvi.kt` | 보여 주기. 두 방식의 ViewModel과 화면, 왜 이렇게 나누는지에 대한 설명 |
| `practice/MvvmVsMvi_My.kt` | 같이 해 보기. 양쪽에 -1 버튼을 넣어 봄 ("MVVM과 MVI 비교 직접" 아이콘, `tag:MyArch`) |

이 폴더는 `day1_step02_compose`에 위 두 파일만 더한 것입니다.
