package com.example.cleanlauncher.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanlauncher.domain.model.AppInfo
import com.example.cleanlauncher.domain.usecase.GetAppsUseCase
import com.example.cleanlauncher.domain.usecase.SearchAppsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 화면의 상태를 들고 있습니다. 규칙(이름순, 검색)은 직접 하지 않고 core:domain 에 맡깁니다.
// 앱 목록이 어디서 오는지도 모릅니다
class HomeViewModel(
    private val getApps: GetAppsUseCase,
    private val searchApps: SearchAppsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var allApps: List<AppInfo>? = null
    private var query = ""

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                allApps = getApps()
                publish()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "앱 목록을 불러오지 못했습니다")
            }
        }
    }

    fun onQueryChange(text: String) {
        query = text
        publish()
    }

    private fun publish() {
        val apps = allApps ?: return        // 아직 불러오는 중이면 그대로 둡니다
        _uiState.value = HomeUiState.Ready(searchApps(apps, query), query)
    }
}
