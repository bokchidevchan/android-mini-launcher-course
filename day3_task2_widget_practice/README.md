# 오후 과제: 홈 화면 위젯 만들기 (뼈대)

3일차 14:00~ | [전체 일정으로](../README.md) | 완성: [day3_task2_widget_answer](../day3_task2_widget_answer)

미니 런처에 홈 화면 위젯을 더합니다. 위젯 화면, 설명서, Manifest 등록은 이미 들어 있고, 채우는 곳은 `widget/MiniWidget.kt`의 [1] [2] [3]입니다.

| 파일 | 내용 |
|---|---|
| `res/layout/widget_mini.xml` | 위젯 화면. 글자 세 줄 |
| `res/xml/mini_widget_info.xml` | 위젯 설명서. 크기, 처음 화면, 자동 갱신 주기 |
| `AndroidManifest.xml` | 위젯을 `<receiver>`로 등록 (맨 아래) |
| `widget/MiniWidget.kt` | 내용 채우기 [1], 누르면 할 일 [2], 앱 수 세기 [3] |

1. File > Open으로 이 폴더를 열고 Run 합니다.
2. 홈 앱이 Pixel Launcher인지 확인합니다. 미니 런처는 위젯 자리를 만들지 않습니다.
3. 홈 화면 빈 곳을 길게 누름 > Widgets > Browse > Mini Launcher > Add.
4. [1] [2] [3]을 채울 때마다 Run 하고 위젯을 다시 봅니다. Logcat 검색칸에 `tag:Widget`.
