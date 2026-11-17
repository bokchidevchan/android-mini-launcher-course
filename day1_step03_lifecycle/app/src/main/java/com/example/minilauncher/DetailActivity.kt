package com.example.minilauncher

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

// 계산기 화면이 보낸 값을 받아 보여 주는 화면
class DetailActivity : ComponentActivity() {
    companion object {
        const val EXTRA_RESULT = "result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 보낸 쪽의 putExtra 와 같은 키로 꺼냅니다
        val result = intent.getStringExtra(EXTRA_RESULT) ?: "받은 값 없음"
        Log.d("Lifecycle", "DetailActivity onCreate: $result")
        setContentView(R.layout.activity_detail)

        findViewById<TextView>(R.id.textResult).text = result
        findViewById<Button>(R.id.buttonBack).setOnClickListener { finish() }      // 이 화면을 닫습니다
    }
}
