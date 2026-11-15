package com.example.minilauncher

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.minilauncher.model.AppInfo
import com.example.minilauncher.model.FakeApps
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 화면을 돌리면 Activity 는 새로 만들어지지만 ViewModel 은 그대로 남습니다
class LauncherViewModel : ViewModel() {
    init {
        Log.d("Lifecycle", "LauncherViewModel 생성")
    }

    val apps: List<AppInfo> = FakeApps

    private val _selected = MutableStateFlow<String?>(null)
    val selected: StateFlow<String?> = _selected.asStateFlow()

    fun select(app: AppInfo) {
        _selected.value = app.packageName
    }

    override fun onCleared() {
        Log.d("Lifecycle", "LauncherViewModel onCleared")
    }
}
