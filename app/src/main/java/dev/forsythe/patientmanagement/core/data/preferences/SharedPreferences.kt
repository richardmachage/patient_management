package dev.forsythe.patientmanagement.core.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferences(
    context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_FILE_KEY = "patientManagement.SHARED_PREFERENCES"
        const val ACCESS_TOKEN = "access_token"
    }


    fun saveData(key: String, value: String) {
        sharedPreferences.edit(commit = true) {
            putString(key, value)
        }
    }


    fun getData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun deleteData(key: String) {
        sharedPreferences
            .edit(commit = true) {
                remove(key)
            }
    }

    fun clearAll() {
        sharedPreferences.edit(commit = true) { clear() }
    }
}