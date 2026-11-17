package com.example.minilauncher.mvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 비교 코드 2: MVI 의 화면(View). Compose 로 그립니다. 앱 목록의 "MVI 예제 (Compose)" 아이콘. Logcat 은 tag:Arch
// 화면이 하는 일은 둘뿐입니다.
//   1. 상태(state)를 받아 그대로 그립니다
//   2. 버튼이 눌리면 주문서를 냅니다 (onIntent)
// 옆 폴더 mvvm 의 화면과 달리 "값이 바뀌면 글자를 고쳐 주는 줄"이 없습니다. Compose 는 값이 바뀌면 알아서 다시 그리기 때문입니다.
// 값은 위에서 아래로 내려가고(state), 일어난 일은 아래에서 위로 올라옵니다(onIntent). 한 방향으로만 흐릅니다
class MviActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val vm: MviViewModel = viewModel()
                LikeScreen(state = vm.state, onIntent = vm::onIntent)
            }
        }
    }
}

// 상태 하나를 받고, 문 하나로 내보냅니다
@Composable
fun LikeScreen(state: LikeState, onIntent: (LikeIntent) -> Unit) {
    Column(
        Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("MVI (Compose)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text("${state.count}", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Text(state.message, fontSize = 16.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onIntent(LikeIntent.Plus) }) { Text("+1") }
            OutlinedButton(onClick = { onIntent(LikeIntent.Reset) }) { Text("처음으로") }
        }
    }
}
