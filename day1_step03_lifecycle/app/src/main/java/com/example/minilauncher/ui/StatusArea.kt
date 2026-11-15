package com.example.minilauncher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minilauncher.R

// 런처 맨 위 상태 영역. 1일차는 고정 값, 2일차부터 실제 배터리와 네트워크 값으로 바꿉니다
@Composable
fun StatusArea(clock: String, network: String, battery: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().height(40.dp).padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(clock, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        // Figma 에서 SVG 로 내보내 Vector Asset 으로 넣은 아이콘
        Icon(painterResource(R.drawable.ic_network), contentDescription = "네트워크", modifier = Modifier.size(16.dp))
        Text(network, style = MaterialTheme.typography.labelMedium)
        Icon(painterResource(R.drawable.ic_battery), contentDescription = "배터리", modifier = Modifier.size(16.dp))
        Text(battery, style = MaterialTheme.typography.labelMedium)
    }
}
