package com.example.minilauncher.ui

import android.app.role.RoleManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HomeGreen = Color(0xFF2E7D32)

// 지금 기본 홈 앱이 이 앱인지 시스템에 묻기: "내가 홈(HOME) 역할을 맡고 있나?"
fun isMyAppDefaultHome(context: Context): Boolean {
    if (Build.VERSION.SDK_INT < 29) return false                 // RoleManager 는 Android 10 부터
    val roles = context.getSystemService(RoleManager::class.java)
    return roles.isRoleHeld(RoleManager.ROLE_HOME)
}

// 홈 앱이면 화면 가장자리에 초록 테두리와 "홈 앱으로 쓰는 중" 글자를 덧씌움
@Composable
fun HomeFrame(isHome: Boolean, content: @Composable () -> Unit) {
    val edge = if (isHome) Modifier.border(6.dp, HomeGreen) else Modifier
    Box(Modifier.fillMaxSize().then(edge)) {
        content()
        if (isHome) {
            Text(
                "홈 앱으로 쓰는 중",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
                    .background(HomeGreen, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
            )
        }
    }
}
