package com.example.minilauncher.practice

// 3일차 시간이 남으면: 정답. 먼저 Day3Extra.kt 를 풀어 보고 엽니다.
object Day3Answers : Day3Problems {

    override fun reversed(apps: List<ExtraApp>): List<ExtraApp> {
        return apps.sortedByDescending { it.label }
    }

    override fun googleApps(apps: List<ExtraApp>): List<ExtraApp> {
        return apps.filter { it.packageName.startsWith("com.google") }
    }

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
}
