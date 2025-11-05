package dev.forsythe.patientmanagement.core.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.forsythe.patientmanagement.feature.assessment.AssessmentScreen
import dev.forsythe.patientmanagement.feature.auth.login.LoginScreen
import dev.forsythe.patientmanagement.feature.auth.sign_up.SignUpScreen
import dev.forsythe.patientmanagement.feature.patients.PatientListingScreen
import dev.forsythe.patientmanagement.feature.registration.PatientRegistrationScreen
import dev.forsythe.patientmanagement.feature.vitals.PatientVitalsScreen

@Composable
fun PmNavGraph(
    navController: NavHostController,
    startDestination: NavRoutes,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {


        composable<NavRoutes.Login>{
            LoginScreen(navController = navController)
        }

        composable<NavRoutes.SignUp>{
            SignUpScreen(navController = navController)
        }

        composable<NavRoutes.PatientsList> {
            PatientListingScreen(navController = navController)
        }


        composable<NavRoutes.Registration>(
            enterTransition = { slideInVertically(initialOffsetY = { it }) },
            exitTransition = { slideOutVertically(targetOffsetY = { it }) }

        ){
            PatientRegistrationScreen(navController = navController)
        }

        composable <NavRoutes.Assessment> (

        ){
            AssessmentScreen(
                navController = navController
            )
        }

        composable <NavRoutes.Vitals>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ){
            PatientVitalsScreen (
                navController = navController
            )
        }
    }

}