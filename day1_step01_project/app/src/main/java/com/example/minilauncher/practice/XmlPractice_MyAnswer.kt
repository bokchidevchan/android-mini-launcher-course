package com.example.minilauncher.practice

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.minilauncher.R

// XmlPractice_My.kt 의 정답입니다. 먼저 직접 만들어 보고 엽니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "XML 연습 정답" 아이콘을 누릅니다. 화면 모양은 res/layout/xml_my_answer.xml 입니다.
class MyXmlAnswerActivity : ComponentActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xml_my_answer)
        val likeText = findViewById<TextView>(R.id.likeText)
        findViewById<Button>(R.id.likeButton).setOnClickListener {
            count++
            likeText.text = "좋아요 $count"      // 값이 바뀌면 글자도 직접 바꿔 줍니다
        }
        findViewById<Button>(R.id.resetButton).setOnClickListener {
            count = 0
            likeText.text = "좋아요 $count"
        }
    }
}
