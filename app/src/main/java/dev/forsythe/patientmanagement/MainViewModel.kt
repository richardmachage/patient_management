package dev.forsythe.patientmanagement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.forsythe.patientmanagement.core.data.cache.AccessTokenProvider
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes

class MainViewModel(
    private val accessTokenProvider: AccessTokenProvider
) : ViewModel() {



    var keepSplashScreen by mutableStateOf(true)


    fun isUserLoggedIn() : Boolean = try {
        accessTokenProvider.requireAccessToken().isNotEmpty()
    }catch (e: Exception){
        false
    }finally {
        keepSplashScreen = false
    }

    fun  getStartDestination(): NavRoutes {
        return if (isUserLoggedIn()) {
            NavRoutes.PatientsList
        } else {
            NavRoutes.Login
        }
    }

}