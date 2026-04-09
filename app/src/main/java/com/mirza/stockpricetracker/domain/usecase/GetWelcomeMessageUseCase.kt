package com.mirza.stockpricetracker.domain.usecase

import com.mirza.stockpricetracker.domain.repository.WelcomeRepository

class GetWelcomeMessageUseCase(
    private val repository: WelcomeRepository
) {
    suspend operator fun invoke(): String = repository.getWelcomeMessage()
}
