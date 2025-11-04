package dev.forsythe.patientmanagement.core.data.room.entities.vitals

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class VitalsEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val patientId : String,
    val visitDate :  String,
    val height : Double,
    val weight : Double,
    val isSynced : Boolean = false
    ){

    val bmi : Double
        get() = weight / (height * height)
}
