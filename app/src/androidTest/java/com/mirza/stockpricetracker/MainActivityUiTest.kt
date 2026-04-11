package com.mirza.stockpricetracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun launchShowsLiveQuotesScreenWithTitleAndDisconnectedEmptyState() {
        composeRule.onNodeWithText(context.getString(R.string.live_quotes_title)).assertIsDisplayed()
        composeRule
            .onNodeWithText(context.getString(R.string.empty_state_headline_disconnected))
            .assertIsDisplayed()
    }
}
