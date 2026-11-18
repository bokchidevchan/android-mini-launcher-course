# 오후 과제: 홈 화면 위젯 만들기 (뼈대)

3일차 14:00~ | [전체 일정으로](../README.md) | 완성: [day3_task2_widget_answer](../day3_task2_widget_answer)

홈 화면에 놓는 위젯을 만듭니다. 이 폴더는 위젯만 들어 있는 작은 앱(이름 "위젯 연습")입니다. 위젯 화면, 설명서, Manifest 등록, 안내 화면은 만들어져 있고, 채우는 곳은 `practice/MiniWidget.kt`의 [1] [2] [3]입니다.

| 파일 | 내용 |
|---|---|
| `res/layout/widget_mini.xml` | 위젯 화면. 글자 세 줄 |
| `res/xml/mini_widget_info.xml` | 위젯 설명서. 크기, 처음 화면, 자동 갱신 주기 |
| `AndroidManifest.xml` | 위젯을 `<receiver>`로 등록 |
| `practice/MiniWidget.kt` | 내용 채우기 [1], 누르면 할 일 [2], 앱 수 세기 [3] |
| `practice/WidgetGuide.kt` | Run 하면 뜨는 안내 화면. 고치지 않음 |

1. File > Open으로 이 폴더를 열고 Run 합니다. "위젯 연습" 안내 화면이 뜹니다.
2. 홈 앱이 Pixel Launcher인지 확인합니다. 위젯 자리는 홈 앱이 만듭니다. 아니면 Settings > Apps > Default apps > Home app에서 Pixel Launcher로 되돌립니다.
3. 홈 화면 빈 곳을 길게 누름 > Widgets > 위젯 연습 > Add.
4. [1] [2] [3]을 채울 때마다 Run 하고 위젯을 다시 놓아 봅니다. Logcat 검색칸에 `tag:Widget`.

## [1] 글자 넣기

`views.setTextViewText(R.id.widget_count, "설치 앱 ${count}개")`, `widget_refresh`에는 `"새로고침 ($time)"`.

## [2] 누르면 할 일

`views.setOnClickPendingIntent(R.id.widget_refresh, refreshIntent(context))`, 제목(`widget_title`)에는 `openAppIntent(context)`.

## [3] 앱 수 세기

1교시 1단계처럼 `Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)`로 `queryIntentActivities`를 부르고 `.size`를 돌려줍니다.

회색 함수(`refreshIntent`, `openAppIntent`)는 빈칸을 채우면 쓰입니다. 지우지 않습니다.

## 먼저 끝났으면

배경색 바꾸기(`widget_mini.xml`의 `android:background`), 가로 4칸으로 키우기(`targetCellWidth="4"`), 시각 대신 날짜 보이기(`"yyyy-MM-dd"`). 크기를 바꾼 뒤에는 놓여 있던 위젯을 지우고 다시 놓습니다.
