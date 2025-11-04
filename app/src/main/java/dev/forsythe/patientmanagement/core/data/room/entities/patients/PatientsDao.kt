package dev.forsythe.patientmanagement.core.data.room.entities.patients

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: PatientsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPatients(patients: List<PatientsEntity>)

    @Query("SELECT * FROM PatientsEntity")
    fun getAllPatients(): Flow<List<PatientsEntity>>

    @Query("SELECT * FROM PatientsEntity WHERE uniqueId = :uniqueId")
    fun getPatientByUniqueId(uniqueId: String): Flow<PatientsEntity?>

    @Update
    suspend fun updatePatient(patient: PatientsEntity)

    @Delete
    suspend fun deletePatient(patient: PatientsEntity)
}