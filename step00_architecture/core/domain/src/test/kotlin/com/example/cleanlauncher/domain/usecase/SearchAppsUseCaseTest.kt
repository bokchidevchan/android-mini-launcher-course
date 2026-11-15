package com.example.cleanlauncher.domain.usecase

import com.example.cleanlauncher.domain.model.AppInfo
import org.junit.Assert.assertEquals
import org.junit.Test

// 단위 테스트. 에뮬레이터 없이 내 컴퓨터에서 바로 돕니다
class SearchAppsUseCaseTest {

    private val apps = listOf(
        AppInfo("Camera", "com.android.camera2", "Camera"),
        AppInfo("Chrome", "com.android.chrome", "Main"),
        AppInfo("Settings", "com.android.settings", "Settings"),
    )
    private val search = SearchAppsUseCase()

    @Test
    fun 검색어가_비어_있으면_전체() {
        assertEquals(apps, search(apps, ""))
        assertEquals(apps, search(apps, "   "))
    }

    @Test
    fun 검색어가_이름에_들어_있는_앱만() {
        assertEquals(listOf("Settings"), search(apps, "set").map { it.label })
    }

    @Test
    fun 대소문자를_가리지_않음() {
        assertEquals(listOf("Camera", "Chrome"), search(apps, "C").map { it.label })
    }

    @Test
    fun 맞는_앱이_없으면_빈_목록() {
        assertEquals(emptyList<AppInfo>(), search(apps, "zzz"))
    }
}
