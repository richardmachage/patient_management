package dev.forsythe.patientmanagement.feature.patients.model

import dev.forsythe.patientmanagement.core.model.BmiStatus


data class PatientListingItem(
    val patientId: String,       // The 'unique' ID to use for clicks
    val patientName: String,     // Combined "Firstname Lastname"
    val age: Int,                // Calculated from DOB
    val lastVisitDate: String,   // The date of the last vitals/assessment
    val lastBmi: Double?,        // The most recent BMI value
    val lastBmiStatus: BmiStatus    // "Underweight", "Normal", "Overweight"
)


val mockPatients = listOf(
    PatientListingItem("1", "Eleanor Pena", 32, "Oct 26, 2023", 21.5, BmiStatus.Normal),
    PatientListingItem("2", "Cody Fisher", 45, "Oct 26, 2023", 28.1, BmiStatus.Overweight),
    PatientListingItem("3", "Esther Howard", 51, "Oct 26, 2023", 18.2, BmiStatus.Underweight)
)