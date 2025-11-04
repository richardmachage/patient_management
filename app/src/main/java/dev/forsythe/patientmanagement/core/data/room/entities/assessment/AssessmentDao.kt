package dev.forsythe.patientmanagement.core.data.room.entities.assessment

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessment: AssessmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAssessments(assessments: List<AssessmentEntity>)

    @Query("SELECT * FROM AssessmentEntity")
    fun getAllAssessments(): Flow<List<AssessmentEntity>>

    @Query("SELECT * FROM AssessmentEntity WHERE patientId = :patientId")
    fun getAssessmentsByPatientId(patientId: String): Flow<List<AssessmentEntity>>

    @Query("SELECT * FROM AssessmentEntity WHERE id = :id")
    fun getAssessmentById(id: String): Flow<AssessmentEntity?>

    @Update
    suspend fun updateAssessment(assessment: AssessmentEntity)

    @Delete
    suspend fun deleteAssessment(assessment: AssessmentEntity)

}