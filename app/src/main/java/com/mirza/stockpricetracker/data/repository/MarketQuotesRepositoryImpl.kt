package com.mirza.stockpricetracker.data.repository

import com.mirza.stockpricetracker.core.logging.AppLogger
import com.mirza.stockpricetracker.data.datasource.SimulatedQuoteStreamDataSource
import com.mirza.stockpricetracker.domain.model.MarketQuote
import com.mirza.stockpricetracker.domain.model.SessionLinkState
import com.mirza.stockpricetracker.domain.repository.MarketQuotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class  MarketQuotesRepositoryImpl(
    private val dataSource: SimulatedQuoteStreamDataSource,
    private val appLogger: AppLogger,
    private val ioDispatcher: CoroutineDispatcher
) : MarketQuotesRepository {

    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)
    private val mutex = Mutex()

    private val _sessionLink = MutableStateFlow<SessionLinkState>(SessionLinkState.Disconnected)
    override val sessionLink: StateFlow<SessionLinkState> = _sessionLink.asStateFlow()

    private val _quotes = MutableSharedFlow<MarketQuote>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var tickJob: Job? = null

    override fun quoteTicks(): Flow<MarketQuote> = _quotes.asSharedFlow()

    override suspend fun beginStreaming() = mutex.withLock {
        tickJob?.cancel()
        _sessionLink.value = SessionLinkState.Connecting
        delay(CONNECT_DELAY_MS)
        _sessionLink.value = SessionLinkState.Connected
        appLogger.d(TAG, "Simulated quote stream started")
        tickJob = scope.launch {
            dataSource.ticks().collect { quote ->
                _quotes.emit(quote)
            }
        }
    }

    override suspend fun endStreaming() = mutex.withLock {
        tickJob?.cancel()
        tickJob = null
        _sessionLink.value = SessionLinkState.Disconnected
        appLogger.d(TAG, "Simulated quote stream stopped")
    }

    private companion object {
        const val TAG = "MarketQuotesRepository"
        const val CONNECT_DELAY_MS = 320L
    }
}
