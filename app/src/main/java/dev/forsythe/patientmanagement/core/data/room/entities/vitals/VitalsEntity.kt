package dev.forsythe.patientmanagement.core.data.room.entities.vitals

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import kotlin.math.roundToInt

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
        get() {
            // Check for division by zero
            if (height == 0.0) return 0.0

            val rawBmi = weight / (height * height)
            // Multiply by 100, round to the nearest Int,
            // then divide by 100.0 to restore the 2 decimal places.
            return (rawBmi * 100).roundToInt() / 100.0
        }
}
