package dev.forsythe.patientmanagement.core.di

import dev.forsythe.patientmanagement.core.data.cache.AccessTokenProvider
import dev.forsythe.patientmanagement.core.data.preferences.SharedPreferences
import dev.forsythe.patientmanagement.core.data.remote.KtorClient
import dev.forsythe.patientmanagement.core.data.repo.PatientRepoImpl
import dev.forsythe.patientmanagement.core.data.repo.PatientRepository
import dev.forsythe.patientmanagement.core.data.room.db.PatientManagementDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single { SharedPreferences(androidContext()) }

    single { PatientManagementDb.getInstance(androidContext()) }

    single { KtorClient() }

    single { AccessTokenProvider(sharedPrefs = get()) }


    //Repo

    single <PatientRepository>{
        PatientRepoImpl(
            api = get(),
            db = get(),
            sharedPref = get(),
            accessTokenProvider = get()
        )
    }



}