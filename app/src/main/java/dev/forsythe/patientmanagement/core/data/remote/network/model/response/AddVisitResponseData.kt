package dev.forsythe.patientmanagement.core.data.remote.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AddVisitResponseData(
    val message: String,
    val slug: Int
)