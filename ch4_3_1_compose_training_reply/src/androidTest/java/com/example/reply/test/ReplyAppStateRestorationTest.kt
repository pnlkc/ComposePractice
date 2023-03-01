package com.example.reply.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.reply.data.local.LocalEmailsDataProvider
import com.example.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppStateRestorationTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_selectedEmailEmailRetained_afterConfigChange() {
        // 구성 변경 테스트를 위한 StateRestorationTester()
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }

        // 3번째 이메일이 제대로 표시되는지(assertIsDisplayed()) 확인
        composeTestRule.onNodeWithText(LocalEmailsDataProvider.allEmails[2].body)
            .assertIsDisplayed()

        // 이메일 세부화면으로 이동하도록 클릭
        composeTestRule.onNodeWithText(LocalEmailsDataProvider.allEmails[2].subject).performClick()

        val thirdEmailContentTest: () -> Unit = {
            // 이메일 세부화면에서 뒤로가기 버튼이 제대로 표시되는지 확인
            composeTestRule.onNodeWithContentDescriptionForStringId(
                com.example.reply.R.string.navigation_back
            ).assertExists()

            // 3번째 이메일의 세부화면이 잘 나오는지 확인
            composeTestRule.onNodeWithText(LocalEmailsDataProvider.allEmails[2].body).assertExists()
        }

        thirdEmailContentTest()

        // 구성변경 발생시키는 코드
        stateRestorationTester.emulateSavedInstanceStateRestore()

        thirdEmailContentTest()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_selectedEmailEmailRetained_afterConfigChange() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent { ReplyApp(windowSize = WindowWidthSizeClass.Expanded) }

        // 3번째 이메일이 제대로 표시되는지(assertIsDisplayed()) 확인
        composeTestRule.onNodeWithText(LocalEmailsDataProvider.allEmails[2].body)
            .assertIsDisplayed()

        // 이메일 세부화면으로 이동하도록 클릭
        composeTestRule.onNodeWithText(LocalEmailsDataProvider.allEmails[2].subject).performClick()

        // 3번째 이메일의 세부화면이 잘 나오는지 확인 (compactDevice랑 방법이 다름!!)
        composeTestRule.onNodeWithTagForStringId(com.example.reply.R.string.details_screen).onChildren()
            .assertAny(hasAnyDescendant(hasText(LocalEmailsDataProvider.allEmails[2].body)))

        // 구성변경 발생 코드
        stateRestorationTester.emulateSavedInstanceStateRestore()

        // 3번째 이메일의 세부화면이 잘 나오는지 확인 (compactDevice랑 방법이 다름!!)
        composeTestRule.onNodeWithTagForStringId(com.example.reply.R.string.details_screen).onChildren()
            .assertAny(hasAnyDescendant(hasText(LocalEmailsDataProvider.allEmails[2].body)))
    }
}