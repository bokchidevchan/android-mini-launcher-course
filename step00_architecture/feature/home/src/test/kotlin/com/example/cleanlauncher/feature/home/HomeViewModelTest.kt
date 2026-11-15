package com.example.cleanlauncher.feature.home

import com.example.cleanlauncher.domain.model.AppInfo
import com.example.cleanlauncher.domain.repository.AppRepository
import com.example.cleanlauncher.domain.usecase.GetAppsUseCase
import com.example.cleanlauncher.domain.usecase.SearchAppsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

// ViewModel 테스트. 가짜 저장소를 꽂아서 화면도 기기도 없이 확인합니다
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private class FakeAppRepository(private val fail: Boolean = false) : AppRepository {
        override suspend fun loadApps(): List<AppInfo> {
            if (fail) throw IllegalStateException("목록을 읽지 못함")
            return listOf(
                AppInfo("Settings", "com.android.settings", "S"),
                AppInfo("Chrome", "com.android.chrome", "C"),
            )
        }
    }

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    private fun viewModel(fail: Boolean = false) =
        HomeViewModel(GetAppsUseCase(FakeAppRepository(fail)), SearchAppsUseCase())

    @Test
    fun 처음에는_불러오는_중이고_끝나면_이름순_목록() = runTest(dispatcher) {
        val vm = viewModel()
        assertEquals(HomeUiState.Loading, vm.uiState.value)
        advanceUntilIdle()
        val state = vm.uiState.value as HomeUiState.Ready
        assertEquals(listOf("Chrome", "Settings"), state.apps.map { it.label })
    }

    @Test
    fun 검색어를_넣으면_맞는_앱만() = runTest(dispatcher) {
        val vm = viewModel()
        advanceUntilIdle()
        vm.onQueryChange("set")
        val state = vm.uiState.value as HomeUiState.Ready
        assertEquals(listOf("Settings"), state.apps.map { it.label })
        assertEquals("set", state.query)
    }

    @Test
    fun 불러오기에_실패하면_Error() = runTest(dispatcher) {
        val vm = viewModel(fail = true)
        advanceUntilIdle()
        assertTrue(vm.uiState.value is HomeUiState.Error)
    }
}
