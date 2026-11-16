package com.example.minilauncher.practice

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.minilauncher.R

// 직접 만들어 보는 파일입니다. 단계별 예제는 같은 폴더의 XmlPracticeActivity.kt 입니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "XML 연습 직접" 아이콘을 누릅니다.
// 화면 모양은 res/layout/xml_my.xml 에 만들고, 버튼이 눌렸을 때 할 일은 아래 onCreate 안에 적습니다.
// 완성 화면과 요구사항은 교안에 있습니다. 정답은 XmlPractice_MyAnswer.kt 와 res/layout/xml_my_answer.xml 입니다.
// TextView, Button 은 위에 import 해 두었습니다. 회색으로 보이면 아직 안 써서 그런 것입니다.
class MyXmlPracticeActivity : ComponentActivity() {

    private var count = 0      // 좋아요 수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xml_my)
        // 여기에 씁니다. XmlPracticeActivity.kt 의 3단계(step3Button)를 보고 만듭니다
        //   1. xml_my.xml 에 붙인 id 로 글자와 버튼을 찾습니다 (findViewById)
        //   2. 좋아요 버튼이 눌리면 count 를 1 올리고 글자를 바꿉니다
        //   3. 처음으로 버튼이 눌리면 count 를 0 으로 하고 글자를 바꿉니다
    }
}
