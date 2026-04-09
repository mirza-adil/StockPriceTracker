package com.mirza.stockpricetracker.presentation.screens.livequotes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mirza.stockpricetracker.R
import com.mirza.stockpricetracker.designsystem.components.TickerHeaderBar
import com.mirza.stockpricetracker.designsystem.theme.BoardSpacing
import com.mirza.stockpricetracker.designsystem.theme.BoardTypography
import com.mirza.stockpricetracker.designsystem.theme.boardColorScheme
import com.mirza.stockpricetracker.domain.model.SessionLinkState
import com.mirza.stockpricetracker.presentation.screens.livequotes.components.QuoteListItem
import org.koin.androidx.compose.koinViewModel

private enum class BoardContentPhase {
    Loading,
    Empty,
    List
}

@Composable
fun LiveQuotesScreen(
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
            snackbarHostState.showSnackbar(
                message = context.getString(resId),
                duration = SnackbarDuration.Short
            )
        }
    }

    val contentPhase = when {
        uiState.isBusy && uiState.rows.isEmpty() -> BoardContentPhase.Loading
        uiState.rows.isEmpty() -> BoardContentPhase.Empty
        else -> BoardContentPhase.List
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    shape = MaterialTheme.shapes.large
                )
            }
        },
        containerColor = colors.screenBackground,
        topBar = {
            TickerHeaderBar(
                title = stringResource(R.string.live_quotes_title),
                session = uiState.session,
                isBeginEnabled = uiState.isBeginEnabled,
                isEndEnabled = uiState.isEndEnabled,
                onBeginClick = { viewModel.handleIntent(LiveQuotesIntent.BeginStream) },
                onEndClick = { viewModel.handleIntent(LiveQuotesIntent.EndStream) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.screenBackground)
                .padding(paddingValues)
        ) {
            AnimatedContent(
                targetState = contentPhase,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "boardContent"
            ) { phase ->
                when (phase) {
                    BoardContentPhase.Loading -> StreamStartingPanel(
                        modifier = Modifier.fillMaxSize()
                    )

                    BoardContentPhase.Empty -> EmptyBoardPanel(
                        session = uiState.session,
                        modifier = Modifier.fillMaxSize()
                    )

                    BoardContentPhase.List -> LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding(),
                        contentPadding = PaddingValues(bottom = BoardSpacing.medium)
                    ) {
                        items(
                            items = uiState.rows,
                            key = { it.symbol },
                            contentType = { "quote" }
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
private fun StreamStartingPanel(modifier: Modifier = Modifier) {
    val colors = boardColorScheme()
    Column(
        modifier = modifier.padding(horizontal = BoardSpacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = colors.headerActionFill,
            trackColor = colors.divider.copy(alpha = 0.35f),
            strokeWidth = 3.dp
        )
        Spacer(modifier = Modifier.height(BoardSpacing.medium))
        Text(
            text = stringResource(R.string.loading_stream_start),
            style = BoardTypography.statusCaption,
            color = colors.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyBoardPanel(
    session: SessionLinkState,
    modifier: Modifier = Modifier
) {
    val colors = boardColorScheme()
    Column(
        modifier = modifier
            .padding(horizontal = BoardSpacing.large)
            .padding(bottom = BoardSpacing.extraLarge),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (session) {
            is SessionLinkState.Fault -> {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    modifier = Modifier.size(52.dp),
                    tint = colors.sessionError
                )
                Spacer(modifier = Modifier.height(BoardSpacing.medium))
                Text(
                    text = session.message,
                    style = BoardTypography.emptyStateBody,
                    color = colors.textSecondary,
                    textAlign = TextAlign.Center
                )
            }

            else -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                    contentDescription = null,
                    modifier = Modifier.size(52.dp),
                    tint = colors.textTertiary
                )
                Spacer(modifier = Modifier.height(BoardSpacing.medium))
                val headline = when (session) {
                    is SessionLinkState.Disconnected ->
                        stringResource(R.string.empty_state_headline_disconnected)
                    is SessionLinkState.Connecting ->
                        stringResource(R.string.empty_state_headline_connecting)
                    is SessionLinkState.Connected ->
                        stringResource(R.string.empty_state_headline_waiting)
                    is SessionLinkState.Fault -> ""
                }
                val detail = when (session) {
                    is SessionLinkState.Disconnected ->
                        stringResource(R.string.empty_state_detail_disconnected)
                    is SessionLinkState.Connecting ->
                        stringResource(R.string.empty_state_detail_connecting)
                    is SessionLinkState.Connected ->
                        stringResource(R.string.empty_state_detail_waiting)
                    is SessionLinkState.Fault -> ""
                }
                Text(
                    text = headline,
                    style = BoardTypography.emptyStateTitle,
                    color = colors.textPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(BoardSpacing.small))
                Text(
                    text = detail,
                    style = BoardTypography.emptyStateBody,
                    color = colors.textSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
