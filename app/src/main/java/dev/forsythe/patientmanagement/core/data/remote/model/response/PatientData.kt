package dev.forsythe.patientmanagement.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientData(
    val created_at: String,
    val dob: String,
    val firstname: String,
    val gender: String,
    val id: Int,
    val lastname: String,
    val reg_date: String,
    val unique: String,
    val updated_at: String
)