package com.mirza.stockpricetracker.designsystem.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.mirza.stockpricetracker.R
import com.mirza.stockpricetracker.designsystem.theme.StockPriceTrackerTheme
import com.mirza.stockpricetracker.domain.model.SessionLinkState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TickerHeaderBarUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun showsTitleAndIdleSessionWhenDisconnected() {
        composeRule.setContent {
            StockPriceTrackerTheme(darkTheme = false, dynamicColor = false) {
                TickerHeaderBar(
                    title = context.getString(R.string.live_quotes_title),
                    session = SessionLinkState.Disconnected,
                    isBeginEnabled = true,
                    isEndEnabled = false,
                    isDarkTheme = false,
                    onBeginClick = {},
                    onEndClick = {},
                    onThemeToggle = {}
                )
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.live_quotes_title)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.session_status_idle)).assertIsDisplayed()
    }

    @Test
    fun beginAndEndButtonsInvokeCallbacks() {
        val clicks = mutableListOf<String>()

        composeRule.setContent {
            StockPriceTrackerTheme(darkTheme = false, dynamicColor = false) {
                TickerHeaderBar(
                    title = "Test",
                    session = SessionLinkState.Connected,
                    isBeginEnabled = false,
                    isEndEnabled = true,
                    isDarkTheme = false,
                    onBeginClick = { clicks += "begin" },
                    onEndClick = { clicks += "end" },
                    onThemeToggle = { clicks += "theme" }
                )
            }
        }

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.action_end_stream))
            .performClick()
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.cd_toggle_theme_dark))
            .performClick()

        composeRule.waitForIdle()
        assertEquals(listOf("end", "theme"), clicks)
    }
}
