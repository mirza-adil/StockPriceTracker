package com.mirza.stockpricetracker.presentation.screens.stockdetail

import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mirza.stockpricetracker.designsystem.theme.StockPriceTrackerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockDetailScreenUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsSymbolAndInvokesBackOnButtonClick() {
        var backClicked = false

        composeRule.setContent {
            StockPriceTrackerTheme(darkTheme = false, dynamicColor = false) {
                Surface {
                    StockDetailScreen(
                        symbol = "MSFT",
                        onBack = { backClicked = true }
                    )
                }
            }
        }

        composeRule.onNodeWithText("Stock Detail").assertIsDisplayed()
        composeRule.onNodeWithText("MSFT").assertIsDisplayed()
        composeRule.onNodeWithText("Back").performClick()

        composeRule.waitForIdle()
        assertTrue(backClicked)
    }
}
