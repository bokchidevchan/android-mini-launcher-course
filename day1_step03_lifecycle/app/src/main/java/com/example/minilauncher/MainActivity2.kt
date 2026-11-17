package com.example.minilauncher

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

private const val TAG = "Lifecycle"

// ViewModel 을 안 쓰는 계산기. 화면을 돌리면 계산하던 값이 0 으로 돌아갑니다.
// MainActivity 와 다른 점은 값을 어디에 두느냐 하나뿐입니다
class MainActivity2 : ComponentActivity() {

    // 값이 Activity 의 변수입니다. 화면을 돌리면 Activity 가 없어졌다 새로 만들어지니, 이 변수도 다시 "0" 에서 시작합니다
    private var text = "0"

    private lateinit var textDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity2 onCreate (복원 데이터 ${if (savedInstanceState == null) "없음" else "있음"})")
        setContentView(R.layout.activity_main)      // MainActivity 와 같은 화면 모양을 씁니다

        findViewById<TextView>(R.id.textLabel).text = "ViewModel 없음 (Activity 의 변수)"
        textDisplay = findViewById(R.id.textDisplay)

        val keypad = findViewById<GridLayout>(R.id.keypad)
        for (i in 0 until keypad.childCount) {
            val button = keypad.getChildAt(i) as Button
            button.setOnClickListener {
                text = pressKey(text, button.text.toString())
                textDisplay.text = text
            }
        }

        // 새로 만들어진 Activity 의 text 는 "0" 이라, 화면을 돌린 뒤에는 0 이 그려집니다
        textDisplay.text = text
    }

    // 화면을 돌리면 여기가 찍히고(true), 바로 뒤에 onCreate 가 다시 찍힙니다. 그 사이에 text 가 사라집니다
    override fun onDestroy() {
        Log.d(TAG, "MainActivity2 onDestroy (회전 때문이면 true: $isChangingConfigurations)"); super.onDestroy()
    }
}
