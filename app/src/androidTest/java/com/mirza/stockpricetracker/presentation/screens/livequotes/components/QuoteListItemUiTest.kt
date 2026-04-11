package com.mirza.stockpricetracker.presentation.screens.livequotes.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.mirza.stockpricetracker.designsystem.theme.StockPriceTrackerTheme
import com.mirza.stockpricetracker.domain.model.QuoteTrend
import com.mirza.stockpricetracker.presentation.screens.livequotes.model.QuoteRowUiModel
import org.junit.Rule
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuoteListItemUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun displaysMergedQuoteSummaryInSemantics() {
        val row = QuoteRowUiModel(
            symbol = "AAPL",
            displayName = "Apple Inc.",
            priceText = "$150.00",
            priceValue = 150.0,
            changeText = "+$1.00",
            changePercentText = "+0.67%",
            trend = QuoteTrend.Up
        )
        val expectedSummary =
            "${row.symbol}, ${row.displayName}, ${row.priceText}, ${row.changeText} ${row.changePercentText}"

        composeRule.setContent {
            StockPriceTrackerTheme(darkTheme = false, dynamicColor = false) {
                QuoteListItem(row = row, showDivider = false)
            }
        }

        composeRule
            .onNodeWithContentDescription(expectedSummary)
            .assertIsDisplayed()
    }
}
