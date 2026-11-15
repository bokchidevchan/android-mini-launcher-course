package com.example.minilauncher

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minilauncher.data.FakeAppRepository
import com.example.minilauncher.model.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 화면이 가질 수 있는 상태를 하나로 묶습니다. 화면은 이 값만 보고 그립니다 (단방향 데이터 흐름)
sealed interface AppsState {
    data object Loading : AppsState
    data class Error(val message: String) : AppsState
    data class Success(val apps: List<AppInfo>) : AppsState
}

data class LauncherUiState(
    val apps: AppsState = AppsState.Loading,
    val selected: String? = null,
)

class LauncherViewModel(private val repository: FakeAppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LauncherUiState())
    val uiState: StateFlow<LauncherUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.update { it.copy(apps = AppsState.Loading) }
        // viewModelScope: ViewModel 이 사라지면(onCleared) 여기서 시작한 코루틴도 같이 취소됩니다
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
