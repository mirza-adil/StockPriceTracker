package com.mirza.stockpricetracker.data.repository

import com.mirza.stockpricetracker.core.logging.AppLogger
import com.mirza.stockpricetracker.data.datasource.LocalWelcomeDataSource
import com.mirza.stockpricetracker.domain.repository.WelcomeRepository

class WelcomeRepositoryImpl(
    private val localWelcomeDataSource: LocalWelcomeDataSource,
    private val appLogger: AppLogger
) : WelcomeRepository {

    override suspend fun getWelcomeMessage(): String {
        appLogger.d(TAG, "Loading welcome message")
        return localWelcomeDataSource.getMessage()
    }

    private companion object {
        const val TAG = "WelcomeRepository"
    }
}
