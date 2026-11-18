package com.example.minilauncher.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.launchApp
import com.example.minilauncher.ui.AppIconImage
import com.example.minilauncher.ui.theme.LauncherBackground
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 3일차: 검색해서 여는 앱 "앱 찾기". 완성한 미니 런처의 검색창이 하는 일을 화면 하나로 줄인 것입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "앱 찾기" 아이콘을 누릅니다 (기본 홈 앱을 바꾸기 전이나, 되돌린 뒤에).
// 글자를 넣으면 이름에 그 글자가 든 앱만 남고, 누르면 그 앱이 열립니다.
// [1] [2] [3] 은 같은 폴더의 뼈대 SearchApps_My.kt 에서 채우는 자리이고, 같은 번호가 같은 자리에 있습니다.

class SearchAppsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MiniLauncherTheme { SearchAppsScreen() } }
    }
}

@Composable
fun SearchAppsScreen(vm: SearchAppsViewModel = viewModel()) {
    val context = LocalContext.current
    val apps = vm.apps      // 설치된 앱 전체 (ViewModel 이 처음 한 번 읽어 둠)
    val query = vm.query    // 검색어 (ViewModel 에 있어서 화면을 돌려도 남음)

    // [1] 검색어가 이름에 든 앱만 남깁니다. 검색어가 비어 있으면 contains("") 가 항상 참이라 전부 남습니다
    //     filter { 조건 }: 조건이 true 인 앱만 남김.  ignoreCase = true: 대소문자 무시 ("c" 로 "Chrome" 도 찾음)
    val shown = apps.filter { it.label.contains(query, ignoreCase = true) }

    Column(Modifier.fillMaxSize().background(LauncherBackground).safeDrawingPadding().padding(16.dp)) {
        Text("앱 찾기", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        // [2] 입력창은 값을 스스로 기억하지 않습니다. query 를 보여 주고, 글자가 바뀌면 query 를 바꿉니다
        OutlinedTextField(
            value = query,
            onValueChange = { vm.search(it) },   // 글자가 바뀔 때마다 불림. it 이 새 글자
            label = { Text("앱 이름") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Text("${shown.size}개", modifier = Modifier.padding(vertical = 8.dp))
        LazyColumn {
            items(shown, key = { it.packageName + it.className }) { app ->
                Row(
                    // [3] 줄을 누르면 그 앱을 엽니다
                    modifier = Modifier.fillMaxWidth().clickable { launchApp(context, app) }.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppIconImage(app, 40.dp)
                    Spacer(Modifier.width(12.dp))
                    Text(app.label)
                }
            }
        }
    }
}
