package com.example.tipcalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import org.junit.Rule
import org.junit.Test

class TipCalculatorUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip_without_roundup() {
        composeTestRule.setContent {
            TipCalculatorTheme() {
                TipCalculatorScreen()
            }
        }
        composeTestRule.onNodeWithText("Enter Bill").performTextInput("150")
        composeTestRule.onNodeWithText("Enter Tip Percent").performTextInput("20")
        composeTestRule.onNodeWithText("Tip Amount is 30.0").assertExists()
    }
}