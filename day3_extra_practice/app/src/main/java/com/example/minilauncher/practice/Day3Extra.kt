package com.example.minilauncher.practice

// 3일차 연습 문제: 런처에 쓰는 함수 10개, 로그를 읽는 함수 5개.
// TODO 가 적힌 줄을 고치고 Run 합니다. Logcat 검색창에 tag:Extra 를 넣으면 문제마다 O, X 가 찍힙니다.
// 정답은 같은 폴더의 Day3ExtraAnswers.kt 에 있습니다. 먼저 풀어 보고 엽니다.
object Day3Extra : Day3Problems {

    // ---------------- 런처에 쓰는 함수 ----------------

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

    // 문제 6. 런처 자신은 목록에서 뺍니다. 패키지 이름이 myPackage 인 앱만 빼고 돌려줍니다. (힌트: filter, !=)
    override fun withoutSelf(apps: List<ExtraApp>, myPackage: String): List<ExtraApp> {
        return apps          // TODO
    }

    // 문제 7. 이름에 검색어가 들어 있는 첫 번째 앱을 돌려줍니다(대소문자 무시). 없으면 null. (힌트: firstOrNull { })
    override fun firstMatch(apps: List<ExtraApp>, query: String): ExtraApp? {
        return apps.firstOrNull()   // TODO
    }

    // 문제 8. 한 화면에 perPage 개씩 보이도록 쪽으로 나눕니다. (힌트: chunked)
    override fun pages(apps: List<ExtraApp>, perPage: Int): List<List<ExtraApp>> {
        return emptyList()   // TODO
    }

    // 문제 9. 폴더 이름처럼 줄입니다. 두 개까지는 "Calendar, Camera", 더 많으면 "Calendar, Camera 외 4개"
    // (힌트: take(2), joinToString(", "), size)
    override fun summary(apps: List<ExtraApp>): String {
        return ""            // TODO
    }

    // 문제 10. 즐겨찾기. 없던 패키지면 넣고, 있던 패키지면 뺀 새 집합을 돌려줍니다. (힌트: in, +, -)
    override fun toggleFavorite(favorites: Set<String>, packageName: String): Set<String> {
        return favorites     // TODO
    }

    // ---------------- 로그를 읽는 함수 ----------------
    // 로그 한 줄의 모양: "D/MiniLauncher: MainActivity onCreate" (레벨/태그: 내용)

    // 문제 11. 태그만 꺼냅니다. 예: "D/MiniLauncher: ..." 는 MiniLauncher (힌트: substringAfter("/"), substringBefore(":"))
    override fun tagOf(line: String): String {
        return ""            // TODO
    }

    // 문제 12. 레벨이 E 인 줄만 고릅니다. (힌트: filter, startsWith("E/"))
    override fun errorsOnly(lines: List<String>): List<String> {
        return emptyList()   // TODO
    }

    // 문제 13. "Caused by:" 줄에서 예외 이름만 꺼냅니다. 예: NullPointerException. 그런 줄이 없으면 "없음"
    // (힌트: find, substringAfter("Caused by: "), substringBefore(":"), substringAfterLast("."))
    override fun exceptionName(lines: List<String>): String {
        return ""            // TODO
    }

    // 문제 14. "at com.example.minilauncher" 가 들어 있는 첫 줄에서 at 뒤쪽만 돌려줍니다. 없으면 "없음"
    // (힌트: find, substringAfter("at "))
    override fun firstAppLine(lines: List<String>): String {
        return ""            // TODO
    }

    // 문제 15. 레벨(줄의 첫 글자)마다 몇 줄인지 셉니다. (힌트: groupingBy { it.first() }.eachCount())
    override fun countByLevel(lines: List<String>): Map<Char, Int> {
        return emptyMap()    // TODO
    }
}
