package com.mirza.stockpricetracker

import android.app.Application
import com.mirza.stockpricetracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class StockPriceTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@StockPriceTrackerApplication)
            modules(appModule)
        }
    }
}
