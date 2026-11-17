package com.example.minilauncher.practice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 같이 해 보기 파일입니다. 완성본은 같은 폴더의 LifecyclePractice.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "생명주기 연습 직접" 아이콘을 누릅니다. Logcat 검색창에는 tag:MyLife 를 넣습니다.
// 화면 모양(EntranceLayout, CalculatorLayout), 금고(CalculatorViewModel), 글자판(pressKey)은 완성본의 것을 그대로 씁니다.
// 필요한 import 는 위에 다 있습니다. 회색으로 보이는 것은 아직 안 써서 그런 것입니다.
//
// [1] 계산기 화면 열기. MyLifecycleActivity 의 onOpen = { } 의 { } 안에 붙이고 Run. 계산기 열기를 눌러 화면이 바뀌는지 봅니다
//         val intent = Intent(this@MyLifecycleActivity, MyCalculatorActivity::class.java)
//         startActivity(intent)
//
// [2] 콜백마다 로그 찍기. MyCalculatorActivity 의 "[2] 여기에" 자리에 붙이고 Run. 계산기를 열고, 화면을 돌리고, 뒤로 가 봅니다
//         override fun onStart() { super.onStart(); Log.d(TAG, "계산기 onStart") }
//         override fun onResume() { super.onResume(); Log.d(TAG, "계산기 onResume") }
//         override fun onPause() { super.onPause(); Log.d(TAG, "계산기 onPause") }
//         override fun onStop() { super.onStop(); Log.d(TAG, "계산기 onStop") }
//         override fun onDestroy() { super.onDestroy(); Log.d(TAG, "계산기 onDestroy") }
//
// [3] 세 가지로 기억하기. "[3] 여기에" 자리에 붙이고, CalculatorLayout 의 "0" 세 개를 remembered, saved, vm.text 로,
//     onKey = { } 를 아래 모양으로 바꿉니다. 12+3 을 누르고 화면을 돌려 봅니다. 그다음 뒤로 갔다가 다시 열어 봅니다
//         var remembered by remember { mutableStateOf("0") }
//         var saved by rememberSaveable { mutableStateOf("0") }
//         val vm: CalculatorViewModel = viewModel()
//
//         onKey = { key -> remembered = pressKey(remembered, key); saved = pressKey(saved, key); vm.press(key) },

private const val TAG = "MyLife"

// 입구 화면
class MyLifecycleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "입구 onCreate")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                EntranceLayout(
                    onOpen = { },
                    onClose = { finish() },
                )
            }
        }
    }
}

// 계산기 화면
class MyCalculatorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "계산기 onCreate")
        enableEdgeToEdge()
        setContent {
            MiniLauncherTheme {
                // [3] 여기에
                CalculatorLayout(
                    remembered = "0",
                    saved = "0",
                    inViewModel = "0",
                    onKey = { },
                    onClose = { finish() },
                )
            }
        }
    }

    // [2] 여기에
}
