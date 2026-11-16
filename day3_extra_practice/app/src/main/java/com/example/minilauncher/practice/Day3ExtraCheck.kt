package com.example.minilauncher.practice

import android.util.Log

// 채점하는 파일입니다. 고치지 않습니다.

data class ExtraApp(val label: String, val packageName: String)

// 문제의 모양. Day3Extra(내가 푸는 것)와 Day3Answers(정답)가 같은 모양을 따릅니다
interface Day3Problems {
    fun reversed(apps: List<ExtraApp>): List<ExtraApp>
    fun googleApps(apps: List<ExtraApp>): List<ExtraApp>
    fun search(apps: List<ExtraApp>, query: String): List<ExtraApp>
    fun countByFirstLetter(apps: List<ExtraApp>): Map<Char, Int>
    fun dock(apps: List<ExtraApp>, packages: List<String>): List<ExtraApp>
    fun withoutSelf(apps: List<ExtraApp>, myPackage: String): List<ExtraApp>
    fun firstMatch(apps: List<ExtraApp>, query: String): ExtraApp?
    fun pages(apps: List<ExtraApp>, perPage: Int): List<List<ExtraApp>>
    fun summary(apps: List<ExtraApp>): String
    fun toggleFavorite(favorites: Set<String>, packageName: String): Set<String>
    fun tagOf(line: String): String
    fun errorsOnly(lines: List<String>): List<String>
    fun exceptionName(lines: List<String>): String
    fun firstAppLine(lines: List<String>): String
    fun countByLevel(lines: List<String>): Map<Char, Int>
}

private val apps = listOf(
    ExtraApp("Calendar", "com.google.android.calendar"),
    ExtraApp("Camera", "com.android.camera2"),
    ExtraApp("Chrome", "com.android.chrome"),
    ExtraApp("Maps", "com.google.android.apps.maps"),
    ExtraApp("Phone", "com.google.android.dialer"),
    ExtraApp("Settings", "com.android.settings"),
)

// 앱이 꺼졌을 때 Logcat 에 찍히는 줄을 줄여 놓은 것
private val logs = listOf(
    "D/MiniLauncher: MainActivity onCreate",
    "I/ActivityManager: Displayed com.example.minilauncher/.MainActivity",
    "E/AndroidRuntime: FATAL EXCEPTION: main",
    "E/AndroidRuntime: Caused by: java.lang.NullPointerException: label is null",
    "E/AndroidRuntime:     at android.app.Activity.performCreate(Activity.java:8000)",
    "E/AndroidRuntime:     at com.example.minilauncher.DetailActivity.onCreate(DetailActivity.kt:21)",
    "W/StrictMode: StrictMode policy violation",
)

private fun judge(number: Int, actual: Any?, expected: Any?): String =
    if (actual == expected) "문제 $number: O" else "문제 $number: X  나온 값 $actual, 기대한 값 $expected"

private fun names(list: List<ExtraApp>) = list.map { it.label }

// 문제마다 "문제 1: O" 같은 줄을 만들어 돌려줍니다
fun checkDay3(p: Day3Problems): List<String> = listOf(
    judge(1, names(p.reversed(apps)), listOf("Settings", "Phone", "Maps", "Chrome", "Camera", "Calendar")),
    judge(2, names(p.googleApps(apps)), listOf("Calendar", "Maps", "Phone")),
    judge(
        3,
        listOf(names(p.search(apps, "CA")), names(p.search(apps, "dialer")), names(p.search(apps, " ")).size),
        listOf(listOf("Calendar", "Camera"), listOf("Phone"), 6),
    ),
    judge(4, p.countByFirstLetter(apps), mapOf('C' to 3, 'M' to 1, 'P' to 1, 'S' to 1)),
    judge(
        5,
        names(p.dock(apps, listOf("com.google.android.dialer", "com.example.none", "com.android.chrome"))),
        listOf("Phone", "Chrome"),
    ),
    judge(6, names(p.withoutSelf(apps, "com.android.chrome")), listOf("Calendar", "Camera", "Maps", "Phone", "Settings")),
    judge(7, listOf(p.firstMatch(apps, "ma")?.label, p.firstMatch(apps, "zzz")?.label), listOf("Maps", null)),
    judge(8, p.pages(apps, 4).map { names(it) }, listOf(listOf("Calendar", "Camera", "Chrome", "Maps"), listOf("Phone", "Settings"))),
    judge(
        9,
        listOf(p.summary(apps), p.summary(apps.take(2)), p.summary(apps.take(1))),
        listOf("Calendar, Camera 외 4개", "Calendar, Camera", "Calendar"),
    ),
    judge(
        10,
        listOf(p.toggleFavorite(setOf("a"), "b"), p.toggleFavorite(setOf("a", "b"), "a")),
        listOf(setOf("a", "b"), setOf("b")),
    ),
    judge(11, listOf(p.tagOf(logs[0]), p.tagOf(logs[6])), listOf("MiniLauncher", "StrictMode")),
    judge(12, p.errorsOnly(logs), logs.subList(2, 6)),
    judge(13, listOf(p.exceptionName(logs), p.exceptionName(logs.take(2))), listOf("NullPointerException", "없음")),
    judge(
        14,
        listOf(p.firstAppLine(logs), p.firstAppLine(logs.take(2))),
        listOf("com.example.minilauncher.DetailActivity.onCreate(DetailActivity.kt:21)", "없음"),
    ),
    judge(15, p.countByLevel(logs), mapOf('D' to 1, 'I' to 1, 'E' to 4, 'W' to 1)),
)

// MainActivity 가 부릅니다. Logcat 에 tag:Extra
fun day3Extra() {
    val lines = checkDay3(Day3Extra)
    lines.forEach { Log.d("Extra", it) }
    Log.d("Extra", "맞은 문제 ${lines.count { it.endsWith(": O") }} / ${lines.size}")
}
