package dev.forsythe.patientmanagement.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ViewVisitsRequest(
    val visit_date: String
)