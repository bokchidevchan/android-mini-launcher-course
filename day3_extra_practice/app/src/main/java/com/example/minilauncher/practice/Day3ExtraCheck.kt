package com.example.minilauncher.practice

import android.util.Log

// 채점하는 파일입니다. 고치지 않습니다.

data class ExtraApp(val label: String, val packageName: String)

// 문제 5개의 모양. Day3Extra(내가 푸는 것)와 Day3Answers(정답)가 같은 모양을 따릅니다
interface Day3Problems {
    fun reversed(apps: List<ExtraApp>): List<ExtraApp>
    fun googleApps(apps: List<ExtraApp>): List<ExtraApp>
    fun search(apps: List<ExtraApp>, query: String): List<ExtraApp>
    fun countByFirstLetter(apps: List<ExtraApp>): Map<Char, Int>
    fun dock(apps: List<ExtraApp>, packages: List<String>): List<ExtraApp>
}

private val apps = listOf(
    ExtraApp("Calendar", "com.google.android.calendar"),
    ExtraApp("Camera", "com.android.camera2"),
    ExtraApp("Chrome", "com.android.chrome"),
    ExtraApp("Maps", "com.google.android.apps.maps"),
    ExtraApp("Phone", "com.google.android.dialer"),
    ExtraApp("Settings", "com.android.settings"),
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
)

// MainActivity 가 부릅니다. Logcat 에 tag:Extra
fun day3Extra() {
    checkDay3(Day3Extra).forEach { Log.d("Extra", it) }
}
