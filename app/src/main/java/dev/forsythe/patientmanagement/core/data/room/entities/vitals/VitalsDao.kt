package dev.forsythe.patientmanagement.core.data.room.entities.vitals

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {

    @Query("UPDATE VitalsEntity SET isSynced = :isSynced WHERE id = :id")
    suspend fun updateSyncStatus (id: String, isSynced: Boolean)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVitals(vitals: VitalsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllVitals(vitals: List<VitalsEntity>)

    @Query("SELECT * FROM VitalsEntity")
    fun getAllVitals(): Flow<List<VitalsEntity>>

    @Query("SELECT * FROM VitalsEntity WHERE patientId = :patientId")
    fun getVitalsByPatientId(patientId: String): Flow<List<VitalsEntity>>

    @Query("SELECT * FROM VitalsEntity WHERE id = :id")
    fun getVitalsById(id: String): Flow<VitalsEntity?>

    @Update
    suspend fun updateVitals(vitals: VitalsEntity)

    @Delete
    suspend fun deleteVitals(vitals: VitalsEntity)

}