package com.example.minilauncher

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels

private const val TAG = "Lifecycle"

// ViewModel 을 쓰는 계산기. 화면을 돌려도 값이 남습니다.
// ViewModel 을 안 쓰는 쪽은 MainActivity2 입니다 (앱 목록의 "계산기 ViewModel 없음" 아이콘)
class MainActivity : ComponentActivity() {

    // 값은 Activity 밖의 ViewModel 이 들고 있습니다. 화면을 돌려 Activity 가 새로 만들어져도 같은 ViewModel 을 다시 받습니다
    private val vm: CalculatorViewModel by viewModels()

    private lateinit var textDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (복원 데이터 ${if (savedInstanceState == null) "없음" else "있음"})")
        setContentView(R.layout.activity_main)      // res/layout/activity_main.xml 을 화면으로 씁니다

        findViewById<TextView>(R.id.textLabel).text = "ViewModel 씀"
        textDisplay = findViewById(R.id.textDisplay)

        // 글자판의 단추마다 "눌리면 할 일"을 붙입니다. 단추에 적힌 글자가 눌린 키입니다
        val keypad = findViewById<GridLayout>(R.id.keypad)
        for (i in 0 until keypad.childCount) {
            val button = keypad.getChildAt(i) as Button
            button.setOnClickListener {
                vm.press(button.text.toString())
                textDisplay.text = vm.text
            }
        }

        // 화면이 새로 만들어질 때마다 ViewModel 이 들고 있는 값을 표시창에 그립니다
        textDisplay.text = vm.text
    }

    // 아래 함수들은 내가 부르는 것이 아니라, 화면 상태가 바뀔 때마다 시스템이 불러 줍니다.
    // 열 때는 onCreate, onStart, onResume 순서로, 닫을 때는 onPause, onStop, onDestroy 순서로 찍힙니다

    // 화면이 보이기 시작할 때. 앱을 처음 열 때, 홈 화면에 나갔다가 돌아올 때 찍힙니다
    override fun onStart() {
        super.onStart(); Log.d(TAG, "onStart")
    }

    // 터치를 받을 수 있게 될 때. onStart 바로 뒤에 찍힙니다
    override fun onResume() {
        super.onResume(); Log.d(TAG, "onResume")
    }

    // 화면이 앞자리에서 물러나기 시작할 때. 홈 버튼, 뒤로 가기, 화면 돌리기 모두 여기부터 찍힙니다
    override fun onPause() {
        Log.d(TAG, "onPause"); super.onPause()
    }

    // 화면이 완전히 안 보이게 됐을 때. 홈 버튼으로 나가면 여기까지만 찍히고 화면은 살아 있습니다
    override fun onStop() {
        Log.d(TAG, "onStop"); super.onStop()
    }

    // 화면(Activity)이 없어질 때. 이때 Activity 의 변수도 함께 사라집니다 (MainActivity2 의 text 가 그렇습니다).
    // 화면을 돌려서 없어지는 것이면 true (곧 onCreate 가 다시 찍히고 ViewModel 은 남습니다),
    // 뒤로 가기로 닫는 것이면 false (ViewModel 도 같이 없어집니다)
    override fun onDestroy() {
        Log.d(TAG, "onDestroy (회전 때문이면 true: $isChangingConfigurations)"); super.onDestroy()
    }
}
