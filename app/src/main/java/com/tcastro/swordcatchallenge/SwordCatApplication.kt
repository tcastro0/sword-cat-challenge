package com.tcastro.swordcatchallenge

import android.app.Application
import com.tcastro.data.core.di.dataCoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class SwordCatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SwordCatApplication)
            modules(
                dataCoreModule(
                    baseUrl = BuildConfig.CAT_API_URL,
                    apiKey = BuildConfig.CAT_KEY
                )
            )
        }
    }

}