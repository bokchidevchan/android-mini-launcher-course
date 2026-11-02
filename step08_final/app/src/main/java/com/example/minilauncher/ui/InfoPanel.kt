package com.example.minilauncher.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 가로 화면 왼쪽 정보 패널. 차량 화면처럼 넓은 화면을 나눠 쓰는 연습입니다
@Composable
fun InfoPanel(status: StatusInfo, sync: Int?) {
    Column(
        Modifier
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            .width(296.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(status.clock, fontSize = 44.sp, fontWeight = FontWeight.Bold)
        Text(
            SimpleDateFormat("M월 d일 EEEE", Locale.KOREA).format(Date()),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        InfoRow("배터리", status.battery?.let { "${it.level}%" + if (it.charging) " 충전 중" else "" } ?: "--")
        InfoRow("네트워크", status.network)
        InfoRow("동기화", when { sync == null -> "대기"; sync >= 100 -> "완료"; else -> "$sync%" })
        InfoRow("부팅 수신", status.boot ?: "아직 없음")
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.weight(1f))
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}
