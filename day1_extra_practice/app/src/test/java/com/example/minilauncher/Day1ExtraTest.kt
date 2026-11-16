package com.example.minilauncher

import com.example.minilauncher.practice.Day1Answers
import com.example.minilauncher.practice.Day1Extra
import com.example.minilauncher.practice.checkDay1
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Day1ExtraTest {
    @Test
    fun 정답은_모두_O() {
        val lines = checkDay1(Day1Answers)
        assertEquals(5, lines.size)
        assertTrue(lines.toString(), lines.all { it.endsWith(": O") })
    }

    @Test
    fun 문제_파일은_풀기_전에도_채점이_끝까지_돈다() {
        assertEquals(5, checkDay1(Day1Extra).size)
    }
}
