package dev.forsythe.patientmanagement.core.data.room.entities.patients

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class PatientsEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name : String = "",
    val age : String = "",
    val gender : String = "",
)
