package dev.forsythe.patientmanagement.feature.auth.sign_up

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
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.isSignUpSuccessful) {
        if (uiState.isSignUpSuccessful) {
            // On success, show a success message and pop back to Login
            snackbarHostState.showSnackbar(message = "Sign-up successful! Please log in.")
            navController.popBackStack()
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(key1 = uiState.signUpError) {
        uiState.signUpError?.let {
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
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Get started with your new account",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                VerticalSpacer(32)
                
                // First Name
                FormTextField(
                    label = "First Name",
                    value = uiState.firstName,
                    onValueChange = viewModel::onFirstNameChange,
                    placeholder = "Enter your first name",
                    isError = uiState.signUpError != null
                )
                VerticalSpacer(16)
                
                // Last Name
                FormTextField(
                    label = "Last Name",
                    value = uiState.lastName,
                    onValueChange = viewModel::onLastNameChange,
                    placeholder = "Enter your last name",
                    isError = uiState.signUpError != null
                )
                VerticalSpacer(16)

                // Email
                FormTextField(
                    label = "Email",
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    placeholder = "Enter your email",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = uiState.signUpError != null
                )
                VerticalSpacer(16)

                // Password
                PasswordTextField(
                    label = "Password",
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    placeholder = "Enter a password",
                    isError = uiState.signUpError != null
                )
                VerticalSpacer(16)
                
                // Confirm Password
                PasswordTextField(
                    label = "Confirm Password",
                    value = uiState.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    placeholder = "Confirm your password",
                    isError = uiState.signUpError != null
                )

                VerticalSpacer(8)

                // Error message
                if (uiState.signUpError != null) {
                    Text(
                        text = uiState.signUpError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                }
                VerticalSpacer(24)

                // Sign Up Button
                PrimaryButton(
                    text = "Sign Up",
                    onClick = viewModel::onSignUpClicked,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )
                VerticalSpacer(16)
                
                // Back to Login Button
                SecondaryButton(
                    text = "Already have an account? Log In",
                    onClick = {
                        navController.popBackStack() // Just go back to Login
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )

                if (uiState.isLoading) {
                    VerticalSpacer(16)
                    CircularProgressIndicatorPm(
                        isLoading = true,
                        displayText = "Creating account..."
                    )
                }
            }
        }
    }
}