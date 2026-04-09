package com.mirza.stockpricetracker.domain.repository

interface WelcomeRepository {
    suspend fun getWelcomeMessage(): String
}
