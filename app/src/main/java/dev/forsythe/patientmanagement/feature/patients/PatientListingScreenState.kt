package dev.forsythe.patientmanagement.feature.patients

import dev.forsythe.patientmanagement.feature.patients.model.PatientListingItem

data class PatientListingScreenState(
    val isLoading: Boolean = true,
    val patients: List<PatientListingItem> = emptyList(),
    val filterDate: String = "", // Holds the UI value for the date picker
    val error: String? = null
)