package com.example.minilauncher.practice

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.minilauncher.R
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 같이 해 보기 파일입니다. 완성본은 같은 폴더의 XmlVsCompose.kt 입니다.
// 한 덩어리를 붙일 때마다 Run 하고, 앱 목록에서 "XML과 Compose 비교 직접" 아이콘을 눌러 +1 을 눌러 봅니다.
// Logcat 검색창에는 tag:MyCompare 를 넣습니다. 필요한 import 는 위에 다 있습니다.
// 화면 모양은 완성본과 같은 res/layout/compare_counter.xml 을 씁니다 ("값만 +1" 버튼은 여기서는 안 씁니다).
//
// [1] XML 쪽: 값만 올리기. 위쪽 setOnClickListener 의 { } 안에 붙입니다
//         count++
//         Log.d(TAG, "XML: 값 $count")
//     Run 해서 위쪽 +1 을 누릅니다. Logcat 의 값은 오르는데 화면은 0 그대로입니다
//
// [2] XML 쪽: 글자를 직접 고치기. [1] 바로 아래에 붙입니다
//         xmlCount.text = "$count"
//     Run 해서 누릅니다. 이제 화면도 바뀝니다. 값을 바꿀 때마다 이 줄을 같이 적어야 합니다
//
// [3] Compose 쪽: 상태 만들기. MyComposeHalf 의 첫 줄에 붙이고, 숫자 글자의 "0" 을 "$count" 로 바꿉니다
//         var count by remember { mutableIntStateOf(0) }
//         Log.d(TAG, "Compose: 함수가 다시 실행됨. count = $count")
//
// [4] Compose 쪽: 버튼이 값만 바꾸기. 아래쪽 Button 의 onClick = { } 안에 붙입니다
//         count++
//     Run 해서 아래쪽 +1 을 누릅니다. 글자를 고치는 줄을 안 적었는데 화면이 바뀝니다
//
// 다 했으면: 위아래에 "처음으로" 버튼을 하나씩 더 만들어 봅니다. XML 쪽은 몇 군데를 고쳐야 하는지 세어 봅니다
private const val TAG = "MyCompare"

class MyXmlVsComposeActivity : ComponentActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compare_counter)
        val xmlCount = findViewById<TextView>(R.id.xmlCount)

        findViewById<Button>(R.id.xmlPlus).setOnClickListener {
            // [1], [2] 를 여기에 붙입니다
        }

        findViewById<ComposeView>(R.id.composeHalf).setContent {
            MiniLauncherTheme { MyComposeHalf() }
        }
    }
}

@Composable
fun MyComposeHalf() {
    // [3] 을 여기에 붙입니다

    Column {
        Text("Compose 방식", fontSize = 16.sp, color = Color(0xFF666666))
        Text("0", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111111))
        Button(onClick = { /* [4] 를 여기에 붙입니다 */ }) { Text("+1") }
    }
}
