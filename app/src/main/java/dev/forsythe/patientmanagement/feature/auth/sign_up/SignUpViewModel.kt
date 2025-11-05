package dev.forsythe.patientmanagement.feature.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.forsythe.patientmanagement.core.data.repo.PatientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: PatientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpScreenState())
    val uiState = _uiState.asStateFlow()

    fun onFirstNameChange(name: String) {
        _uiState.update { it.copy(firstName = name, signUpError = null) }
    }
    
    fun onLastNameChange(name: String) {
        _uiState.update { it.copy(lastName = name, signUpError = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, signUpError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, signUpError = null) }
    }
    
    fun onConfirmPasswordChange(password: String) {
        _uiState.update { it.copy(confirmPassword = password, signUpError = null) }
    }

    fun onSignUpClicked() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, signUpError = null) }

            val result = repository.signup(
                email = _uiState.value.email,
                firstName = _uiState.value.firstName,
                lastName = _uiState.value.lastName,
                password = _uiState.value.password
            )

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSignUpSuccessful = true
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        signUpError = error.message ?: "An unknown sign-up error occurred"
                    )
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val state = _uiState.value
        var isValid = true

        if (state.firstName.isBlank() || state.lastName.isBlank()) {
            _uiState.update { it.copy(signUpError = "First and last name cannot be empty") }
            isValid = false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _uiState.update { it.copy(signUpError = "Please enter a valid email") }
            isValid = false
        }
        if (state.password.length < 6) { // API probably has a minimum
            _uiState.update { it.copy(signUpError = "Password must be at least 6 characters") }
            isValid = false
        }
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(signUpError = "Passwords do not match") }
            isValid = false
        }
        
        return isValid
    }

    // Called from the UI after handling the navigation
    fun onNavigationHandled() {
        _uiState.update { it.copy(isSignUpSuccessful = false) }
    }
}