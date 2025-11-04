package dev.forsythe.patientmanagement.core.data.remote.model.response

data class RegisterResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)