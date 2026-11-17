package com.example.minilauncher.mvvm

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.minilauncher.R

// 비교 코드 1: MVVM 의 화면(View). XML 로 그립니다. 앱 목록의 "MVVM 예제 (XML)" 아이콘. Logcat 은 tag:Arch
// 이 파일이 하는 일은 둘뿐입니다. 계산은 하지 않습니다.
//   1. 버튼이 눌리면 ViewModel 의 함수를 부릅니다
//   2. ViewModel 의 값이 바뀌면 화면의 글자를 고칩니다
//
// XML 은 값이 바뀌어도 화면이 알아서 바뀌지 않습니다. 그래서 "값을 지켜보다가 바뀌면 글자를 고쳐 주는" 줄(observe)을 값마다 적습니다.
// 값이 둘이면 두 줄, 열이면 열 줄입니다. 옆 폴더 mvi 의 Compose 화면에는 이 줄이 없습니다
class MvvmActivity : ComponentActivity() {

    // 화면을 돌려도 남는 ViewModel 을 받아 옵니다
    private val vm: MvvmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvvm_like)
        val countText = findViewById<TextView>(R.id.countText)
        val messageText = findViewById<TextView>(R.id.messageText)

        // 1. 화면 -> ViewModel: 버튼마다 다른 함수를 부릅니다
        findViewById<Button>(R.id.plusButton).setOnClickListener { vm.plus() }
        findViewById<Button>(R.id.resetButton).setOnClickListener { vm.reset() }

        // 2. ViewModel -> 화면: 값을 지켜보다가 바뀌면 글자를 직접 고칩니다
        vm.count.observe(this) { countText.text = "$it" }
        vm.message.observe(this) { messageText.text = it }
    }
}
