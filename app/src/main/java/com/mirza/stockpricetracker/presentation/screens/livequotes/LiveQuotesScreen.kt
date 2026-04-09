package com.mirza.stockpricetracker.presentation.screens.livequotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mirza.stockpricetracker.R
import com.mirza.stockpricetracker.designsystem.components.TickerHeaderBar
import com.mirza.stockpricetracker.designsystem.theme.BoardSpacing
import com.mirza.stockpricetracker.designsystem.theme.BoardTypography
import com.mirza.stockpricetracker.designsystem.theme.boardColorScheme
import com.mirza.stockpricetracker.domain.model.SessionLinkState
import com.mirza.stockpricetracker.presentation.screens.livequotes.components.QuoteListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun LiveQuotesScreen(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LiveQuotesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val colors = boardColorScheme()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            val resId = when (effect) {
                is LiveQuotesEffect.NotifySuccess -> effect.messageResId
                is LiveQuotesEffect.NotifyError -> effect.messageResId
                is LiveQuotesEffect.NotifyInfo -> effect.messageResId
            }
            snackbarHostState.showSnackbar(context.getString(resId))
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = colors.screenBackground,
        topBar = {
            TickerHeaderBar(
                title = stringResource(R.string.live_quotes_title),
                session = uiState.session,
                isBeginEnabled = uiState.isBeginEnabled,
                isEndEnabled = uiState.isEndEnabled,
                isDarkTheme = isDarkTheme,
                onBeginClick = { viewModel.handleIntent(LiveQuotesIntent.BeginStream) },
                onEndClick = { viewModel.handleIntent(LiveQuotesIntent.EndStream) },
                onThemeToggle = onThemeToggle
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.screenBackground)
                .padding(paddingValues)
        ) {
            when {
                uiState.isBusy && uiState.rows.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.rows.isEmpty() -> {
                    EmptyBoardMessage(
                        session = uiState.session,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            items = uiState.rows,
                            key = { it.symbol }
                        ) { row ->
                            QuoteListItem(row = row)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyBoardMessage(
    session: SessionLinkState,
    modifier: Modifier = Modifier
) {
    val colors = boardColorScheme()
    val message = when (session) {
        is SessionLinkState.Disconnected ->
            stringResource(R.string.empty_state_disconnected)
        is SessionLinkState.Connecting ->
            stringResource(R.string.empty_state_connecting)
        is SessionLinkState.Fault ->
            session.message
        is SessionLinkState.Connected ->
            stringResource(R.string.empty_state_waiting)
    }
    Text(
        text = message,
        style = BoardTypography.statusCaption,
        color = colors.textSecondary,
        modifier = modifier.padding(BoardSpacing.large)
    )
}
