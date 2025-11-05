package dev.forsythe.patientmanagement.core.data.cache

import dev.forsythe.patientmanagement.core.data.preferences.SharedPreferences

class AccessTokenProvider(
    private val sharedPrefs: SharedPreferences,
) {

    private fun getAccessToken(): String? {
        // Read from cache first (
        val cachedToken = AppCache.get<String>(SharedPreferences.ACCESS_TOKEN)
        if (cachedToken != null) {
            return cachedToken
        }

        //Cache miss, read from SharedPreferences
        val prefsToken = sharedPrefs.getData(SharedPreferences.ACCESS_TOKEN)

        if (prefsToken.isNullOrBlank()) {
            //If it's not in prefs, there is no token.
            return null
        }

        //If we found  in prefs, save it to the cache
        AppCache.put(SharedPreferences.ACCESS_TOKEN, prefsToken)
        return prefsToken
    }
    fun requireAccessToken(): String {
        return getAccessToken() ?: throw Exception("No access token found")
    }

    fun saveAccessToken(token: String) {
        sharedPrefs.saveData(SharedPreferences.ACCESS_TOKEN, token)
        AppCache.put(SharedPreferences.ACCESS_TOKEN, token)
    }

}