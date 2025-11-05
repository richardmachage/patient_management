package dev.forsythe.patientmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes
import dev.forsythe.patientmanagement.core.ui.navigation.PmNavGraph
import dev.forsythe.patientmanagement.core.ui.theme.PatientManagementTheme
import org.koin.android.ext.android.inject
import kotlin.getValue

class MainActivity : ComponentActivity() {

    val viewModel : MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
            .setKeepOnScreenCondition {
                viewModel.keepSplashScreen
            }

        enableEdgeToEdge()
        setContent {
            PatientManagementTheme {
                val navController = rememberNavController()


                LaunchedEffect(Unit) {
                    viewModel.onMainContentRendered()
                }
                PmNavGraph(
                    navController = navController,
                    startDestination = viewModel.getStartDestination()
                )
            }
        }
    }
}
