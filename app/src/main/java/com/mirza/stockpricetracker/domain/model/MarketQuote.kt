package com.mirza.stockpricetracker.domain.model

data class MarketQuote(
    val symbol: String,
    val displayName: String,
    val price: Double,
    val priorPrice: Double
)
