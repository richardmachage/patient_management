package dev.forsythe.patientmanagement.core.data.remote.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)