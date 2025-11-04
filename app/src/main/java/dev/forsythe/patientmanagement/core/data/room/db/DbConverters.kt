package dev.forsythe.patientmanagement.core.data.room.db

import androidx.room.TypeConverter
import java.sql.Date

class DropDbConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { it ->
            Date(it)
        }
    }
}