package com.mirza.stockpricetracker

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import com.mirza.stockpricetracker.data.preferences.ThemePreferenceStore
import com.mirza.stockpricetracker.designsystem.theme.StockPriceTrackerTheme
import com.mirza.stockpricetracker.presentation.screens.livequotes.LiveQuotesScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity() {

    /**
     * When [android:configChanges] includes [Configuration.UI_MODE], the activity is not recreated
     * on night mode changes. Compose still needs a recomposition after [onConfigurationChanged]
     * so [isSystemInDarkTheme] reads the updated uiMode.
     */
    private val uiModeGeneration = mutableIntStateOf(0)

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        uiModeGeneration.intValue++
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val themeStore = getKoin().get<ThemePreferenceStore>()
        runBlocking(Dispatchers.IO) {
            themeStore.applyStoredNightModeToDelegate()
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            uiModeGeneration.intValue
            val isDarkTheme = isSystemInDarkTheme()

            StockPriceTrackerTheme(darkTheme = isDarkTheme) {
                LiveQuotesScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
