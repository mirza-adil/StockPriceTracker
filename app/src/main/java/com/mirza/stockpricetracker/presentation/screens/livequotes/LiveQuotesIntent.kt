package com.mirza.stockpricetracker.presentation.screens.livequotes

sealed interface LiveQuotesIntent {
    data object BeginStream : LiveQuotesIntent
    data object EndStream : LiveQuotesIntent
}
