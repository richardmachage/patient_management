package dev.forsythe.patientmanagement.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AddVisitResponseData(
    val message: String,
    val slug: Int
)