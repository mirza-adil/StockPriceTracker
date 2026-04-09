package com.mirza.stockpricetracker.di

import com.mirza.stockpricetracker.core.logging.AppLogger
import com.mirza.stockpricetracker.core.logging.TimberAppLogger
import com.mirza.stockpricetracker.data.datasource.LocalWelcomeDataSource
import com.mirza.stockpricetracker.data.repository.WelcomeRepositoryImpl
import com.mirza.stockpricetracker.domain.repository.WelcomeRepository
import com.mirza.stockpricetracker.domain.usecase.GetWelcomeMessageUseCase
import com.mirza.stockpricetracker.presentation.screens.home.HomeViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<AppLogger> { TimberAppLogger() }
    singleOf(::LocalWelcomeDataSource)
    singleOf(::WelcomeRepositoryImpl) { bind<WelcomeRepository>() }
    singleOf(::GetWelcomeMessageUseCase)
    viewModelOf(::HomeViewModel)
}
