package dev.forsythe.patientmanagement.core.data.cache

import dev.forsythe.patientmanagement.core.data.preferences.SharedPreferences

class AccessTokenProvider(
    private val sharedPrefs: SharedPreferences,
) {

    private fun getAccessToken(): String? {
        return AppCache.get<String>(SharedPreferences.ACCESS_TOKEN)?.let {
                //fetch from shared prefs
                sharedPrefs.getData(SharedPreferences.ACCESS_TOKEN).also {
                    //save to cache
                    AppCache.put(SharedPreferences.ACCESS_TOKEN, it)
                }
            }
    }

    fun requireAccessToken(): String {
        return getAccessToken() ?: throw Exception("No access token found")
    }

}