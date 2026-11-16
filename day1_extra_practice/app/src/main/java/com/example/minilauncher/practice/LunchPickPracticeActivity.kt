package com.example.minilauncher.practice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.minilauncher.R

// 1일차 8교시 첫 화면: 점심 뽑기. XML 로 만듭니다.
// 실행: 에뮬레이터의 앱 목록에서 "점심 뽑기 연습" 아이콘.
// 화면 모양은 res/layout/activity_lunch_practice.xml 에, 동작은 이 파일에 만듭니다.
// 메뉴 목록은 lunchMenus (LunchMenus.kt) 를 씁니다.
// 정답: LunchPickAnswerActivity.kt, res/layout/activity_lunch_answer.xml ("점심 뽑기 정답" 아이콘)
class LunchPickPracticeActivity : ComponentActivity() {

    // 지금까지 뽑은 메뉴. 둘째 화면에 넘겨줄 것이라 목록으로 모아 둡니다
    private val history = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // XML 파일을 이 화면의 모양으로 씁니다
        setContentView(R.layout.activity_lunch_practice)

        // 여기부터 직접 만듭니다. (TextView, Button, Intent 는 위에 import 해 두었습니다)
        // 1. XML 에 넣은 것을 id 로 찾습니다.      val menuText = findViewById<TextView>(R.id.menuText)
        // 2. 버튼이 눌리면 할 일을 적습니다.        findViewById<Button>(R.id.pickButton).setOnClickListener { ... }
        // 3. 목록에서 아무거나 하나 고르고 기록에 넣습니다.   val menu = lunchMenus.random();  history.add(menu)
        // 4. 화면의 글자를 바꿉니다.                menuText.text = menu,  countText.text = "${history.size}번째 뽑기"
        // 5. 기록 보기 버튼: 둘째 화면을 열면서 기록을 같이 넘깁니다.
        //      startActivity(Intent(this, LunchHistoryPracticeActivity::class.java).putStringArrayListExtra(EXTRA_HISTORY, history))
        // 6. 처음부터 버튼: history.clear() 를 하고 글자들을 처음 것으로 되돌립니다
    }
}
