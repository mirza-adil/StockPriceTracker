package com.mirza.stockpricetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.mirza.stockpricetracker.designsystem.theme.StockPriceTrackerTheme
import com.mirza.stockpricetracker.presentation.screens.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockPriceTrackerTheme {
                HomeScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
