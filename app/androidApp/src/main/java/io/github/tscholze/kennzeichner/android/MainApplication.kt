package io.github.tscholze.kennzeichner.android

import android.app.Application
import io.github.tscholze.kennzeichner.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        // Setup koin
        startKoin{
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule)
        }
    }
}