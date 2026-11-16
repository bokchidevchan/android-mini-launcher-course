package com.example.minilauncher.practice

// 3일차 시간이 남으면: 런처에 쓸 만한 함수 5개.
// TODO 가 적힌 줄을 고치고 Run 합니다. Logcat 검색창에 tag:Extra 를 넣으면 문제마다 O, X 가 찍힙니다.
// 정답은 같은 폴더의 Day3ExtraAnswers.kt 에 있습니다. 먼저 풀어 보고 엽니다.
object Day3Extra : Day3Problems {

    // 문제 1. 이름의 역순(Z 에서 A)으로 정렬합니다. (힌트: sortedByDescending)
    override fun reversed(apps: List<ExtraApp>): List<ExtraApp> {
        return apps          // TODO
    }

    // 문제 2. 패키지 이름이 com.google 로 시작하는 앱만 고릅니다. (힌트: filter, startsWith)
    override fun googleApps(apps: List<ExtraApp>): List<ExtraApp> {
        return apps          // TODO
    }

    // 문제 3. 이름이나 패키지 이름에 검색어가 들어 있는 앱만 고릅니다. 대소문자는 가리지 않고, 검색어가 비면 전부.
    // (힌트: contains(query, ignoreCase = true), ||)
    override fun search(apps: List<ExtraApp>, query: String): List<ExtraApp> {
        return apps          // TODO
    }

    // 문제 4. 이름의 첫 글자마다 앱이 몇 개인지 셉니다. 예: C 가 3개. (힌트: groupBy, mapValues)
    override fun countByFirstLetter(apps: List<ExtraApp>): Map<Char, Int> {
        return emptyMap()    // TODO
    }

    // 문제 5. 도크에 놓을 앱을 고릅니다. packages 에 적힌 순서대로 찾고, 설치되지 않은 것은 건너뜁니다.
    // (힌트: mapNotNull, find)
    override fun dock(apps: List<ExtraApp>, packages: List<String>): List<ExtraApp> {
        return emptyList()   // TODO
    }
}
