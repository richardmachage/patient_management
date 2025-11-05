package dev.forsythe.patientmanagement.feature.vitals

sealed class VitalsNavigationEvent {
    // We just pass the patientId and the type of assessment to show
    data class ToAssessment(
        val patientId: String,
        val assessmentType: String // e.g., "GENERAL" or "OVERWEIGHT"
    ) : VitalsNavigationEvent()
}