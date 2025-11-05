package dev.forsythe.patientmanagement.core.data.room.entities.assessment

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.forsythe.patientmanagement.core.model.GeneralHealth
import java.util.UUID

@Entity
data class AssessmentEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val vitalId : String,
    val visitDate : String,
    val generalHealth : GeneralHealth,
    val onDiet : Boolean?,
    val usingDrugs : Boolean?,
    val comments : String?,
    val isSynced : Boolean = false,
    val patientId : String

)



