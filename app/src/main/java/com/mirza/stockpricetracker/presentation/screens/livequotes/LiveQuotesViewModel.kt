package com.mirza.stockpricetracker.presentation.screens.livequotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirza.stockpricetracker.R
import com.mirza.stockpricetracker.core.logging.AppLogger
import com.mirza.stockpricetracker.domain.model.MarketQuote
import com.mirza.stockpricetracker.domain.model.SessionLinkState
import com.mirza.stockpricetracker.domain.usecase.ManageSessionLinkUseCase
import com.mirza.stockpricetracker.domain.usecase.ObserveQuoteStreamUseCase
import com.mirza.stockpricetracker.domain.usecase.SortQuotesByPriceUseCase
import com.mirza.stockpricetracker.presentation.screens.livequotes.mapper.MarketQuoteToRowUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LiveQuotesViewModel(
    private val observeQuoteStream: ObserveQuoteStreamUseCase,
    private val manageSessionLink: ManageSessionLinkUseCase,
    private val sortQuotesByPrice: SortQuotesByPriceUseCase,
    private val mapper: MarketQuoteToRowUiMapper,
    private val appLogger: AppLogger
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiveQuotesUiState())
    val uiState: StateFlow<LiveQuotesUiState> = _uiState.asStateFlow()

    private val _effects = Channel<LiveQuotesEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    private val quotesBySymbol = linkedMapOf<String, MarketQuote>()
    private var quoteJob: Job? = null

    init {
        viewModelScope.launch {
            manageSessionLink.sessionLink().collect { link ->
                _uiState.update {
                    it.copy(
                        session = link,
                        faultMessage = (link as? SessionLinkState.Fault)?.message
                    )
                }
                if (link is SessionLinkState.Disconnected) {
                    quoteJob?.cancel()
                    quoteJob = null
                    quotesBySymbol.clear()
                    _uiState.update { it.copy(rows = emptyList()) }
                }
            }
        }
    }

    fun handleIntent(intent: LiveQuotesIntent) {
        when (intent) {
            LiveQuotesIntent.BeginStream -> beginStream()
            LiveQuotesIntent.EndStream -> endStream()
        }
    }

    private fun beginStream() {
        viewModelScope.launch {
            _uiState.update { it.copy(isBusy = true, faultMessage = null) }
            runCatching { manageSessionLink.beginStreaming() }
                .onSuccess {
                    appLogger.d(TAG, "Stream begin acknowledged")
                    _effects.send(LiveQuotesEffect.NotifySuccess(R.string.live_quotes_notify_stream_ready))
                    startObservingTicks()
                }
                .onFailure { e ->
                    appLogger.e(TAG, "Failed to begin stream", e)
                    _effects.send(LiveQuotesEffect.NotifyError(R.string.live_quotes_notify_stream_failed))
                }
            _uiState.update { it.copy(isBusy = false) }
        }
    }

    private fun endStream() {
        viewModelScope.launch {
            _uiState.update { it.copy(isBusy = true) }
            runCatching { manageSessionLink.endStreaming() }
                .onSuccess {
                    _effects.send(LiveQuotesEffect.NotifyInfo(R.string.live_quotes_notify_stream_stopped))
                }
                .onFailure { e ->
                    appLogger.e(TAG, "Failed to end stream", e)
                    _effects.send(LiveQuotesEffect.NotifyError(R.string.live_quotes_notify_stream_failed))
                }
            _uiState.update { it.copy(isBusy = false) }
        }
    }

    private fun startObservingTicks() {
        quoteJob?.cancel()
        quoteJob = viewModelScope.launch {
            observeQuoteStream().collect { quote ->
                quotesBySymbol[quote.symbol] = quote
                val sorted = sortQuotesByPrice(quotesBySymbol.values.toList())
                val rows = sorted.map { mapper.toRowUi(it) }
                _uiState.update { it.copy(rows = rows) }
            }
        }
    }

    override fun onCleared() {
        quoteJob?.cancel()
        runBlocking(Dispatchers.IO) {
            runCatching { manageSessionLink.endStreaming() }
        }
        super.onCleared()
    }

    private companion object {
        const val TAG = "LiveQuotesViewModel"
    }
}
