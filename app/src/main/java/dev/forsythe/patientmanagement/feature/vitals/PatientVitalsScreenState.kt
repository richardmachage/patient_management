package dev.forsythe.patientmanagement.feature.vitals



data class PatientVitalsScreenState(
    val isLoading: Boolean = false,
    val patientName: String = "Loading...", // Default while we fetch
    val visitDate: String = "",
    val height: String = "",
    val weight: String = "",
    val bmi: String = "", // Calculated, read-only
    val visitDateError: String? = null,
    val heightError: String? = null,
    val weightError: String? = null,
    val saveError: String? = null,

    // NEW: Simplified navigation event
    val navigationEvent: VitalsNavigationEvent? = null
)