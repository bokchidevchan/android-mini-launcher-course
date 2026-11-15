package com.example.cleanlauncher.domain.usecase

import com.example.cleanlauncher.domain.model.AppInfo
import com.example.cleanlauncher.domain.repository.AppRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAppsUseCaseTest {

    // 진짜 기기 대신 꽂는 가짜 저장소. 인터페이스만 맞추면 됩니다
    private class FakeAppRepository(private val apps: List<AppInfo>) : AppRepository {
        override suspend fun loadApps(): List<AppInfo> = apps
    }

    @Test
    fun 앱_목록은_이름순() = runTest {
        val repository = FakeAppRepository(
            listOf(
                AppInfo("settings", "s", "S"),
                AppInfo("Chrome", "c", "C"),
                AppInfo("Camera", "a", "A"),
            )
        )
        val labels = GetAppsUseCase(repository)().map { it.label }
        assertEquals(listOf("Camera", "Chrome", "settings"), labels)
    }
}
