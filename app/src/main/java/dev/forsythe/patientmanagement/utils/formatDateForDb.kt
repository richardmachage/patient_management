package dev.forsythe.patientmanagement.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatDateForDb(date: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE // "YYYY-MM-DD"
        LocalDate.parse(date, inputFormatter).format(outputFormatter)
    } catch (e: Exception) {
        //  return original if format is wrong
        date
    }
}



private val uiDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
 fun convertMillisToDate(millis: Long): String {
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(uiDateFormatter)
}

 fun convertDateToMillis(dateString: String): Long? {
    return try {
        val localDate = LocalDate.parse(dateString, uiDateFormatter)
        localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    } catch (e: Exception) {
        null // Return null if string is empty or invalid
    }
}


fun getTodayDateString(): String {
    return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}