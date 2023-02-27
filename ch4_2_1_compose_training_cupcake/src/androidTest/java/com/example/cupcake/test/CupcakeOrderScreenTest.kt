package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.theme.CupcakeTheme
import org.junit.Rule
import org.junit.Test
import com.example.cupcake.R

class CupcakeOrderScreenTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        val flavours = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subTotal = "$100"

        composeTestRule.setContent {
            CupcakeTheme {
                SelectOptionScreen(subtotal = subTotal, options = flavours)
            }
        }

        // 맛이 제대로 표시되는지 확인
        flavours.forEach { flavour ->
            composeTestRule.onNodeWithText(flavour).assertIsDisplayed()
        }

        // 총액이 제대로 표시되는지 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, subTotal)
        ).assertIsDisplayed()

        // next 버튼이 비활성화 되어 있는지 확인
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }
}