package com.example.minilauncher.practice

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.minilauncher.R

// 1일차 8교시: "오늘 점심 뭐 먹지?" 를 XML 로 만듭니다.
// 실행: 에뮬레이터의 앱 목록에서 "점심 뽑기 XML 연습" 아이콘.
// 화면 모양은 res/layout/activity_lunch_practice.xml 에, 동작은 이 파일에 만듭니다.
// 메뉴 목록은 lunchMenus (LunchMenus.kt) 를 씁니다.
// 정답: XmlLunchAnswerActivity.kt, res/layout/activity_lunch_answer.xml ("점심 뽑기 XML 정답" 아이콘)
class XmlLunchPracticeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // XML 파일을 이 화면의 모양으로 씁니다
        setContentView(R.layout.activity_lunch_practice)

        // 여기부터 직접 만듭니다. (TextView, Button 은 위에 import 해 두었습니다)
        // 1. XML 에 넣은 것을 id 로 찾습니다.      val menuText = findViewById<TextView>(R.id.menuText)
        // 2. 버튼이 눌리면 할 일을 적습니다.        findViewById<Button>(R.id.pickButton).setOnClickListener { ... }
        // 3. 목록에서 아무거나 하나 고르기.          lunchMenus.random()
        // 4. 화면의 글자 바꾸기.                    menuText.text = "돈까스"
        // 5. 몇 번 뽑았는지는 변수로 셉니다.        var count = 0 을 onCreate 위에 두고, 누를 때마다 count++
        // 6. 처음부터 버튼은 count 를 0 으로 하고 글자들을 처음 것으로 되돌립니다
    }
}
