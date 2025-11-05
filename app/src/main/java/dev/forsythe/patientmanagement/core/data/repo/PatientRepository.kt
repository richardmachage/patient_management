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
     * On success, saves the auth token to SharedPrefs.
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
     * Saves a new patient to the local Room database.
     * The implementation will also enqueue a WorkManager job to sync this
     * patient to the remote API.
     */
    suspend fun registerPatient(patient: PatientsEntity) : Result<String>



    /**
     * Saves a new vitals reading to the local Room database.
     * The implementation will also enqueue a WorkManager job (chained to the
     * patient sync) to sync these vitals to the remote API.
     */
    suspend fun saveVitals(vitals: VitalsEntity) : Result<String>

    /**
     * Saves a new assessment to the local Room database.
     * The implementation will also enqueue a WorkManager job (chained to the
     * vitals sync) to sync this assessment to the remote API.
     */
    suspend fun saveAssessment(assessment: AssessmentEntity) : Result<String>

    
    /**
     * Gets a real-time stream of all patients for the patient listing page.
     * This Flow will automatically update the UI whenever the Room database changes.
     * It combines Patient and their latest Vitals for the BMI status.
     *
     * @return A Flow emitting a list of [PatientListingItem].
     */
    fun getPatientListings(filterDate : String?): Flow<List<PatientListingItem>>

    /**
     * Checks if a patient with this unique ID already exists in the local DB.
     * This is for the Patient Registration form validation.
     */
    suspend fun doesPatientExist(patientId: String): Boolean

    suspend fun getPatient(patientId: String): PatientsEntity?

}

