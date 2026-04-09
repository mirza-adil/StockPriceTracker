package com.mirza.stockpricetracker.data.datasource

import com.mirza.stockpricetracker.domain.model.MarketQuote
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SimulatedQuoteStreamDataSource {

    private val random = Random.Default
    private val snapshots = mutableListOf(
        QuoteSnapshot("AAPL", "Apple Inc.", 178.42),
        QuoteSnapshot("MSFT", "Microsoft Corp.", 378.91),
        QuoteSnapshot("GOOGL", "Alphabet Inc.", 141.80),
        QuoteSnapshot("AMZN", "Amazon.com Inc.", 178.25),
        QuoteSnapshot("META", "Meta Platforms Inc.", 505.30)
    )

    fun ticks(): Flow<MarketQuote> = flow {
        while (true) {
            delay(TICK_MS)
            val index = random.nextInt(snapshots.size)
            val snap = snapshots[index]
            val delta = (random.nextDouble() - 0.5) * 3.5
            val next = (snap.last + delta).coerceIn(5.0, 999.99)
            val prior = snap.last
            snapshots[index] = snap.copy(last = next)
            emit(
                MarketQuote(
                    symbol = snap.symbol,
                    displayName = snap.label,
                    price = next,
                    priorPrice = prior
                )
            )
        }
    }

    private data class QuoteSnapshot(
        val symbol: String,
        val label: String,
        val last: Double
    )

    private companion object {
        const val TICK_MS = 700L
    }
}
