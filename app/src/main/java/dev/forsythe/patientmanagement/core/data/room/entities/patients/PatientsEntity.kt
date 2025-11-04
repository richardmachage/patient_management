package dev.forsythe.patientmanagement.core.data.room.entities.patients

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class PatientsEntity(
    @PrimaryKey
    val uniqueId: String /*= UUID.randomUUID().toString()*/,
    val patientId : String?,
    val firstname :  String,
    val lastname : String,
    val regDate : String,
    val gender : String,
    val dateOfBirth : String,
    val isSynced : Boolean = false

)
