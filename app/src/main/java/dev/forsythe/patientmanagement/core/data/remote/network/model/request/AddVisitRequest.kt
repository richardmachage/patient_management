package dev.forsythe.patientmanagement.core.data.remote.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddVisitRequest(
    val comments: String,
    val general_health: String,
    val on_diet: String,
    val on_drugs: String,
    val patient_id: String,
    val visit_date: String,
    val vital_id: String
)