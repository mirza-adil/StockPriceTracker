package com.mirza.stockpricetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mirza.stockpricetracker.presentation.screens.livequotes.LiveQuotesScreen
import com.mirza.stockpricetracker.presentation.screens.stockdetail.StockDetailScreen

object AppRoutes {
    const val LIVE_QUOTES = "live_quotes"
    const val SYMBOL_ARG = "symbol"
    const val STOCK_DETAIL_ROUTE = "stock_detail/{$SYMBOL_ARG}"
    const val STOCK_DETAIL_DEEP_LINK = "stocks://symbol/{$SYMBOL_ARG}"
}

@Composable
fun AppNavHost(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LIVE_QUOTES,
        modifier = modifier
    ) {
        composable(route = AppRoutes.LIVE_QUOTES) {
            LiveQuotesScreen(
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        }

        composable(
            route = AppRoutes.STOCK_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(AppRoutes.SYMBOL_ARG) { type = NavType.StringType }
            ),
            deepLinks = listOf(
                navDeepLink { uriPattern = AppRoutes.STOCK_DETAIL_DEEP_LINK }
            )
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString(AppRoutes.SYMBOL_ARG).orEmpty()
            LaunchedEffect(symbol) {
                if (symbol.isBlank()) {
                    navController.popBackStack()
                }
            }
            StockDetailScreen(
                symbol = symbol,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
