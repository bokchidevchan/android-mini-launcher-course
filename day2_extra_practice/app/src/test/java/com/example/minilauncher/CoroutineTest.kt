package com.example.minilauncher

import com.example.minilauncher.data.FakeAppRepository
import com.example.minilauncher.lab.RaceCounter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTest {
    private val main = StandardTestDispatcher()

    @Before fun setUp() = Dispatchers.setMain(main)
    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun 성공하면_Loading_다음_Success() = runTest(main) {
        val vm = LauncherViewModel(FakeAppRepository(failRate = 0f))
        assertEquals(AppsState.Loading, vm.uiState.value.apps)
        advanceUntilIdle()   // 2초 delay 를 가상 시간으로 건너뜁니다
        assertTrue(vm.uiState.value.apps is AppsState.Success)
    }

    @Test
    fun 실패하면_Error() = runTest(main) {
        val vm = LauncherViewModel(FakeAppRepository(failRate = 1f))
        advanceUntilIdle()
        assertEquals(AppsState.Error("로딩 실패"), vm.uiState.value.apps)
    }

    @Test
    fun Mutex_를_쓰면_항상_정확한_값() = runBlocking {
        repeat(5) { assertEquals(RaceCounter.TIMES * 2, RaceCounter.withMutex(Dispatchers.Default)) }
    }
}
