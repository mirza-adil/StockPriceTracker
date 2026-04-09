package com.mirza.stockpricetracker.presentation.screens.livequotes

import com.mirza.stockpricetracker.domain.model.SessionLinkState
import com.mirza.stockpricetracker.presentation.screens.livequotes.model.QuoteRowUiModel

data class LiveQuotesUiState(
    val rows: List<QuoteRowUiModel> = emptyList(),
    val session: SessionLinkState = SessionLinkState.Disconnected,
    val isBusy: Boolean = false,
    val faultMessage: String? = null
) {
    val isBeginEnabled: Boolean
        get() =
            (session is SessionLinkState.Disconnected || session is SessionLinkState.Fault) && !isBusy

    val isEndEnabled: Boolean
        get() = session is SessionLinkState.Connected && !isBusy
}
