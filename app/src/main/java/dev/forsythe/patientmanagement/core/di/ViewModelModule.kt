package dev.forsythe.patientmanagement.core.di

import dev.forsythe.patientmanagement.feature.registration.PatientRegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel{ PatientRegistrationViewModel(repo = get()) }
}