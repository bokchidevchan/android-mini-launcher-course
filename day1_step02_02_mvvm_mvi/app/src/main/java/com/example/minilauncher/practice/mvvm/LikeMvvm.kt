package com.example.minilauncher.practice.mvvm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.practice.LikePanel
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 비교 코드 1: MVVM 으로 만든 "좋아요" 화면. 같은 화면을 MVI 로 만든 것은 옆 폴더 practice/mvi/LikeMvi.kt 입니다.
// 두 파일을 Android Studio 에서 양옆에 열어 놓고 비교합니다 (탭을 오른쪽으로 끌어 놓으면 나란히 열립니다).
// Run 한 뒤 에뮬레이터의 앱 목록에서 "MVVM 예제" 아이콘을 누릅니다. Logcat 검색창에는 tag:Arch 를 넣습니다.
//
// MVVM 은 Model, View, ViewModel 의 앞 글자입니다.
//   View        화면. 그리기만 합니다 (아래 LikeMvvmActivity 와 LikePanel)
//   ViewModel   화면이 보여 줄 값과, 값을 바꾸는 함수를 들고 있습니다 (아래 LikeMvvmViewModel)
//   Model       데이터와 규칙. 이 예제는 작아서 따로 없습니다
// 화면이 ViewModel 에 말하는 방법: 할 일마다 함수가 따로 있습니다 (plus(), reset())
// 비유: 리모컨입니다. 버튼마다 하는 일이 따로 있습니다. C++ 로 치면 멤버 함수를 여러 개 부르는 방식
//
// 왜 이렇게 나누나
//   화면에서 계산을 빼기 위해서입니다. 화면은 그리기만 하고, 값을 바꾸는 규칙은 ViewModel 에 둡니다.
//   그래야 화면 없이 ViewModel 만 테스트할 수 있고, 화면을 돌려도 값이 남습니다 (day1_step03_lifecycle 에서 봅니다)
//
// Compose 에서는 MVVM 을 안 쓰나
//   안 쓰는 것이 아닙니다. 지금 이 파일이 "Compose + MVVM" 이고, 3일 동안 만드는 런처도 이 방식입니다.
//   안 쓰게 된 것은 XML 시절 MVVM 의 "양방향 바인딩"입니다. XML 에서는 화면과 값이 서로를 고쳤습니다
//   (입력 칸에 글자를 치면 값이 바뀌고, 값을 바꾸면 입력 칸이 바뀜). 편하지만 값이 어디서 바뀌었는지 찾기 어려웠습니다.
//   Compose 는 값이 한 방향으로만 흐릅니다. 값은 위에서 아래로 내려가고(count), 일어난 일은 아래에서 위로 올라옵니다(onPlus).
//   그래서 Compose 에서 MVVM 을 쓰면 모양이 MVI 와 비슷해지고, 아예 MVI 로 쓰는 팀도 많습니다
private const val TAG = "Arch"

class LikeMvvmViewModel : ViewModel() {
    // 상태가 값마다 따로 있습니다
    var count by mutableIntStateOf(0)
        private set
    var message by mutableStateOf("눌러 보세요")
        private set

    // 할 일마다 함수가 따로 있습니다
    fun plus() {
        Log.d(TAG, "MVVM: plus() 호출")
        count++
        message = "좋아요 ${count}개"       // 이 줄을 빼먹어도 빨간 줄이 안 생깁니다. 값 두 개를 내가 맞춰야 합니다
    }

    fun reset() {
        Log.d(TAG, "MVVM: reset() 호출")
        count = 0
        message = "처음으로 돌아왔습니다"
    }
}

class LikeMvvmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                val vm: LikeMvvmViewModel = viewModel()
                Box(Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp)) {
                    // 값을 하나씩 꺼내 쓰고, 버튼마다 다른 함수를 부릅니다
                    LikePanel(
                        title = "MVVM",
                        count = vm.count,
                        message = vm.message,
                        onPlus = { vm.plus() },
                        onReset = { vm.reset() },
                    )
                }
            }
        }
    }
}
