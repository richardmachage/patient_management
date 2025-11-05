package dev.forsythe.patientmanagement.feature.registration

data class PatientRegistrationScreenState(
    val patientNumber: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val registrationDate: String = "", // We'll set the default in the VM
    val dob: String = "",
    val gender: String = "",
    val isLoading: Boolean = false,
    val patientNumberError: String? = null,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val dobError: String? = null,
    val genderError: String? = null,
    val registrationError: String? = null, // For general registration errors
    val isRegistrationSuccessful: Boolean = false // To trigger navigation
)
