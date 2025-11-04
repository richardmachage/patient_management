package dev.forsythe.patientmanagement.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String
)