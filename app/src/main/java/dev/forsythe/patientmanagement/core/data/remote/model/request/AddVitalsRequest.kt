package dev.forsythe.patientmanagement.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddVitalsRequest(
    val bmi: String,
    val height: String,
    val patient_id: String,
    val visit_date: String,
    val weight: String
)