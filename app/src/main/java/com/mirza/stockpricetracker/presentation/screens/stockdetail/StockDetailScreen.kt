package com.mirza.stockpricetracker.presentation.screens.stockdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mirza.stockpricetracker.designsystem.theme.BoardSpacing

@Composable
fun StockDetailScreen(
    symbol: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = BoardSpacing.large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Stock Detail",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = symbol,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = BoardSpacing.small)
        )
        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = BoardSpacing.large)
        ) {
            Text(text = "Back")
        }
    }
}
