package dev.forsythe.patientmanagement.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ViewPatientResponse(
    val code: Int,
    val `data`: List<PatientData>,
    val message: String,
    val success: Boolean
)