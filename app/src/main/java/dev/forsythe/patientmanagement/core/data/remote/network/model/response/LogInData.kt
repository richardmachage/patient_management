package dev.forsythe.patientmanagement.core.data.remote.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LogInData(
    val access_token: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val name: String,
    val updated_at: String
)