package dev.forsythe.patientmanagement.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.forsythe.patientmanagement.core.ui.components.CircularProgressIndicatorPm
import dev.forsythe.patientmanagement.core.ui.components.FormTextField
import dev.forsythe.patientmanagement.core.ui.components.PasswordTextField
import dev.forsythe.patientmanagement.core.ui.components.VerticalSpacer
import dev.forsythe.patientmanagement.core.ui.components.buttons.PrimaryButton
import dev.forsythe.patientmanagement.core.ui.components.buttons.SecondaryButton
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // --- Side Effects ---
    LaunchedEffect(key1 = uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            // On success, go to the patient list and clear the whole back stack
            navController.navigate(NavRoutes.PatientsList) {
                popUpTo(0) // Clears everything
            }
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(key1 = uiState.loginError) {
        uiState.loginError?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sign in to access patient records",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                VerticalSpacer(32)

                // Email
                FormTextField(
                    label = "Email",
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    placeholder = "Enter your email",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = uiState.loginError != null
                )
                VerticalSpacer(16)

                // Password
                PasswordTextField(
                    label = "Password",
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    placeholder = "Enter your password",
                    isError = uiState.loginError != null
                )

                VerticalSpacer(8)

                // Error message
                if (uiState.loginError != null) {
                    Text(
                        text = uiState.loginError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                }
                VerticalSpacer(24)

                // Login Button
                PrimaryButton(
                    text = "Login",
                    onClick = viewModel::onLoginClicked,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )
                VerticalSpacer(16)
                
                // Sign Up Button (Navigation)
                SecondaryButton(
                    text = "Don't have an account? Sign Up",
                    onClick = {
                        navController.navigate(NavRoutes.SignUp) // Navigates to SignUp
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )

                if (uiState.isLoading) {
                    VerticalSpacer(16)
                    CircularProgressIndicatorPm(
                        isLoading = true,
                        displayText = "Logging in..."
                    )
                }
            }
        }
    }
}