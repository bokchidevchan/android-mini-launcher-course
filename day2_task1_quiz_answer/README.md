# 오전 과제: 복습 퀴즈 앱 만들기 (완성)

2일차 11:00~ | [전체 일정으로](../README.md) | 뼈대: [day2_task1_quiz_practice](../day2_task1_quiz_practice)

`day2_task1_quiz_practice`에서 직접 만들어 본 뒤에 여는 완성 프로젝트입니다. Android Studio에서 이 폴더를 열고 Run 하면 복습 퀴즈 화면이 바로 뜹니다. [4]의 고친 화면은 에뮬레이터의 앱 목록에서 멈춤 고치기 아이콘으로 엽니다. 뼈대와 앱 ID가 같아서, 설치하면 에뮬레이터의 앱이 이 앱으로 바뀝니다.

| 파일 | 내용 |
|---|---|
| `practice/QuizApp.kt` | 완성본. 받아 오는 함수, ViewModel, Activity |
| `practice/QuizScreen.kt` | 주소, 화면 상태, 화면 (뼈대와 같음) |
| `practice/FreezeFix.kt` | [4]의 완성본. 3초 걸리는 일을 워커 스레드로 옮긴 화면 |
