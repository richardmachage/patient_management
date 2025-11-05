package dev.forsythe.patientmanagement.feature.auth.sign_up

data class SignUpScreenState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val signUpError: String? = null,
    val isSignUpSuccessful: Boolean = false
)