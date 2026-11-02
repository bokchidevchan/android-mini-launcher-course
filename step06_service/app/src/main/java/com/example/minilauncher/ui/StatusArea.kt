package com.example.minilauncher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AirplanemodeActive
import androidx.compose.material.icons.outlined.Battery1Bar
import androidx.compose.material.icons.outlined.BatteryChargingFull
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
import com.example.minilauncher.system.BatteryInfo
import com.example.minilauncher.system.OFFLINE

data class StatusInfo(
    val clock: String,
    val network: String,
    val battery: BatteryInfo?,
    val boot: String? = null,
)

// Figma "상태 영역 경우별": 배터리 20% 이하면 빨간 battery_1_bar, 충전 중이면 번개, 오프라인이면 비행기 아이콘
@Composable
fun StatusArea(status: StatusInfo, modifier: Modifier = Modifier, extra: @Composable () -> Unit = {}) {
    val offline = status.network == OFFLINE
    val low = (status.battery?.level ?: 100) <= 20
    val sub = MaterialTheme.colorScheme.onSurfaceVariant
    val batteryColor = if (low) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground

    Row(
        modifier = modifier.fillMaxWidth().height(40.dp).padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(status.clock, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        status.boot?.let { Text("부팅 $it", style = MaterialTheme.typography.labelMedium, color = sub) }
        Spacer(Modifier.weight(1f))
        extra()
        if (offline) {
            Icon(Icons.Outlined.AirplanemodeActive, contentDescription = null, tint = sub, modifier = Modifier.size(16.dp))
        } else {
            Icon(painterResource(R.drawable.ic_network), contentDescription = null, modifier = Modifier.size(16.dp))
        }
        Text(status.network, style = MaterialTheme.typography.labelMedium, color = if (offline) sub else MaterialTheme.colorScheme.onBackground)

        val battery = status.battery
        if (battery?.charging == true) {
            Icon(Icons.Outlined.BatteryChargingFull, contentDescription = null, tint = batteryColor, modifier = Modifier.size(16.dp))
        } else if (low) {
            Icon(Icons.Outlined.Battery1Bar, contentDescription = null, tint = batteryColor, modifier = Modifier.size(16.dp))
        } else {
            Icon(painterResource(R.drawable.ic_battery), contentDescription = null, tint = batteryColor, modifier = Modifier.size(16.dp))
        }
        Text(
            if (battery == null) "--%" else "${battery.level}%" + if (battery.charging) " 충전 중" else "",
            style = MaterialTheme.typography.labelMedium,
            color = batteryColor,
        )
    }
}
