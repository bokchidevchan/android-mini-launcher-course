package com.example.minilauncher

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minilauncher.data.AppRepository
import com.example.minilauncher.model.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AppsState {
    data object Loading : AppsState
    data class Error(val message: String) : AppsState
    data class Success(val apps: List<AppInfo>) : AppsState
}

data class LauncherUiState(
    val apps: AppsState = AppsState.Loading,
    val selected: String? = null,
)

// 앱 목록의 상태(불러오는 중, 성공, 실패)와 고른 앱을 들고 있다가 화면에 알려 줍니다
// 목록은 생성자로 받은 AppRepository 에 물어서 받습니다 (지금은 실제 설치 앱을 주는 InstalledAppRepository)
class LauncherViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LauncherUiState())
    val uiState: StateFlow<LauncherUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.update { it.copy(apps = AppsState.Loading) }
        viewModelScope.launch {
            val next = try {
                AppsState.Success(repository.loadApps())
            } catch (e: IllegalStateException) {
                Log.w("Coroutine", "앱 목록 실패: ${e.message}")
                AppsState.Error(e.message ?: "알 수 없는 오류")
            }
            _uiState.update { it.copy(apps = next) }
        }
    }

    fun select(app: AppInfo) {
        _uiState.update { it.copy(selected = app.packageName) }
    }
}
