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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.minilauncher.R
import com.example.minilauncher.ui.theme.MiniLauncherTheme

// 1일차 5교시: XML 과 Compose 비교. 같은 카운터를 한 화면의 위(XML)와 아래(Compose)에 나란히 놓았습니다.
// Run 한 뒤 에뮬레이터의 앱 목록에서 "XML과 Compose 비교" 아이콘을 누릅니다.
// Logcat 검색창에 tag:Compare 를 넣고, 버튼을 누를 때마다 어떤 줄이 찍히는지 봅니다.
//
// 볼 것은 둘입니다.
//   값 관리      XML 은 값(count)과 화면의 글자가 따로 놉니다. 값을 바꾼 뒤 글자도 내가 고쳐 써야 합니다.
//               Compose 는 값만 바꾸면 글자가 따라 바뀝니다.
//   화면 만들기   XML 은 글자와 버튼을 처음에 한 번 만들고, 그 뒤로는 만든 것을 찾아서 고칩니다.
//               Compose 는 값이 바뀔 때마다 함수를 다시 불러 그 값으로 다시 그립니다.
//
// 비유: XML 은 칠판입니다. 점수가 바뀌면 내가 지우개로 지우고 다시 적어야 합니다. 안 적으면 옛 점수가 그대로 남습니다.
//       Compose 는 전광판입니다. 점수만 바꾸면 화면이 따라 바뀝니다.
// C++ 로 치면: XML 은 label->setText(...) 를 내가 부르는 방식, Compose 는 값이 바뀌면 draw() 가 알아서 다시 불리는 방식
//
// ===== 보여 주기 =====
//   1. 위쪽 +1 을 누릅니다. Logcat: "XML: 값 1, 글자를 직접 고쳤습니다"
//   2. 위쪽 "값만 +1" 을 세 번 누릅니다. Logcat 의 값은 4 까지 오르는데 화면은 1 그대로입니다. 값과 화면이 어긋났습니다
//   3. 아래쪽 +1 을 누릅니다. Logcat: "Compose: 함수가 다시 실행됨. count = 1". 글자를 고치는 줄이 없는데 화면이 바뀝니다
//   4. Logcat 에서 "XML: 화면을 만들었습니다" 는 처음에 한 번뿐이고, "Compose: 함수가 ..." 는 누를 때마다 찍히는 것을 봅니다
//
// ===== 같이 해 보기 (한 덩어리씩 복사합니다) =====
//   같은 폴더의 XmlVsCompose_My.kt 를 엽니다. 아래 [1]~[4] 표시가 붙은 줄을 차례로 가져갑니다
private const val TAG = "Compare"

class XmlVsComposeActivity : ComponentActivity() {

    // XML 쪽의 값. 그냥 변수입니다. 이 값이 바뀌어도 화면은 모릅니다
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // XML 파일을 읽어 글자(TextView)와 버튼(Button)을 실제로 만듭니다. 이때 한 번만 만듭니다
        setContentView(R.layout.compare_counter)
        Log.d(TAG, "XML: 화면을 만들었습니다 (처음 한 번)")

        // ===== 위쪽: XML 방식 =====
        // 만들어진 글자를 이름표(id)로 찾아서 손에 쥐고 있습니다
        val xmlCount = findViewById<TextView>(R.id.xmlCount)

        findViewById<Button>(R.id.xmlPlus).setOnClickListener {
            count++                                                   // [1] 값을 바꾸고
            xmlCount.text = "$count"                                  // [2] 화면의 글자도 직접 고쳐 씁니다
            Log.d(TAG, "XML: 값 $count, 글자를 직접 고쳤습니다")
        }

        // 글자를 고치는 줄을 빼먹으면 어떻게 되는지 보는 버튼
        findViewById<Button>(R.id.xmlValueOnly).setOnClickListener {
            count++
            Log.d(TAG, "XML: 값 $count, 글자는 안 고쳤습니다. 화면은 ${xmlCount.text} 그대로")
        }

        // ===== 아래쪽: Compose 방식 =====
        // 비워 둔 자리에 ComposeHalf 함수가 그린 화면을 넣습니다
        findViewById<ComposeView>(R.id.composeHalf).setContent {
            MiniLauncherTheme { ComposeHalf() }
        }
    }
}

// Compose 가 화면을 만드는 순서
//   1. 구성    이 함수를 위에서 아래로 실행합니다. Text, Button 을 부르면 "여기 글자, 여기 버튼"이라는 화면 설계도가 만들어집니다
//   2. 배치    설계도를 보고 크기와 자리를 정합니다
//   3. 그리기   화면에 그립니다
// count 가 바뀌면 count 를 읽은 이 함수가 다시 실행됩니다(재구성). 설계도에서 달라진 곳(숫자 글자)만 다시 그립니다.
// Text() 는 TextView 같은 객체를 돌려주지 않습니다. 손에 쥘 것이 없어서 "찾아서 고치기"를 할 수 없고, 값으로만 화면을 바꿉니다
@Composable
fun ComposeHalf() {
    // Compose 쪽의 값. 화면이 지켜보는 값(상태)입니다. 이 값이 바뀌면 이 함수가 다시 불립니다
    var count by remember { mutableIntStateOf(0) }                    // [3]

    // 이 줄은 함수가 불릴 때마다 찍힙니다. 버튼을 누를 때마다 찍히는지 Logcat 에서 봅니다
    Log.d(TAG, "Compose: 함수가 다시 실행됨. count = $count")

    Column {
        Text("Compose 방식", fontSize = 16.sp, color = Color(0xFF666666))
        Text("$count", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111111))
        Button(onClick = { count++ }) { Text("+1") }                  // [4] 값만 바꿉니다. 글자를 고치는 줄이 없습니다
    }
    // 바꿔 보기: [3] 을 var count = 0 으로 바꾸면 빨간 줄이 생기지 않는데도 눌러도 화면이 안 바뀝니다.
    //           화면이 지켜보지 않는 보통 변수라서, 값이 바뀌어도 함수가 다시 불리지 않기 때문입니다 (Logcat 에도 안 찍힘)
}

@Preview(showBackground = true, widthDp = 412, heightDp = 260)
@Composable
private fun ComposeHalfPreview() {
    MiniLauncherTheme { ComposeHalf() }
}
