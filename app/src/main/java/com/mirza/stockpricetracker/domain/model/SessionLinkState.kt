package com.mirza.stockpricetracker.domain.model

sealed interface SessionLinkState {
    data object Connected : SessionLinkState
    data object Disconnected : SessionLinkState
    data object Connecting : SessionLinkState
    data class Fault(val message: String) : SessionLinkState
}
