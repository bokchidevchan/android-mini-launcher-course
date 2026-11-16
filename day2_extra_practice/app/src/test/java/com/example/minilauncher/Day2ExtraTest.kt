package com.example.minilauncher

import com.example.minilauncher.practice.Day2Answers
import com.example.minilauncher.practice.Day2Extra
import com.example.minilauncher.practice.checkDay2
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Day2ExtraTest {
    @Test
    fun 정답은_모두_O() = runBlocking {
        val lines = checkDay2(Day2Answers)
        assertEquals(5, lines.size)
        assertTrue(lines.toString(), lines.all { it.endsWith(": O") })
    }

    @Test
    fun 문제_파일은_풀기_전에도_채점이_끝까지_돈다() = runBlocking {
        assertEquals(5, checkDay2(Day2Extra).size)
    }
}
