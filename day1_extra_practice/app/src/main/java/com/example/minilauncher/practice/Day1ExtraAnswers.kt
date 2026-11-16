package com.example.minilauncher.practice

// 1일차 연습 문제: 정답. 먼저 Day1Extra.kt 를 풀어 보고 엽니다.
object Day1Answers : Day1Problems {

    override fun longNames(apps: List<String>): List<String> = apps.filter { it.length >= 6 }

    override fun upper(apps: List<String>): List<String> = apps.map { it.uppercase() }

    override fun lengthOf(apps: List<String>, name: String): Int = apps.find { it == name }?.length ?: 0

    override fun longestTitle(songs: List<Song>): String = songs.maxByOrNull { it.seconds }?.title ?: "없음"

    override fun batteryText(percent: Int): String = when {
        percent <= 20 -> "부족"
        percent <= 80 -> "보통"
        else -> "충분"
    }

    override fun sumOfEven(numbers: List<Int>): Int = numbers.filter { it % 2 == 0 }.sum()

    override fun initials(apps: List<String>): String = apps.map { it.first() }.joinToString("")

    override fun countLong(apps: List<String>, min: Int): Int = apps.count { it.length >= min }

    override fun timeText(seconds: Int): String = "${seconds / 60}분 ${seconds % 60}초"

    override fun safeDivide(a: Int, b: Int): Int? = if (b == 0) null else a / b

    override fun totalSeconds(songs: List<Song>): Int = songs.sumOf { it.seconds }

    override fun titlesShortFirst(songs: List<Song>): List<String> = songs.sortedBy { it.seconds }.map { it.title }

    override fun rename(song: Song, title: String): Song = song.copy(title = title)

    override fun groupByLength(apps: List<String>): Map<Int, List<String>> = apps.groupBy { it.length }

    override fun pressCounts(presses: List<String>): Map<String, Int> = presses.groupingBy { it }.eachCount()

    override fun select(current: String?, clicked: String): String? = if (current == clicked) null else clicked

    override fun visible(apps: List<String>, query: String, onlyLong: Boolean): List<String> {
        return apps.filter { it.contains(query, ignoreCase = true) && (!onlyLong || it.length >= 6) }
    }
}
