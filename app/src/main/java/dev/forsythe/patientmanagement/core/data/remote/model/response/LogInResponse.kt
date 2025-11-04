package dev.forsythe.patientmanagement.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LogInResponse(
    val code: Int,
    val `data`: LogInData,
    val message: String,
    val success: Boolean
)