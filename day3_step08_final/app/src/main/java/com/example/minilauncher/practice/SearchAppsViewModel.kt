package com.example.minilauncher.practice

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.minilauncher.data.InstalledAppRepository
import com.example.minilauncher.model.AppInfo
import kotlinx.coroutines.launch

// "앱 찾기" 화면 뒤의 사무실. 앱 목록과 검색어를 들고 있어서 화면을 돌려도 그대로 남음
class SearchAppsViewModel(application: Application) : AndroidViewModel(application) {

    var apps by mutableStateOf<List<AppInfo>>(emptyList())     // 설치된 앱 전체
        private set
    var query by mutableStateOf("")                            // 검색어
        private set

    // 처음 한 번, 설치된 앱 목록을 읽음
    init {
        viewModelScope.launch {
            apps = InstalledAppRepository(getApplication()).loadApps()
        }
    }

    // 입력창 글자가 바뀌면 화면이 부름
    fun search(text: String) {
        query = text
    }
}
