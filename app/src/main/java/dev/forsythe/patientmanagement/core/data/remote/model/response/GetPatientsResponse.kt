package dev.forsythe.patientmanagement.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetPatientsResponse(
    val code: Int,
    val `data`: List<PatientData>,
    val message: String,
    val success: Boolean
)