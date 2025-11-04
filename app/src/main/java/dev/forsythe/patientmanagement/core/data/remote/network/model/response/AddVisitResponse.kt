package dev.forsythe.patientmanagement.core.data.remote.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AddVisitResponse(
    val code: Int,
    val `data`: AddVisitResponseData,
    val message: String,
    val success: Boolean
)