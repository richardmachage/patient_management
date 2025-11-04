package dev.forsythe.patientmanagement.core.data.remote.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AddVitalsResponse(
    val code: Int,
    val `data`: AddVitalsResponseData,
    val message: String,
    val success: Boolean
)