package com.example.cleanlauncher.feature.home

import com.example.cleanlauncher.domain.model.AppInfo

// 화면이 가질 수 있는 상태는 이 셋 중 하나입니다
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Ready(val apps: List<AppInfo>, val query: String) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
