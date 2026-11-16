package com.example.cleanlauncher

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// E2E 테스트. 에뮬레이터에서 앱을 실제로 띄우고, 사람이 하듯 입력하고 화면을 확인합니다.
// 모든 모듈(화면, 규칙, 데이터)이 이어져 도는지를 한 번에 봅니다
@RunWith(AndroidJUnit4::class)
class HomeE2ETest {

    // 알림 권한을 미리 허락해 둡니다. 권한을 묻는 창이 화면을 가리면 테스트가 입력하지 못합니다
    @get:Rule(order = 0)
    val permission: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.POST_NOTIFICATIONS)

    @get:Rule(order = 1)
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun searchShowsOnlyMatchingApps() {
        // 1. 앱 목록이 뜰 때까지 기다립니다 (에뮬레이터 언어가 영어이고 Calendar, Settings 앱이 있다고 가정합니다)
        rule.waitUntil(timeoutMillis = 10_000) {
            rule.onAllNodesWithText("Calendar").fetchSemanticsNodes().isNotEmpty()
        }
        // 2. 검색창에 set 을 넣습니다
        rule.onNodeWithTag("search").performTextInput("set")
        // 3. Settings 는 보이고 Calendar 는 사라져야 합니다
        rule.onNodeWithText("Settings").assertIsDisplayed()
        rule.onAllNodesWithText("Calendar").assertCountEquals(0)
    }
}
