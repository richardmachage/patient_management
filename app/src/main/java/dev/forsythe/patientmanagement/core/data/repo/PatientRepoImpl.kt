package dev.forsythe.patientmanagement.core.data.repo

import android.R.attr.phoneNumber
import dev.forsythe.patientmanagement.core.data.cache.AccessTokenProvider
import dev.forsythe.patientmanagement.core.data.preferences.SharedPreferences
import dev.forsythe.patientmanagement.core.data.remote.KtorClient
import dev.forsythe.patientmanagement.core.data.remote.logIn
import dev.forsythe.patientmanagement.core.data.remote.model.request.LogInRequest
import dev.forsythe.patientmanagement.core.data.remote.model.request.SignUpRequest
import dev.forsythe.patientmanagement.core.data.remote.signUp
import dev.forsythe.patientmanagement.core.data.room.db.PatientManagementDb
import dev.forsythe.patientmanagement.core.data.room.entities.assessment.AssessmentEntity
import dev.forsythe.patientmanagement.core.data.room.entities.patients.PatientsEntity
import dev.forsythe.patientmanagement.core.data.room.entities.vitals.VitalsEntity
import dev.forsythe.patientmanagement.core.model.BmiStatus
import dev.forsythe.patientmanagement.feature.patients.BmiStatusChip
import dev.forsythe.patientmanagement.feature.patients.PatientListItem
import dev.forsythe.patientmanagement.feature.patients.model.PatientListingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter


private const val TAG = "PatientRepoImpl"
class PatientRepoImpl (
    private val api: KtorClient,
    private val db: PatientManagementDb,
    private val accessTokenProvider: AccessTokenProvider,
    private val sharedPref : SharedPreferences
) : PatientRepository {

    private val assessmentDao = db.assessmentDao()
    private val patientsDao = db.patientsDao()
    private val vitalsDao = db.vitalsDao()




    override suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LogInRequest(email, password)
                // Call the Ktor extension function
                val logInData = api.logIn(request)


                // Save the token on success
                sharedPref.saveData(
                    key = SharedPreferences.ACCESS_TOKEN, value = logInData.access_token
                )

                Timber.tag(TAG).d("Login successful")
                Result.success(Unit)
            } catch (e: Exception) {
                Timber.tag(TAG).e(e)
                Result.failure(e)
            }
        }
    }

    override suspend fun signup(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {try {
            val request = SignUpRequest(email, firstName, lastName, password)
            // Call the Ktor extension function
           val data =  api.signUp(request)
            Timber.tag(TAG).d(data.message)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e)
            Result.failure(e)
        }
    }
        }

    override suspend fun registerPatient(patient: PatientsEntity): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                patientsDao.insertPatient(patient)
                Timber.tag(TAG).d("Patient registered successfully")
                Result.success(patient.uniqueId)
            } catch (e: Exception) {
                Timber.tag(TAG).e(e)
                Result.failure(e.cause ?: Exception("Unknown error"))
            }
        }
    }

    override suspend fun saveVitals(vitals: VitalsEntity): Result<String> {
        return withContext(Dispatchers.IO) {

            try {
                vitalsDao.insertVitals(vitals)
                Timber.tag(TAG).d("Vitals saved successfully")
                Result.success(vitals.id)
            } catch (e: Exception) {
                Timber.tag(TAG).e(e)
                Result.failure(e.cause ?: Exception("Unknown error"))
            }
        }
    }

    override suspend fun saveAssessment(assessment: AssessmentEntity): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                assessmentDao.insertAssessment(assessment)
                Timber.tag(TAG).d("Assessment saved successfully")
                Result.success(assessment.id)
            } catch (e: Exception) {
                Timber.tag(TAG).e(e)
                Result.failure(e.cause ?: Exception("Unknown error"))
            }
        }
    }

    override suspend fun getPatient(patientId: String): PatientsEntity? {
        return withContext(Dispatchers.IO){patientsDao.getPatientByUniqueId(patientId)}
    }

    override fun getPatientListings(filterDate : String?): Flow<List<PatientListingItem>> {
        return try {

                patientsDao.getPatientsWithLatestVitals(filterDate).map { patientWithVitalsList ->
                        patientWithVitalsList.map { patientWithVital ->
                            val patient = patientWithVital.patient
                            val latestVital = patientWithVital.vital // This can be null

                            val bmi = latestVital?.bmi ?: 0.0
                            val bmiStatus = getBmiStatus(bmi) ?: BmiStatus.Normal

                            PatientListingItem(
                                patientId = patient.uniqueId,
                                patientName = patient.firstname + " " + patient.lastname,
                                age = calculateAge(dobString = patient.dateOfBirth),
                                lastVisitDate = latestVital?.visitDate
                                    ?: patient.regDate, // Fallback
                                lastBmiStatus = bmiStatus,
                                lastBmi = bmi
                            )
                        }

                    }

            } catch (e: Exception) {
                Timber.tag(TAG).e(e)
                flow { }
            }
    }

    override suspend fun doesPatientExist(patientId: String): Boolean {
        return withContext(Dispatchers.IO){ patientsDao.getPatientByUniqueId(patientId) != null}
    }

    private fun calculateAge(dobString: String): Int {
        return try {
            val dob = LocalDate.parse(dobString, DateTimeFormatter.ISO_LOCAL_DATE)
            Period.between(dob, LocalDate.now()).years
        } catch (e: Exception) {
            0
        }
    }

    private fun getBmiStatus(bmi: Double): BmiStatus? {

        return when {
            bmi < 18.5 -> BmiStatus.Underweight
            bmi >= 18.5 && bmi < 25 -> BmiStatus.Normal
            bmi >= 25 -> BmiStatus.Overweight
            else -> null
        }
    }

}