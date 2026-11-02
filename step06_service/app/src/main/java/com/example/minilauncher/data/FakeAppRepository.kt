package com.example.minilauncher.data

import android.util.Log
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.model.FakeApps
import kotlinx.coroutines.delay
import kotlin.random.Random

// 실제 서버 대신 2초 기다렸다가 가짜 앱 목록을 돌려줍니다.
// failRate 만큼의 확률로 실패해서, 에러 화면도 볼 수 있게 했습니다.
class FakeAppRepository(
    private val failRate: Float = 0.3f,
    private val random: Random = Random.Default,
) {
    // suspend: 기다리는 동안 스레드를 붙잡지 않고 양보합니다. 메인 스레드에서 불러도 화면이 멈추지 않습니다
    suspend fun loadApps(): List<AppInfo> {
        Log.d("Coroutine", "loadApps 시작, 스레드=${Thread.currentThread().name}")
        delay(2_000)
        if (random.nextFloat() < failRate) throw IllegalStateException("로딩 실패")
        return FakeApps
    }
}
