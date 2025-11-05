package dev.forsythe.patientmanagement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.forsythe.patientmanagement.core.data.cache.AccessTokenProvider
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes
import timber.log.Timber

private const val TAG = "MainViewModel"
class MainViewModel(
    private val accessTokenProvider: AccessTokenProvider,
) : ViewModel() {

    var keepSplashScreen by mutableStateOf(true)
        private set

    fun isUserLoggedIn(): Boolean = try {
        Timber.tag(TAG).d("Checking if user is logged in")
        val hasToken = accessTokenProvider.requireAccessToken().isNotEmpty()
        Timber.tag(TAG).d("User is logged in: $hasToken")
        hasToken
    } catch (e: Exception) {
        Timber.tag(TAG).e(e, "Failed to check login status")
        false
    }

    fun getStartDestination(): NavRoutes {
        Timber.tag(TAG).d("Getting start destination")
        return if (isUserLoggedIn()) {
            Timber.tag(TAG).d("Navigating to PatientsList")
            NavRoutes.PatientsList
        } else {
            Timber.tag(TAG).d("Navigating to Login")
            NavRoutes.Login
        }
    }


    fun onMainContentRendered() {
        keepSplashScreen = false
    }
}