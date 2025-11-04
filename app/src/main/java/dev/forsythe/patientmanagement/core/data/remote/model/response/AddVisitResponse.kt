package dev.forsythe.patientmanagement.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AddVisitResponse(
    val code: Int,
    val `data`: AddVisitResponseData,
    val message: String,
    val success: Boolean
)