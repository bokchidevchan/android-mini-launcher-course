package com.example.minilauncher.practice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun CommonBtn(btnText : String) {

    // count =

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(text = "공통버튼")
        Button(onClick = {

        }) {
            Text(btnText)
        }
    }



}