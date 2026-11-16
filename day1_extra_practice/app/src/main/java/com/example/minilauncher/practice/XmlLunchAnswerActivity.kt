package com.example.minilauncher.practice

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.minilauncher.R

// 1일차 8교시 정답: "오늘 점심 뭐 먹지?" XML 판.
// XML 방식은 화면 모양(XML)과 동작(Kotlin)이 두 파일로 나뉘고, 값이 바뀔 때마다 글자를 직접 바꿔 줍니다
class XmlLunchAnswerActivity : ComponentActivity() {

    private var last: String? = null     // 방금 뽑은 메뉴
    private var count = 0                // 몇 번 뽑았는지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch_answer)

        val menuText = findViewById<TextView>(R.id.menuText)
        val countText = findViewById<TextView>(R.id.countText)

        findViewById<Button>(R.id.pickButton).setOnClickListener {
            val menu = pickLunchAnswer(lunchMenus, last)
            last = menu
            count++
            menuText.text = menu                      // 값이 바뀌면 화면도 직접 고칩니다
            countText.text = "${count}번째 뽑기"
        }
        findViewById<Button>(R.id.resetButton).setOnClickListener {
            last = null
            count = 0
            menuText.text = "?"
            countText.text = "아직 안 뽑았어요"
        }
    }
}
