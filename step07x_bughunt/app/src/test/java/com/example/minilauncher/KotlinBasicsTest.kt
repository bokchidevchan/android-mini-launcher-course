package com.example.minilauncher

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

// 1일차 09:00 Kotlin 핵심. 함수 왼쪽 초록 화살표로 하나씩 실행합니다.
// C++ 과 다른 점 세 가지만 봅니다: null 안전, 람다, data class

data class App(val label: String, val packageName: String)

class KotlinBasicsTest {

    @Test
    fun nullSafety() {
        // C++ 포인터와 달리, null 이 될 수 있는 타입은 뒤에 ? 를 붙여야 합니다
        val found: App? = listOf(App("Chrome", "com.android.chrome")).find { it.label == "Maps" }
        assertNull(found)

        // ?. 는 null 이면 그냥 null, ?: 는 null 일 때 쓸 값
        val name = found?.label ?: "없음"
        assertEquals("없음", name)
    }

    @Test
    fun lambda() {
        val apps = listOf(App("Settings", "a"), App("Camera", "b"), App("Chrome", "c"))
        // C++ 의 [](auto& a){ ... } 자리. 마지막 인자가 람다면 괄호 밖으로 뺍니다
        val labels = apps.filter { it.label.startsWith("C") }.map { it.label }.sorted()
        assertEquals(listOf("Camera", "Chrome"), labels)
    }

    @Test
    fun dataClass() {
        // equals, hashCode, toString, copy 를 컴파일러가 만들어 줍니다
        val a = App("Chrome", "com.android.chrome")
        val b = a.copy()
        assertEquals(a, b)                 // 내용 비교 (C++ 의 operator== 를 직접 안 써도 됨)
        assertEquals("App(label=Chrome, packageName=com.android.chrome)", a.toString())

        val (label, pkg) = a               // 구조 분해
        assertEquals("Chrome", label)
        assertEquals("com.android.chrome", pkg)
    }
}
