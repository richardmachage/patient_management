package dev.forsythe.patientmanagement.core.data.remote.network.model.response

data class RegisterResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)