package com.example.minilauncher.practice

// 1일차 시간이 남으면: 정답. 먼저 Day1Extra.kt 를 풀어 보고 엽니다.
object Day1Answers : Day1Problems {

    override fun longNames(apps: List<String>): List<String> {
        return apps.filter { it.length >= 6 }
    }

    override fun upper(apps: List<String>): List<String> {
        return apps.map { it.uppercase() }
    }

    override fun lengthOf(apps: List<String>, name: String): Int {
        return apps.find { it == name }?.length ?: 0
    }

    override fun longestTitle(songs: List<Song>): String {
        return songs.maxByOrNull { it.seconds }?.title ?: "없음"
    }

    override fun batteryText(percent: Int): String = when {
        percent <= 20 -> "부족"
        percent <= 80 -> "보통"
        else -> "충분"
    }
}
