package com.example.minilauncher.data

import android.util.Log
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.model.FakeApps
import kotlinx.coroutines.delay
import kotlin.random.Random

// 실제 서버 대신 2초 기다렸다가 가짜 앱 목록을 돌려줍니다.
class FakeAppRepository(
    private val failRate: Float = 0.3f,
    private val random: Random = Random.Default,
) : AppRepository {
    override suspend fun loadApps(): List<AppInfo> {
        Log.d("Coroutine", "loadApps 시작, 스레드=${Thread.currentThread().name}")
        delay(2_000)
        if (random.nextFloat() < failRate) throw IllegalStateException("로딩 실패")
        return FakeApps
    }
}
