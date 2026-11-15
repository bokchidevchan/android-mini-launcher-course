package com.example.cleanlauncher.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanlauncher.domain.model.AppInfo

// 화면은 상태를 보고 그리기만 합니다. 눌렸다는 사실은 위로 알립니다
@Composable
fun HomeScreen(viewModel: HomeViewModel, onAppClick: (AppInfo) -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    // 입력 중인 글자는 화면이 들고 있고, 검색 결과는 ViewModel 이 들고 있습니다
    var query by rememberSaveable { mutableStateOf("") }

    Column(Modifier.fillMaxSize().safeDrawingPadding().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.onQueryChange(it)
            },
            label = { Text("앱 검색") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("search"),
        )
        when (val s = state) {
            HomeUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            is HomeUiState.Error -> Text(s.message, Modifier.padding(top = 24.dp))
            is HomeUiState.Ready -> AppList(s.apps, onAppClick)
        }
    }
}

@Composable
private fun AppList(apps: List<AppInfo>, onAppClick: (AppInfo) -> Unit) {
    if (apps.isEmpty()) {
        Text("찾는 앱이 없습니다", Modifier.padding(top = 24.dp))
        return
    }
    LazyColumn(Modifier.fillMaxSize().padding(top = 8.dp)) {
        items(apps, key = { it.packageName + it.className }) { app ->
            Column(Modifier.fillMaxWidth().clickable { onAppClick(app) }.padding(vertical = 10.dp)) {
                Text(app.label, style = MaterialTheme.typography.titleMedium)
                Text(app.packageName, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
