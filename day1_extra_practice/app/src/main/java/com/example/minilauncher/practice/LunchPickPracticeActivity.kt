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
// 정답은 day1_extra_answer 폴더에 있습니다. 먼저 만들어 보고 엽니다
class LunchPickPracticeActivity : ComponentActivity() {

    // 지금까지 뽑은 메뉴. 둘째 화면에 넘겨줄 것이라 목록으로 모아 둡니다
    private val history = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // XML 파일을 이 화면의 모양으로 씁니다
        setContentView(R.layout.activity_lunch_practice)

        // 여기부터 직접 만듭니다. 요구사항은 교안의 1일차 8교시 장에 있습니다.
        // 할 일: 뽑기 버튼, 기록 보기 버튼(둘째 화면에 history 를 넘김), 처음부터 버튼
        // 막히면 README.md 의 "막히면 보는 힌트"를 엽니다
    }
}
