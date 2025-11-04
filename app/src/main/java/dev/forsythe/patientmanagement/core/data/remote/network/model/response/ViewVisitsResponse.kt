package dev.forsythe.patientmanagement.core.data.remote.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ViewVisitsResponse(
    val code: Int,
    val `data`: List<ViewVisitsResponseData>,
    val message: String,
    val success: Boolean
)