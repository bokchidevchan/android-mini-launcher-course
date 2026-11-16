package com.example.minilauncher

import com.example.minilauncher.practice.GameState
import com.example.minilauncher.practice.ReactionAnswerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReactionAnswerTest {
    private val main = StandardTestDispatcher()

    @Before fun setUp() = Dispatchers.setMain(main)
    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun 기다렸다_누르면_걸린_시간이_나오고_일찍_누르면_너무_빨랐어요() = runTest(main) {
        var clock = 1_000L
        val vm = ReactionAnswerViewModel(waitMs = { 2_000L }, now = { clock })
        assertEquals(GameState.Ready, vm.state.value)

        vm.onTap()                                  // 시작
        assertEquals(GameState.Waiting, vm.state.value)
        advanceTimeBy(2_001)
        assertEquals(GameState.Go, vm.state.value)
        clock += 250
        vm.onTap()                                  // 0.25초 만에 누름
        assertEquals(GameState.Result(250), vm.state.value)
        assertEquals(250L, vm.best.value)

        vm.onTap()                                  // 다시 시작
        assertEquals(GameState.Waiting, vm.state.value)
        vm.onTap()                                  // 초록색이 되기 전에 누름
        assertEquals(GameState.TooEarly, vm.state.value)
        advanceTimeBy(5_000)                        // 취소했으므로 초록색이 되지 않습니다
        assertEquals(GameState.TooEarly, vm.state.value)
        assertEquals(250L, vm.best.value)
    }
}
