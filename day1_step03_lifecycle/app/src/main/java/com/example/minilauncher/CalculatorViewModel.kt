package com.example.minilauncher

import android.util.Log
import androidx.lifecycle.ViewModel

// 화면을 돌리면 Activity 는 새로 만들어지지만 ViewModel 은 그대로 남습니다
class CalculatorViewModel : ViewModel() {
    init {
        Log.d("Lifecycle", "CalculatorViewModel 생성")
    }

    var text = "0"
        private set

    fun press(key: String) {
        text = pressKey(text, key)
    }

    // 화면을 정말로 닫을 때(뒤로 가기) 한 번 불립니다. 돌릴 때는 안 불립니다
    override fun onCleared() {
        Log.d("Lifecycle", "CalculatorViewModel onCleared")
    }
}

// 계산기 글자판. 지금 보이는 글자(text)와 눌린 키(key)를 받아, 다음에 보일 글자를 돌려줍니다
fun pressKey(text: String, key: String): String = when {
    key == "C" -> "0"
    key == "=" -> calculate(text)
    key in "+-×" -> text.trimEnd('+', '-', '×') + key       // 기호를 연달아 누르면 마지막 것으로 바꿉니다
    text.length >= 12 -> text                                // 너무 길면 더 받지 않습니다
    text == "0" -> key
    else -> text + key
}

// "12+3×2" 를 왼쪽부터 차례로 계산합니다 (곱하기를 먼저 하지 않는 단순한 계산기입니다)
private fun calculate(text: String): String {
    var total = 0L
    var number = ""
    var sign = '+'
    for (c in text.trimEnd('+', '-', '×') + "+") {           // 끝에 + 를 붙여 마지막 숫자까지 계산되게 합니다
        if (c.isDigit()) { number += c; continue }
        if (number.isNotEmpty()) {
            val n = number.toLong()
            total = when (sign) { '+' -> total + n; '-' -> total - n; else -> total * n }
            number = ""
        }
        sign = c
    }
    return total.toString()
}
