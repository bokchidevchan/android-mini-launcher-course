package com.example.minilauncher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 상태 영역 아래 버튼 줄 (Figma "버튼 줄")
@Composable
fun ButtonRow(vararg buttons: Pair<String, () -> Unit>) {
    Row(
        Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        buttons.forEach { (label, onClick) ->
            FilledTonalButton(onClick = onClick) { Text(label) }
        }
    }
}
