package com.example.minilauncher.practice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 같이 해 보기 파일입니다. 완성본은 같은 폴더의 LifecyclePractice.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "생명주기 연습 직접" 아이콘을 누릅니다. Logcat 검색창에는 tag:MyLife 를 넣습니다.
// 화면 모양(LifecycleLayout), 금고(CounterViewModel), 둘째 화면(LifecycleSecondActivity), EXTRA_COUNT 는 완성본의 것을 그대로 씁니다.
// 필요한 import 는 위에 다 있습니다. 회색으로 보이는 것은 아직 안 써서 그런 것입니다.
//
// [1] 콜백마다 로그 찍기. 아래 "[1] 여기에" 자리에 붙이고 Run. 홈으로 나갔다 돌아오고, 화면을 돌려 봅니다
//         override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
//         override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
//         override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
//         override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
//         override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
//
// [2] 세 가지로 기억하기. 아래 "[2] 여기에" 자리에 붙이고, LifecycleLayout 의 0 세 개를 remembered, saved, vm.count 로,
//     onAdd = { } 를 onAdd = { remembered++; saved++; vm.add() } 로 바꿉니다. +1 을 누르고 화면을 돌려 봅니다
//         var remembered by remember { mutableIntStateOf(0) }
//         var saved by rememberSaveable { mutableIntStateOf(0) }
//         val vm: CounterViewModel = viewModel()
//
// [3] 다음 화면 열기. onOpen = { } 의 { } 안에 붙입니다 ([2]를 먼저 해야 vm 을 쓸 수 있습니다)
//         val intent = Intent(this@MyLifecycleActivity, LifecycleSecondActivity::class.java)
//         intent.putExtra(EXTRA_COUNT, vm.count)
//         startActivity(intent)

private const val TAG = "MyLife"

class MyLifecycleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                // [2] 여기에
                LifecycleLayout(
                    remembered = 0,
                    saved = 0,
                    inViewModel = 0,
                    onAdd = { },
                    onOpen = { },
                    onClose = { finish() },
                )
            }
        }
    }

    // [1] 여기에
}
