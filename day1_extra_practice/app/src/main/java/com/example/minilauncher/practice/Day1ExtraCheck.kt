package com.example.minilauncher.practice

import android.util.Log

// 채점하는 파일입니다. 고치지 않습니다.

data class Song(val title: String, val seconds: Int)

// 문제 5개의 모양. Day1Extra(내가 푸는 것)와 Day1Answers(정답)가 같은 모양을 따릅니다
interface Day1Problems {
    fun longNames(apps: List<String>): List<String>
    fun upper(apps: List<String>): List<String>
    fun lengthOf(apps: List<String>, name: String): Int
    fun longestTitle(songs: List<Song>): String
    fun batteryText(percent: Int): String
}

private val apps = listOf("Settings", "Camera", "Chrome", "Maps", "Calendar", "Phone")
private val songs = listOf(Song("봄", 185), Song("여름", 242), Song("가을", 201))

private fun judge(number: Int, actual: Any?, expected: Any?): String =
    if (actual == expected) "문제 $number: O" else "문제 $number: X  나온 값 $actual, 기대한 값 $expected"

// 문제마다 "문제 1: O" 같은 줄을 만들어 돌려줍니다
fun checkDay1(p: Day1Problems): List<String> = listOf(
    judge(1, p.longNames(apps), listOf("Settings", "Camera", "Chrome", "Calendar")),
    judge(2, p.upper(apps), listOf("SETTINGS", "CAMERA", "CHROME", "MAPS", "CALENDAR", "PHONE")),
    judge(3, listOf(p.lengthOf(apps, "Maps"), p.lengthOf(apps, "Netflix")), listOf(4, 0)),
    judge(4, listOf(p.longestTitle(songs), p.longestTitle(emptyList())), listOf("여름", "없음")),
    judge(5, listOf(15, 50, 100).map { p.batteryText(it) }, listOf("부족", "보통", "충분")),
)

// MainActivity 가 부릅니다. Logcat 에 tag:Extra
fun day1Extra() {
    checkDay1(Day1Extra).forEach { Log.d("Extra", it) }
}
