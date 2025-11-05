package dev.forsythe.patientmanagement.core.data.room.entities.patients

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.forsythe.patientmanagement.core.data.room.entities.vitals.VitalsEntity

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


data class PatientWithLatestVital(
    @Embedded
    val patient: PatientsEntity,

    @Embedded
    val vital: VitalsEntity? // Nullable, in case a patient has no vitals yet
)