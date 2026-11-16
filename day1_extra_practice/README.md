# 1일차 8교시: 앱 만들어 보기 (오늘 점심 뭐 먹지?)

[전체 일정으로](../README.md)

7교시까지 마치고 시간이 남으면 만드는 앱입니다. 버튼을 누르면 점심 메뉴를 아무거나 하나 뽑아 주는 앱을 XML로 한 번, Compose로 한 번 만들어 두 방식의 차이를 봅니다. `day1_step03_lifecycle`와 같은 프로젝트에 이 앱의 뼈대와 정답을 더했습니다.

## 요구사항

1. 뽑기 버튼을 누르면 메뉴 목록(`lunchMenus`)에서 하나를 골라 크게 보여 줍니다
2. 몇 번째 뽑기인지 보여 줍니다
3. 처음부터 버튼을 누르면 처음 화면으로 돌아갑니다
4. 도전: 같은 메뉴가 연달아 나오지 않게 합니다
5. 도전: 메뉴 목록을 내 취향으로 바꿉니다

## 만드는 방법

1. Android Studio에서 File > Open으로 이 폴더를 열고 Run 합니다.
2. 에뮬레이터의 앱 목록(홈 화면을 위로 밀어 올림)에서 아래 표의 연습 아이콘을 누릅니다.
3. 연습 파일을 고치고 다시 Run 합니다. 막히면 정답 파일을 봅니다.

| 아이콘 | 연습 파일 | 정답 파일 |
|---|---|---|
| 점심 뽑기 XML 연습 | `res/layout/activity_lunch_practice.xml`, `practice/XmlLunchPracticeActivity.kt` | `res/layout/activity_lunch_answer.xml`, `practice/XmlLunchAnswerActivity.kt` |
| 점심 뽑기 Compose 연습 | `practice/ComposeLunch.kt`의 `LunchScreen` | 같은 파일의 `LunchScreenAnswer` |
