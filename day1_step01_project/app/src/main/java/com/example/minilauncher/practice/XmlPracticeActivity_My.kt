package com.example.minilauncher.practice

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minilauncher.R

// 같이 해 보기 파일입니다. 완성본은 같은 폴더의 XmlPracticeActivity.kt 입니다.
// 완성본의 1~6단계를 한 덩어리씩 직접 붙여 가며 만듭니다. 단계마다 파일이 둘입니다.
//   화면 모양   res/layout/xml_my_step1.xml ~ xml_my_step6.xml (먼저 합니다. 붙일 것은 그 파일 맨 위에 적혀 있습니다)
//   동작       이 파일의 myStep3 ~ myStep6 (1, 2단계는 화면 모양만 있어서 여기에 적을 것이 없습니다)
// 아래 MY_STEP 숫자를 바꾸고 Run 한 뒤, 에뮬레이터의 앱 목록에서 "XML 단계 직접" 아이콘을 누릅니다.
// 필요한 import 는 위에 다 있습니다. 회색으로 보이는 것은 아직 안 써서 그렇습니다.
// 화면 모양을 붙이기 전에 동작부터 붙이면 찾을 글자와 버튼이 없어서 앱이 꺼집니다. 꼭 XML 을 먼저 붙입니다.
// (화면을 처음부터 혼자 만들어 보는 것은 XmlPractice_My.kt 와 xml_my.xml 입니다. "XML 연습 직접" 아이콘)
private const val MY_STEP = 1

class MyXmlStepsActivity : ComponentActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyXml", "===== 내가 만든 ${MY_STEP}단계 =====")
        when (MY_STEP) {
            1 -> setContentView(R.layout.xml_my_step1)
            2 -> setContentView(R.layout.xml_my_step2)
            3 -> myStep3()
            4 -> myStep4()
            5 -> myStep5()
            6 -> myStep6()
            else -> Log.d("MyXml", "1부터 6까지의 숫자를 넣어 주세요")
        }
    }

    // 3단계. 버튼과 클릭 (xml_my_step3.xml 을 먼저 채웁니다)
    // [1] 글자를 이름표로 찾기
    //         val countText = findViewById<TextView>(R.id.countText)
    // [2] 버튼을 찾아서 눌렸을 때 할 일 적기. Run 해서 눌러 봅니다. Logcat(tag:MyXml)의 값은 오르는데 화면은 그대로입니다
    //         findViewById<Button>(R.id.plusButton).setOnClickListener {
    //             count++
    //             Log.d("MyXml", "눌림: $count")
    //         }
    // [3] 글자를 직접 고치는 줄. [2]의 count++ 바로 아래에 붙입니다. 이제 화면도 바뀝니다
    //             countText.text = "지금 $count"
    private fun myStep3() {
        setContentView(R.layout.xml_my_step3)
        // 여기에 붙입니다
    }

    // 4단계. 글자 입력 (xml_my_step4.xml 을 먼저 채웁니다)
    // [1] 입력 칸과 결과 글자를 찾기
    //         val nameInput = findViewById<EditText>(R.id.nameInput)
    //         val helloText = findViewById<TextView>(R.id.helloText)
    // [2] 버튼이 눌리면 입력된 글자를 읽어서 결과 글자를 고치기
    //         findViewById<Button>(R.id.helloButton).setOnClickListener {
    //             helloText.text = "안녕하세요, ${nameInput.text}"
    //         }
    private fun myStep4() {
        setContentView(R.layout.xml_my_step4)
        // 여기에 붙입니다
    }

    // 5단계. 코드로 줄 추가하기 (xml_my_step5.xml 을 먼저 채웁니다)
    // [1] 빈 상자를 찾고, 넣을 이름 목록 만들기
    //         val listLayout = findViewById<LinearLayout>(R.id.listLayout)
    //         val names = listOf("Settings", "Camera", "Chrome", "Maps")
    // [2] 이름마다 TextView 를 코드로 만들어 상자에 넣기
    //         for (name in names) {
    //             val row = TextView(this)
    //             row.text = name
    //             row.textSize = 20f
    //             row.setPadding(0, 24, 0, 24)
    //             listLayout.addView(row)
    //         }
    private fun myStep5() {
        setContentView(R.layout.xml_my_step5)
        // 여기에 붙입니다
    }

    // 6단계. 긴 목록 (xml_my_step6.xml 을 먼저 채웁니다)
    // [1] 이름 100개와 RecyclerView 찾기
    //         val names = List(100) { "앱 ${it + 1}" }
    //         val appList = findViewById<RecyclerView>(R.id.appList)
    // [2] 줄을 위에서 아래로 놓겠다고 알려 주기. 여기까지만 하고 Run 하면 아직 빈 화면입니다
    //         appList.layoutManager = LinearLayoutManager(this)
    // [3] 목록과 화면을 이어 주는 Adapter 달기. AppAdapter 는 XmlPracticeActivity.kt 맨 아래에 있는 것을 그대로 씁니다
    //         appList.adapter = AppAdapter(names)
    private fun myStep6() {
        setContentView(R.layout.xml_my_step6)
        // 여기에 붙입니다
    }
}
