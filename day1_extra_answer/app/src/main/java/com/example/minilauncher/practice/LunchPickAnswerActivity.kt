package com.example.minilauncher.practice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.minilauncher.R

// 1일차 8교시 정답, 첫 화면: 점심 뽑기 (XML).
// XML 방식은 화면 모양(XML)과 동작(Kotlin)이 두 파일로 나뉘고, 값이 바뀔 때마다 글자를 직접 바꿔 줍니다
class LunchPickAnswerActivity : ComponentActivity() {

    private val history = ArrayList<String>()     // 지금까지 뽑은 메뉴

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch_answer)

        val menuText = findViewById<TextView>(R.id.menuText)
        val countText = findViewById<TextView>(R.id.countText)

        findViewById<Button>(R.id.pickButton).setOnClickListener {
            val menu = pickLunchAnswer(lunchMenus, history.lastOrNull())
            history.add(menu)
            menuText.text = menu                          // 값이 바뀌면 화면도 직접 고칩니다
            countText.text = "${history.size}번째 뽑기"
        }
        findViewById<Button>(R.id.historyButton).setOnClickListener {
            // 둘째 화면을 열면서 기록을 같이 넘깁니다. 오후에 배운 Intent 와 putExtra 입니다
            val intent = Intent(this, LunchHistoryAnswerActivity::class.java)
                .putStringArrayListExtra(EXTRA_HISTORY, history)
            startActivity(intent)
        }
        findViewById<Button>(R.id.resetButton).setOnClickListener {
            history.clear()
            menuText.text = "?"
            countText.text = "아직 안 뽑았어요"
        }
    }
}
