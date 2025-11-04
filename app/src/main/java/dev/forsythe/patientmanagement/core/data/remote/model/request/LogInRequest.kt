package dev.forsythe.patientmanagement.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LogInRequest(
    val email: String,
    val password: String
)