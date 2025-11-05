package dev.forsythe.patientmanagement.core.data.room.entities.patients

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
    fun getPatientByUniqueId(uniqueId: String): PatientsEntity?

    @Update
    suspend fun updatePatient(patient: PatientsEntity)

    @Delete
    suspend fun deletePatient(patient: PatientsEntity)

    @Query("UPDATE PatientsEntity SET isSynced = :isSynced WHERE uniqueId = :patientId")
    suspend fun updatePatientSyncStatus(patientId: String, isSynced: Boolean)

    @Transaction
    @Query("""
    SELECT 
        p.*, -- All columns from PatientsEntity, mapped to the 'patient' object

        -- Aliased columns from the vitals subquery 'v' to match the 'vital_' prefix
        v.id AS vital_id,
        v.patientId AS vital_patientId,
        v.visitDate AS vital_visitDate,
        v.height AS vital_height,
        v.weight AS vital_weight,
        v.isSynced AS vital_isSynced,
        
        -- Calculate BMI on the fly and alias it
        (v.weight / (v.height * v.height)) AS vital_bmi 
        
    FROM PatientsEntity AS p
    LEFT JOIN (
        SELECT *,
               -- Partition by 'patientId' (the foreign key in VitalsEntity)
               ROW_NUMBER() OVER(PARTITION BY patientId ORDER BY visitDate DESC) as rn
        FROM VitalsEntity
    ) AS v ON p.uniqueId = v.patientId AND v.rn = 1
    WHERE (:filterDate IS NULL OR v.visitDate = :filterDate)
    """)
    fun getPatientsWithLatestVitals(filterDate: String?): Flow<List<PatientWithLatestVital>>
    
    
}