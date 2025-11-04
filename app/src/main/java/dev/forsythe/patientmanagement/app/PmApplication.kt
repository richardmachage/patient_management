package dev.forsythe.patientmanagement.app

import android.app.Application
import dev.forsythe.patientmanagement.core.di.dataModule
import dev.forsythe.patientmanagement.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class PmApplication  : Application() {

    override fun onCreate() {
        super.onCreate()

        //initialize timber logging
        Timber.plant(Timber.DebugTree())

        //initialize koin
        startKoin {
            androidLogger()
            androidContext(this@PmApplication)
            modules(dataModule, viewModelModule)
        }


    }
}