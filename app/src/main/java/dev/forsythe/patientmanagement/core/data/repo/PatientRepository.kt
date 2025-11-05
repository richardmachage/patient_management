package dev.forsythe.patientmanagement.core.data.repo

import dev.forsythe.patientmanagement.core.data.room.entities.assessment.AssessmentEntity
import dev.forsythe.patientmanagement.core.data.room.entities.patients.PatientsEntity
import dev.forsythe.patientmanagement.core.data.room.entities.vitals.VitalsEntity
import dev.forsythe.patientmanagement.feature.patients.model.PatientListingItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface for the single source of truth for patient data.
 * ViewModels will interact with this, not directly with Room or Ktor.
 */
interface PatientRepository {


    /**
     * Attempts to log in the user via the API.
     * On success, saves the auth token
     * Returns a Result wrapper indicating success or failure.
     */
    suspend fun login(email: String, password: String): Result<Unit>


    /**
     * Attempts to register a new user via the API.
     */
    suspend fun signup(
        email: String,
        firstName: String,
        lastName: String,
        password: String
    ): Result<Unit>


    /**
     * Saves a new patient

     */
    suspend fun registerPatient(patient: PatientsEntity) : Result<String>



    /**
     * Saves a new vitals reading
     */
    suspend fun saveVitals(vitals: VitalsEntity) : Result<String>

    /**
     * Saves a new assessment
     */
    suspend fun saveAssessment(assessment: AssessmentEntity) : Result<String>

    
    /**
     * Gets a real-time stream of all patients for the patient listing page.
     */
    fun getPatientListings(filterDate : String?): Flow<List<PatientListingItem>>

    /**
     * Checks if a patient with this unique ID already exists
     * This is for the Patient Registration form validation.
     */
    suspend fun doesPatientExist(patientId: String): Boolean

    suspend fun getPatient(patientId: String): PatientsEntity?

}

