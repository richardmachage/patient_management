package dev.forsythe.patientmanagement.core.data.remote.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ViewVisitsRequest(
    val visit_date: String
)