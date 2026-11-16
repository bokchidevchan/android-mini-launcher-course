package com.example.minilauncher.practice

import android.util.Log

// 채점하는 파일입니다. 고치지 않습니다.

data class Song(val title: String, val seconds: Int)

// 문제의 모양. Day1Extra(내가 푸는 것)와 Day1Answers(정답)가 같은 모양을 따릅니다
interface Day1Problems {
    fun longNames(apps: List<String>): List<String>
    fun upper(apps: List<String>): List<String>
    fun lengthOf(apps: List<String>, name: String): Int
    fun longestTitle(songs: List<Song>): String
    fun batteryText(percent: Int): String
    fun sumOfEven(numbers: List<Int>): Int
    fun initials(apps: List<String>): String
    fun countLong(apps: List<String>, min: Int): Int
    fun timeText(seconds: Int): String
    fun safeDivide(a: Int, b: Int): Int?
    fun totalSeconds(songs: List<Song>): Int
    fun titlesShortFirst(songs: List<Song>): List<String>
    fun rename(song: Song, title: String): Song
    fun groupByLength(apps: List<String>): Map<Int, List<String>>
    fun pressCounts(presses: List<String>): Map<String, Int>
    fun select(current: String?, clicked: String): String?
    fun visible(apps: List<String>, query: String, onlyLong: Boolean): List<String>
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
    judge(6, p.sumOfEven(listOf(1, 2, 3, 4, 10)), 16),
    judge(7, p.initials(apps), "SCCMCP"),
    judge(8, listOf(p.countLong(apps, 6), p.countLong(apps, 8)), listOf(4, 2)),
    judge(9, listOf(p.timeText(185), p.timeText(60)), listOf("3분 5초", "1분 0초")),
    judge(10, listOf(p.safeDivide(10, 2), p.safeDivide(1, 0)), listOf(5, null)),
    judge(11, p.totalSeconds(songs), 628),
    judge(12, p.titlesShortFirst(songs), listOf("봄", "가을", "여름")),
    judge(13, p.rename(songs[0], "봄 2"), Song("봄 2", 185)),
    judge(
        14, p.groupByLength(apps),
        mapOf(8 to listOf("Settings", "Calendar"), 6 to listOf("Camera", "Chrome"), 4 to listOf("Maps"), 5 to listOf("Phone")),
    ),
    judge(15, p.pressCounts(listOf("Chrome", "Maps", "Chrome")), mapOf("Chrome" to 2, "Maps" to 1)),
    judge(16, listOf(p.select(null, "a"), p.select("a", "a"), p.select("a", "b")), listOf("a", null, "b")),
    judge(
        17, listOf(p.visible(apps, "c", true), p.visible(apps, "", false).size),
        listOf(listOf("Camera", "Chrome", "Calendar"), 6),
    ),
)

// MainActivity 가 부릅니다. Logcat 에 tag:Extra
fun day1Extra() {
    val lines = checkDay1(Day1Extra)
    lines.forEach { Log.d("Extra", it) }
    Log.d("Extra", "맞은 문제 ${lines.count { it.endsWith(": O") }} / ${lines.size}")
}
