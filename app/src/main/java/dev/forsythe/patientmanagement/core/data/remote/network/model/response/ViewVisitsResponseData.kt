package dev.forsythe.patientmanagement.core.data.remote.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ViewVisitsResponseData(
    val age: Int,
    val bmi: String,
    val name: String,
    val status: String
)