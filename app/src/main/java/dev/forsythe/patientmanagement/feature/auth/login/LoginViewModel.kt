package dev.forsythe.patientmanagement.feature.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.forsythe.patientmanagement.core.data.preferences.SharedPreferences
import dev.forsythe.patientmanagement.core.data.repo.PatientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: PatientRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, loginError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, loginError = null) }
    }

    fun onLoginClicked() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, loginError = null) }

            val result = repository.login(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginError = error.message ?: "An unknown login error occurred"
                    )
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        if (_uiState.value.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()) {
            _uiState.update { it.copy(loginError = "Please enter a valid email") }
            isValid = false
        }
        if (_uiState.value.password.isBlank()) {
            _uiState.update { it.copy(loginError = "Password cannot be empty") }
            isValid = false
        }
        return isValid
    }

    // Call this from the UI after handling the navigation
    fun onNavigationHandled() {
        _uiState.update { it.copy(isLoginSuccessful = false) }
    }
}