package dev.forsythe.patientmanagement.core.di

import dev.forsythe.patientmanagement.feature.assessment.AssessmentViewModel
import dev.forsythe.patientmanagement.feature.registration.PatientRegistrationViewModel
import dev.forsythe.patientmanagement.feature.vitals.VitalsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ PatientRegistrationViewModel(repository = get()) }
    viewModel{ VitalsViewModel(repository = get(), savedStateHandle = get()) }
    viewModel{ AssessmentViewModel(repository = get(), savedStateHandle = get()) }


}