package com.example.minilauncher.practice

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.minilauncher.R

// 1일차 XML 화면 실습 5단계.
// 아래 STEP 숫자를 1부터 5까지 바꿔 가며 Run 하고, 에뮬레이터의 앱 목록에서 "XML 연습" 아이콘을 누릅니다.
// 화면 모양은 res/layout/xml_step1.xml ~ xml_step5.xml 에 있습니다. 파일을 열고 오른쪽 위 Split 을 누르면 미리 보기가 나옵니다.
// 직접 만들어 보는 것은 같은 폴더의 XmlPractice_My.kt 와 res/layout/xml_my.xml 에 합니다 ("XML 연습 직접" 아이콘).
// XML 방식은 화면 모양을 XML 파일에, 동작을 Kotlin 파일에 나눠 적습니다. 오후에 배우는 Compose 는 둘을 Kotlin 한곳에 적습니다
private const val STEP = 1

class XmlPracticeActivity : ComponentActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val step = intent.getIntExtra("step", STEP)
        Log.d("Xml", "===== ${step}단계 =====")
        when (step) {
            1 -> setContentView(R.layout.xml_step1)      // 글자 하나. XML 파일을 이 화면의 모양으로 씁니다
            2 -> setContentView(R.layout.xml_step2)      // 세로와 가로로 놓기
            3 -> step3Button()
            4 -> step4Input()
            5 -> step5AddViews()
            else -> Log.d("Xml", "1부터 5까지의 숫자를 넣어 주세요")
        }
    }

    // 3단계. 버튼과 클릭
    // findViewById 로 XML 의 이름표(id)를 찾고, 눌렸을 때 할 일을 적습니다. 값이 바뀌면 글자도 직접 바꿔 줍니다
    private fun step3Button() {
        setContentView(R.layout.xml_step3)
        val countText = findViewById<TextView>(R.id.countText)
        findViewById<Button>(R.id.plusButton).setOnClickListener {
            count++
            countText.text = "지금 $count"
            Log.d("Xml", "눌림: $count")
        }
        // 바꿔 보기: count++ 를 count += 10 으로 바꿔 봅니다. countText.text 줄을 지우면 눌러도 화면이 안 바뀝니다
    }

    // 4단계. 글자 입력
    // EditText 에 적힌 글자는 nameInput.text 로 읽습니다
    private fun step4Input() {
        setContentView(R.layout.xml_step4)
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val helloText = findViewById<TextView>(R.id.helloText)
        findViewById<Button>(R.id.helloButton).setOnClickListener {
            helloText.text = "안녕하세요, ${nameInput.text}"
            Log.d("Xml", "입력: ${nameInput.text}")
        }
    }

    // 5단계. 코드로 줄 추가하기
    // TextView 를 코드로 만들어 빈 상자에 넣습니다. 목록의 개수만큼 줄이 생깁니다
    private fun step5AddViews() {
        setContentView(R.layout.xml_step5)
        val listLayout = findViewById<LinearLayout>(R.id.listLayout)
        val names = listOf("Settings", "Camera", "Chrome", "Maps")
        for (name in names) {
            val row = TextView(this)
            row.text = name
            row.textSize = 20f
            row.setPadding(0, 24, 0, 24)
            listLayout.addView(row)
        }
        Log.d("Xml", "줄 ${names.size}개를 넣었습니다")
    }
}
