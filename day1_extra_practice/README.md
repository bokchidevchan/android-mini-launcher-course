# 1일차 8교시: 앱 만들어 보기 (오늘 점심 뭐 먹지?)

[전체 일정으로](../README.md)

7교시까지 마치고 시간이 남으면 만드는 앱입니다. 점심 메뉴를 뽑아 주는 화면 두 개짜리 앱입니다. 첫 화면(뽑기)은 XML로, 둘째 화면(기록 보기)은 Compose로 만들어 두 방식의 차이를 보고, 화면 사이는 Intent로 넘어갑니다. `day1_step03_lifecycle`와 같은 프로젝트에 이 앱의 뼈대와 정답을 더했습니다.

## 요구사항

1. 첫 화면(XML): 뽑기 버튼을 누르면 메뉴 목록(`lunchMenus`)에서 하나를 골라 크게 보여 주고, 몇 번째 뽑기인지 보여 줍니다
2. 첫 화면의 기록 보기 버튼을 누르면 둘째 화면이 열립니다. 지금까지 뽑은 목록을 Intent에 실어 넘깁니다
3. 둘째 화면(Compose): 뽑은 순서대로 목록을 보여 주고, 모두 몇 번인지 보여 줍니다
4. 둘째 화면의 돌아가기를 누르면 첫 화면으로 돌아오고, 뽑던 것이 그대로 있습니다
5. 첫 화면의 처음부터 버튼을 누르면 기록이 비워집니다
6. 도전: 같은 메뉴가 연달아 나오지 않게 합니다
7. 도전: 둘째 화면에 가장 많이 나온 메뉴를 보여 줍니다

## 만드는 방법

1. Android Studio에서 File > Open으로 이 폴더를 열고 Run 합니다.
2. 에뮬레이터의 앱 목록(홈 화면을 위로 밀어 올림)에서 아래 표의 연습 아이콘을 누릅니다.
3. 연습 파일을 고치고 다시 Run 합니다. 막히면 정답 파일을 봅니다.

| 아이콘 | 연습 파일 | 정답 파일 |
|---|---|---|
| 점심 뽑기 연습 (첫 화면) | `res/layout/activity_lunch_practice.xml`, `practice/LunchPickPracticeActivity.kt` | `res/layout/activity_lunch_answer.xml`, `practice/LunchPickAnswerActivity.kt` |
| 기록 보기 (둘째 화면) | `practice/LunchHistoryPracticeActivity.kt`의 `LunchHistoryScreen` | `practice/LunchHistoryAnswerActivity.kt` |
