package dev.forsythe.patientmanagement.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)