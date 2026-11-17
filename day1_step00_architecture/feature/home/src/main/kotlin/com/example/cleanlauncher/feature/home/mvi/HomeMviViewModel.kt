package com.example.cleanlauncher.feature.home.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanlauncher.domain.model.AppInfo
import com.example.cleanlauncher.domain.usecase.GetAppsUseCase
import com.example.cleanlauncher.domain.usecase.SearchAppsUseCase
import com.example.cleanlauncher.feature.home.HomeUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 비교 코드: 같은 홈 화면의 ViewModel 을 MVI 로 쓰면 이렇게 됩니다. 바로 위 폴더의 HomeViewModel.kt(MVVM)와 나란히 놓고 봅니다.
// 앱은 지금도 MVVM 쪽(HomeViewModel)을 씁니다. 이 파일은 비교해서 읽는 용도이고, 동작은 HomeMviViewModelTest 로 확인합니다.
//
// 멀티모듈에서 볼 점: MVVM 을 MVI 로 바꿔도 고치는 곳은 이 feature:home 모듈 하나뿐입니다.
//   core:domain   규칙(이름순, 검색). 화면이 MVVM 인지 MVI 인지 모릅니다. 한 글자도 안 바뀝니다
//   core:data     앱 목록을 읽는 곳. 마찬가지로 안 바뀝니다
//   feature:home  화면과 ViewModel. MVVM 과 MVI 의 차이는 전부 여기 안의 이야기입니다
// 모듈을 나눈 이유가 이것입니다. 화면을 그리는 방식을 바꿔도 규칙과 데이터는 건드리지 않습니다.
//
// MVVM(HomeViewModel)과 달라진 곳
//   함수 load(), onQueryChange(text) 두 개  ->  onIntent(intent) 하나
//   화면이 부르는 모양: viewModel.onQueryChange("set")  ->  viewModel.onIntent(HomeIntent.QueryChanged("set"))
//   상태(HomeUiState)는 원래 한 덩어리였으므로 그대로 씁니다

// 주문서: 홈 화면에서 일어날 수 있는 일의 목록
sealed interface HomeIntent {
    data object Load : HomeIntent                                // 목록을 (다시) 불러와 주세요
    data class QueryChanged(val text: String) : HomeIntent       // 검색어가 바뀌었습니다
}

class HomeMviViewModel(
    private val getApps: GetAppsUseCase,
    private val searchApps: SearchAppsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var allApps: List<AppInfo>? = null
    private var query = ""

    init {
        onIntent(HomeIntent.Load)
    }

    // 들어오는 문은 이 함수 하나입니다. 화면에서 무슨 일이 있었는지 여기만 보면 다 나옵니다
    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Load -> load()
            is HomeIntent.QueryChanged -> {
                query = intent.text
                publish()
            }
        }
    }

    private fun load() {
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

    private fun publish() {
        val apps = allApps ?: return
        _uiState.value = HomeUiState.Ready(searchApps(apps, query), query)
    }
}
