package com.example.minilauncher

import com.example.minilauncher.practice.lunchMenus
import com.example.minilauncher.practice.mostPickedAnswer
import com.example.minilauncher.practice.pickLunchAnswer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LunchAnswerTest {
    @Test
    fun 목록에_있는_메뉴가_나오고_같은_메뉴가_연달아_나오지_않는다() {
        var last: String? = null
        repeat(300) {
            val menu = pickLunchAnswer(lunchMenus, last)
            assertTrue(menu in lunchMenus)
            assertNotEquals(last, menu)
            last = menu
        }
    }

    @Test
    fun 가장_많이_뽑힌_메뉴() {
        assertEquals("돈까스" to 2, mostPickedAnswer(listOf("돈까스", "쌀국수", "돈까스")))
        assertNull(mostPickedAnswer(emptyList()))
    }
}
