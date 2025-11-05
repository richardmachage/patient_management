package dev.forsythe.patientmanagement.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoutes {

    @Serializable
    object Registration : NavRoutes
    @Serializable
    object PatientsList : NavRoutes

    @Serializable
    data class Vitals(val patientId : String) : NavRoutes

    @Serializable
    data class Assessment(val patientId : String, val assessmentType : String) :  NavRoutes

}