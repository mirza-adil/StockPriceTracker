package com.mirza.stockpricetracker.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mirza.stockpricetracker.R
import com.mirza.stockpricetracker.designsystem.theme.BoardSpacing
import com.mirza.stockpricetracker.designsystem.theme.BoardTypography
import com.mirza.stockpricetracker.designsystem.theme.boardColorScheme
import com.mirza.stockpricetracker.domain.model.SessionLinkState

@Composable
fun TickerHeaderBar(
    title: String,
    session: SessionLinkState,
    isBeginEnabled: Boolean,
    isEndEnabled: Boolean,
    onBeginClick: () -> Unit,
    onEndClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = boardColorScheme()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(colors.screenBackground)
            .padding(
                horizontal = BoardSpacing.medium,
                vertical = BoardSpacing.mediumSmall
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = BoardTypography.headerTitle,
                color = colors.textPrimary,
                maxLines = 2
            )
            SessionStatusRow(session = session)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = BoardSpacing.small),
            horizontalArrangement = Arrangement.spacedBy(BoardSpacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val beginLabel = stringResource(R.string.action_begin_stream)
            Button(
                onClick = onBeginClick,
                enabled = isBeginEnabled,
                modifier = Modifier
                    .weight(1f)
                    .semantics { contentDescription = beginLabel },
                contentPadding = PaddingValues(
                    horizontal = BoardSpacing.medium,
                    vertical = BoardSpacing.mediumSmall
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.headerActionFill,
                    contentColor = colors.headerActionContent,
                    disabledContainerColor = colors.divider,
                    disabledContentColor = colors.textTertiary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.padding(end = BoardSpacing.extraSmall)
                )
                Text(text = stringResource(R.string.action_begin_stream))
            }
            val endLabel = stringResource(R.string.action_end_stream)
            Button(
                onClick = onEndClick,
                enabled = isEndEnabled,
                modifier = Modifier
                    .weight(1f)
                    .semantics { contentDescription = endLabel },
                contentPadding = PaddingValues(
                    horizontal = BoardSpacing.medium,
                    vertical = BoardSpacing.mediumSmall
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.negative.copy(alpha = 0.85f),
                    contentColor = Color.White,
                    disabledContainerColor = colors.divider,
                    disabledContentColor = colors.textTertiary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Stop,
                    contentDescription = null,
                    modifier = Modifier.padding(end = BoardSpacing.extraSmall)
                )
                Text(text = stringResource(R.string.action_end_stream))
            }
        }
    }
}

@Composable
private fun SessionStatusRow(session: SessionLinkState) {
    val colors = boardColorScheme()
    val (label, dotColor) = when (session) {
        is SessionLinkState.Connected ->
            stringResource(R.string.session_status_live) to colors.sessionConnected
        is SessionLinkState.Connecting ->
            stringResource(R.string.session_status_connecting) to colors.sessionConnecting
        is SessionLinkState.Disconnected ->
            stringResource(R.string.session_status_idle) to colors.sessionDisconnected
        is SessionLinkState.Fault ->
            session.message to colors.sessionError
    }
    Row(
        modifier = Modifier.padding(top = BoardSpacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BoardSpacing.small)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Text(
            text = label,
            style = BoardTypography.statusCaption,
            color = colors.textSecondary
        )
    }
}
