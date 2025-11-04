package dev.forsythe.patientmanagement.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ViewVisitsResponse(
    val code: Int,
    val `data`: List<ViewVisitsResponseData>,
    val message: String,
    val success: Boolean
)