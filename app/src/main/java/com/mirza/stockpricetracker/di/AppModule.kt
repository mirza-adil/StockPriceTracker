package com.mirza.stockpricetracker.di

import com.mirza.stockpricetracker.core.logging.AppLogger
import com.mirza.stockpricetracker.core.logging.TimberAppLogger
import com.mirza.stockpricetracker.data.datasource.SimulatedQuoteStreamDataSource
import com.mirza.stockpricetracker.data.preferences.ThemePreferenceStore
import com.mirza.stockpricetracker.data.repository.MarketQuotesRepositoryImpl
import com.mirza.stockpricetracker.domain.repository.MarketQuotesRepository
import com.mirza.stockpricetracker.domain.usecase.ManageSessionLinkUseCase
import com.mirza.stockpricetracker.domain.usecase.ObserveQuoteStreamUseCase
import com.mirza.stockpricetracker.domain.usecase.SortQuotesByPriceUseCase
import com.mirza.stockpricetracker.presentation.screens.livequotes.LiveQuotesViewModel
import com.mirza.stockpricetracker.presentation.screens.livequotes.mapper.MarketQuoteToRowUiMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<AppLogger> { TimberAppLogger() }
    single<CoroutineDispatcher> { Dispatchers.IO }
    single { ThemePreferenceStore(androidContext()) }

    singleOf(::SimulatedQuoteStreamDataSource)
    singleOf(::MarketQuotesRepositoryImpl) { bind<MarketQuotesRepository>() }

    singleOf(::ObserveQuoteStreamUseCase)
    singleOf(::ManageSessionLinkUseCase)
    singleOf(::SortQuotesByPriceUseCase)
    singleOf(::MarketQuoteToRowUiMapper)

    viewModelOf(::LiveQuotesViewModel)
}
