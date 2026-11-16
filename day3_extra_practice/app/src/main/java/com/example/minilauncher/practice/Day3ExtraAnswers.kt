package com.example.minilauncher.practice

// 3일차 연습 문제: 정답. 먼저 Day3Extra.kt 를 풀어 보고 엽니다.
object Day3Answers : Day3Problems {

    override fun reversed(apps: List<ExtraApp>): List<ExtraApp> = apps.sortedByDescending { it.label }

    override fun googleApps(apps: List<ExtraApp>): List<ExtraApp> = apps.filter { it.packageName.startsWith("com.google") }

    override fun search(apps: List<ExtraApp>, query: String): List<ExtraApp> {
        if (query.isBlank()) return apps
        return apps.filter {
            it.label.contains(query, ignoreCase = true) || it.packageName.contains(query, ignoreCase = true)
        }
    }

    override fun countByFirstLetter(apps: List<ExtraApp>): Map<Char, Int> {
        return apps.groupBy { it.label.first() }.mapValues { it.value.size }
    }

    override fun dock(apps: List<ExtraApp>, packages: List<String>): List<ExtraApp> {
        return packages.mapNotNull { wanted -> apps.find { it.packageName == wanted } }
    }

    override fun withoutSelf(apps: List<ExtraApp>, myPackage: String): List<ExtraApp> {
        return apps.filter { it.packageName != myPackage }
    }

    override fun firstMatch(apps: List<ExtraApp>, query: String): ExtraApp? {
        return apps.firstOrNull { it.label.contains(query, ignoreCase = true) }
    }

    override fun pages(apps: List<ExtraApp>, perPage: Int): List<List<ExtraApp>> = apps.chunked(perPage)

    override fun summary(apps: List<ExtraApp>): String {
        val names = apps.map { it.label }
        return if (names.size <= 2) names.joinToString(", ")
        else names.take(2).joinToString(", ") + " 외 ${names.size - 2}개"
    }

    override fun toggleFavorite(favorites: Set<String>, packageName: String): Set<String> {
        return if (packageName in favorites) favorites - packageName else favorites + packageName
    }

    override fun tagOf(line: String): String = line.substringAfter("/").substringBefore(":")

    override fun errorsOnly(lines: List<String>): List<String> = lines.filter { it.startsWith("E/") }

    override fun exceptionName(lines: List<String>): String {
        val line = lines.find { "Caused by:" in it } ?: return "없음"
        return line.substringAfter("Caused by: ").substringBefore(":").substringAfterLast(".")
    }

    override fun firstAppLine(lines: List<String>): String {
        return lines.find { "at com.example.minilauncher" in it }?.substringAfter("at ") ?: "없음"
    }

    override fun countByLevel(lines: List<String>): Map<Char, Int> = lines.groupingBy { it.first() }.eachCount()
}
