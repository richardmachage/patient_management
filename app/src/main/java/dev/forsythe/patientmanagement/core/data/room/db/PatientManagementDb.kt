package dev.forsythe.patientmanagement.core.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.forsythe.patientmanagement.core.data.room.entities.patients.PatientsDao
import dev.forsythe.patientmanagement.core.data.room.entities.patients.PatientsEntity
import kotlin.jvm.java

@Database(
    entities = [PatientsEntity::class],
    version = 1,
    exportSchema = true

)
abstract class PatientManagementDb : RoomDatabase(){

    abstract fun patientsDao(): PatientsDao

    companion object {
        private var INSTANCE: PatientManagementDb? = null

        fun getInstance(context: Context): PatientManagementDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PatientManagementDb::class.java,
                        "patient_management_db"
                    )
                        .fallbackToDestructiveMigration(true)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}