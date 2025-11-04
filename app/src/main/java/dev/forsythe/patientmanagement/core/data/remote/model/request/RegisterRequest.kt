package dev.forsythe.patientmanagement.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val dob: String,
    val firstname: String,
    val gender: String,
    val lastname: String,
    val reg_date: String,
    val unique: String
)