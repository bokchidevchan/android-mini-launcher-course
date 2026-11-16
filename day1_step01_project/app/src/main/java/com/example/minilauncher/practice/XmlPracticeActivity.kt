package com.example.minilauncher.practice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minilauncher.R

// 1일차 XML 화면 실습 5단계.
// 아래 STEP 숫자를 1부터 5까지 바꿔 가며 Run 하고 (6은 Compose 와 비교해 볼 긴 목록), 에뮬레이터의 앱 목록에서 "XML 연습" 아이콘을 누릅니다.
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
            6 -> step6RecyclerView()
            else -> Log.d("Xml", "1부터 6까지의 숫자를 넣어 주세요")
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

    // 6단계. 긴 목록 (RecyclerView)
    // 5단계처럼 줄을 전부 만들면 줄이 많을 때 느려집니다. RecyclerView 는 화면에 보이는 줄만 만들고 다시 씁니다.
    // 그 대신 적을 것이 많습니다. 아래 AppAdapter 와 AppHolder, 그리고 xml_item_app.xml 까지 필요합니다.
    // 오후의 ComposePractice.kt 9번(LazyColumn)이 같은 일을 몇 줄로 합니다. 두 코드의 길이를 비교해 봅니다
    private fun step6RecyclerView() {
        setContentView(R.layout.xml_step6)
        val names = List(100) { "앱 ${it + 1}" }
        val appList = findViewById<RecyclerView>(R.id.appList)
        appList.layoutManager = LinearLayoutManager(this)      // 줄을 위에서 아래로 놓겠다는 뜻
        appList.adapter = AppAdapter(names)
        Log.d("Xml", "줄 ${names.size}개를 RecyclerView 에 맡겼습니다")
        // 바꿔 보기: List(100) 을 List(1000) 으로 바꿔도 느려지지 않는지 밀어 봅니다
    }
}

// 줄 하나를 붙잡고 있는 상자. 그 줄 안의 글자 칸(appName)을 미리 찾아 둡니다
class AppHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.appName)
}

// 목록과 화면을 이어 주는 것. RecyclerView 가 "줄 하나 만들어 줘", "몇 번째 값을 넣어 줘", "모두 몇 개야" 하고 물으면 답합니다
class AppAdapter(private val names: List<String>) : RecyclerView.Adapter<AppHolder>() {
    // 줄 하나 만들기: xml_item_app.xml 을 화면 조각으로 바꿉니다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.xml_item_app, parent, false)
        return AppHolder(view)
    }

    // 몇 번째 값을 그 줄에 넣기
    override fun onBindViewHolder(holder: AppHolder, position: Int) {
        holder.name.text = names[position]
    }

    // 모두 몇 개인지
    override fun getItemCount() = names.size
}
