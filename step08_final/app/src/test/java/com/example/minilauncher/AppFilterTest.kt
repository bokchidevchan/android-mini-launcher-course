package com.example.minilauncher

import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.model.FakeApps
import com.example.minilauncher.model.dockApps
import com.example.minilauncher.model.filterApps
import org.junit.Assert.assertEquals
import org.junit.Test

class AppFilterTest {
    @Test
    fun 검색어_set_이면_Settings_만() {
        assertEquals(listOf("Settings"), filterApps(FakeApps, "set").map { it.label })
    }

    @Test
    fun 빈_검색어면_전체() {
        assertEquals(FakeApps.size, filterApps(FakeApps, "  ").size)
    }

    @Test
    fun 도크는_있는_앱만_정해진_순서로() {
        val apps = listOf(AppInfo("Camera", "c"), AppInfo("Phone", "p"), AppInfo("Maps", "m"))
        assertEquals(listOf("Phone", "Camera"), dockApps(apps).map { it.label })
    }
}
