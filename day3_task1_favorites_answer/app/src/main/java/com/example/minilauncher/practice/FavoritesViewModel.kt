package com.example.minilauncher.practice

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// 화면 뒤의 사무실. 화면을 돌려도 이 객체는 그대로 남아서 즐겨찾기가 안 사라짐
class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    var apps by mutableStateOf<List<AppInfo>>(emptyList())        // 설치된 앱 전체
        private set    // 읽기는 화면에서도, 바꾸기는 ViewModel 안에서만
    var favorites by mutableStateOf<List<String>>(emptyList())    // 즐겨찾기한 앱의 패키지 이름
        private set    // 읽기는 화면에서도, 바꾸기는 ViewModel 안에서만

    // ViewModel 이 처음 만들어질 때 한 번 실행됨
    init {
        // [1] 설치된 앱 목록을 읽어 apps 에 넣기 (오래 걸릴 수 있어 코루틴 안에서)
        viewModelScope.launch {
            apps = loadInstalledApps(getApplication())   // getApplication(): 앱 전체의 Context
        }
    }

    // 별을 누르면 화면이 부르는 함수
    fun toggle(app: AppInfo) {
        // [2] 이미 있으면 빼고, 없으면 넣기
        favorites = if (app.packageName in favorites) favorites - app.packageName else favorites + app.packageName
    }
}
