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
