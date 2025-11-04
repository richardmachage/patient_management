package dev.forsythe.patientmanagement.core.data.remote.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AddVitalsResponseData(
    val id: Int,
    val message: String,
    val patient_id: String,
    val slug: Int
)