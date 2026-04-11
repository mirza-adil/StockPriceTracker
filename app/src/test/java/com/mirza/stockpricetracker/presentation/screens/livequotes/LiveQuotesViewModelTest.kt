package com.mirza.stockpricetracker.presentation.screens.livequotes

import com.mirza.stockpricetracker.R
import com.mirza.stockpricetracker.core.logging.AppLogger
import com.mirza.stockpricetracker.domain.model.MarketQuote
import com.mirza.stockpricetracker.domain.model.QuoteTrend
import com.mirza.stockpricetracker.domain.model.SessionLinkState
import com.mirza.stockpricetracker.domain.usecase.ManageSessionLinkUseCase
import com.mirza.stockpricetracker.domain.usecase.ObserveQuoteStreamUseCase
import com.mirza.stockpricetracker.domain.usecase.SortQuotesByPriceUseCase
import com.mirza.stockpricetracker.presentation.screens.livequotes.mapper.MarketQuoteToRowUiMapper
import com.mirza.stockpricetracker.presentation.screens.livequotes.model.QuoteRowUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.isNull
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class LiveQuotesViewModelTest {

    @Mock
    lateinit var observeQuoteStream: ObserveQuoteStreamUseCase

    @Mock
    lateinit var manageSessionLink: ManageSessionLinkUseCase

    @Mock
    lateinit var sortQuotesByPrice: SortQuotesByPriceUseCase

    @Mock
    lateinit var mapper: MarketQuoteToRowUiMapper

    @Mock
    lateinit var appLogger: AppLogger

    private val sessionFlow = MutableStateFlow<SessionLinkState>(SessionLinkState.Disconnected)

    @BeforeEach
    fun setMainDispatcher() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        whenever(manageSessionLink.sessionLink()).thenReturn(sessionFlow)
    }

    @AfterEach
    fun resetMainDispatcher() {
        Dispatchers.resetMain()
    }

    private fun newViewModel() = LiveQuotesViewModel(
        observeQuoteStream = observeQuoteStream,
        manageSessionLink = manageSessionLink,
        sortQuotesByPrice = sortQuotesByPrice,
        mapper = mapper,
        appLogger = appLogger
    )

    private fun stubBeginStreamingReturns() = runBlocking {
        whenever(manageSessionLink.beginStreaming()).thenReturn(Unit)
    }

    private fun stubBeginStreamingThrows(t: Throwable) = runBlocking {
        whenever(manageSessionLink.beginStreaming()).thenThrow(t)
    }

    private fun stubEndStreamingReturns() = runBlocking {
        whenever(manageSessionLink.endStreaming()).thenReturn(Unit)
    }

    private fun stubEndStreamingThrows(t: Throwable) = runBlocking {
        whenever(manageSessionLink.endStreaming()).thenThrow(t)
    }

    @Test
    @DisplayName("session link Connected updates uiState.session; Fault sets faultMessage")
    fun sessionLink_whenConnected_thenUpdatesSession_andFaultSetsMessage() {
        val vm = newViewModel()
        assertEquals(SessionLinkState.Disconnected, vm.uiState.value.session)

        sessionFlow.value = SessionLinkState.Connected
        assertEquals(SessionLinkState.Connected, vm.uiState.value.session)

        sessionFlow.value = SessionLinkState.Fault("link down")
        assertInstanceOf(SessionLinkState.Fault::class.java, vm.uiState.value.session)
        assertEquals("link down", vm.uiState.value.faultMessage)
    }

    @Test
    @DisplayName("Disconnected after quotes clears rows and stops further row updates")
    fun sessionLink_whenDisconnectedAfterRows_thenClearsRows_andStopsCollection() {
        val quotes = MutableSharedFlow<MarketQuote>(extraBufferCapacity = 8)
        stubBeginStreamingReturns()
        whenever(observeQuoteStream.invoke()).thenReturn(quotes)
        val q1 = MarketQuote("AAA", "Aaa", 10.0, 10.0)
        val row1 = QuoteRowUiModel("AAA", "Aaa", "$10.00", 10.0, "$0.00", "0.00%", QuoteTrend.Flat)
        whenever(sortQuotesByPrice(any())).thenReturn(listOf(q1))
        whenever(mapper.toRowUi(q1)).thenReturn(row1)

        sessionFlow.value = SessionLinkState.Connected
        val vm = newViewModel()
        vm.handleIntent(LiveQuotesIntent.BeginStream)
        quotes.tryEmit(q1)
        assertEquals(1, vm.uiState.value.rows.size)

        sessionFlow.value = SessionLinkState.Disconnected
        assertTrue(vm.uiState.value.rows.isEmpty())

        val q2 = MarketQuote("BBB", "Bbb", 20.0, 20.0)
        quotes.tryEmit(q2)
        assertTrue(vm.uiState.value.rows.isEmpty())
    }

    @Test
    @DisplayName("handleIntent BeginStream when begin succeeds updates rows and emits NotifySuccess")
    fun handleIntent_beginStream_whenBeginSucceeds_updatesRowsAndEmitsSuccessEffect() = runBlocking {
        val quotes = MutableSharedFlow<MarketQuote>(extraBufferCapacity = 8)
        stubBeginStreamingReturns()
        whenever(observeQuoteStream.invoke()).thenReturn(quotes)
        val quote = MarketQuote("SYM", "Sym", 5.0, 4.0)
        val row = QuoteRowUiModel("SYM", "Sym", "$5.00", 5.0, "+$1.00", "+25.00%", QuoteTrend.Up)
        whenever(sortQuotesByPrice(any())).thenReturn(listOf(quote))
        whenever(mapper.toRowUi(quote)).thenReturn(row)

        sessionFlow.value = SessionLinkState.Connected
        val vm = newViewModel()
        assertEquals(false, vm.uiState.value.isBusy)

        vm.handleIntent(LiveQuotesIntent.BeginStream)
        assertEquals(false, vm.uiState.value.isBusy)

        quotes.tryEmit(quote)
        assertEquals(listOf(row), vm.uiState.value.rows)

        val effect = vm.effects.first()
        assertInstanceOf(LiveQuotesEffect.NotifySuccess::class.java, effect)
        assertEquals(R.string.live_quotes_notify_stream_ready, (effect as LiveQuotesEffect.NotifySuccess).messageResId)
        verify(appLogger).d(eq("LiveQuotesViewModel"), eq("Stream begin acknowledged"), isNull())
    }

    @Test
    @DisplayName("handleIntent BeginStream when begin fails emits NotifyError and logs")
    fun handleIntent_beginStream_whenBeginFails_emitsNotifyErrorAndLogs() = runBlocking {
        val boom = IllegalStateException("no uplink")
        stubBeginStreamingThrows(boom)
        sessionFlow.value = SessionLinkState.Connected
        val vm = newViewModel()

        vm.handleIntent(LiveQuotesIntent.BeginStream)

        assertEquals(false, vm.uiState.value.isBusy)
        val effect = vm.effects.first()
        assertInstanceOf(LiveQuotesEffect.NotifyError::class.java, effect)
        assertEquals(R.string.live_quotes_notify_stream_failed, (effect as LiveQuotesEffect.NotifyError).messageResId)
        verify(appLogger).e(eq("LiveQuotesViewModel"), eq("Failed to begin stream"), eq(boom))
    }

    @Test
    @DisplayName("handleIntent EndStream when end succeeds emits NotifyInfo")
    fun handleIntent_endStream_whenEndSucceeds_emitsNotifyInfo() = runBlocking {
        stubEndStreamingReturns()
        sessionFlow.value = SessionLinkState.Connected
        val vm = newViewModel()

        vm.handleIntent(LiveQuotesIntent.EndStream)

        assertEquals(false, vm.uiState.value.isBusy)
        val effect = vm.effects.first()
        assertInstanceOf(LiveQuotesEffect.NotifyInfo::class.java, effect)
        assertEquals(R.string.live_quotes_notify_stream_stopped, (effect as LiveQuotesEffect.NotifyInfo).messageResId)
    }

    @Test
    @DisplayName("handleIntent EndStream when end fails emits NotifyError and logs")
    fun handleIntent_endStream_whenEndFails_emitsNotifyErrorAndLogs() = runBlocking {
        val boom = RuntimeException("stop failed")
        stubEndStreamingThrows(boom)
        sessionFlow.value = SessionLinkState.Connected
        val vm = newViewModel()

        vm.handleIntent(LiveQuotesIntent.EndStream)

        val effect = vm.effects.first()
        assertInstanceOf(LiveQuotesEffect.NotifyError::class.java, effect)
        assertEquals(R.string.live_quotes_notify_stream_failed, (effect as LiveQuotesEffect.NotifyError).messageResId)
        verify(appLogger).e(eq("LiveQuotesViewModel"), eq("Failed to end stream"), eq(boom))
    }

    @Test
    @DisplayName("multiple quotes re-sort and map in mocked order")
    fun quoteStream_whenTwoQuotes_thenSortsAndMapsInOrder() {
        val quotes = MutableSharedFlow<MarketQuote>(extraBufferCapacity = 8)
        stubBeginStreamingReturns()
        whenever(observeQuoteStream.invoke()).thenReturn(quotes)
        val low = MarketQuote("L", "Low", 1.0, 1.0)
        val high = MarketQuote("H", "High", 99.0, 99.0)
        val rowLow = QuoteRowUiModel("L", "Low", "$1.00", 1.0, "$0.00", "0.00%", QuoteTrend.Flat)
        val rowHigh = QuoteRowUiModel("H", "High", "$99.00", 99.0, "$0.00", "0.00%", QuoteTrend.Flat)
        whenever(sortQuotesByPrice(any())).thenAnswer { inv ->
            inv.getArgument<List<MarketQuote>>(0).sortedByDescending { it.price }
        }
        whenever(mapper.toRowUi(high)).thenReturn(rowHigh)
        whenever(mapper.toRowUi(low)).thenReturn(rowLow)

        sessionFlow.value = SessionLinkState.Connected
        val vm = newViewModel()
        vm.handleIntent(LiveQuotesIntent.BeginStream)
        quotes.tryEmit(low)
        quotes.tryEmit(high)

        assertEquals(listOf(rowHigh, rowLow), vm.uiState.value.rows)
        verify(mapper, times(1)).toRowUi(high)
        verify(mapper, times(2)).toRowUi(low)
    }
}
