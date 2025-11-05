package dev.forsythe.patientmanagement.feature.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.forsythe.patientmanagement.core.data.repo.PatientRepository
import dev.forsythe.patientmanagement.core.data.room.entities.patients.PatientsEntity
import dev.forsythe.patientmanagement.utils.formatDateForDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

class PatientRegistrationViewModel(
    private val repository: PatientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientRegistrationScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        // Sets the default registration date
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        _uiState.update { it.copy(registrationDate = today) }

        //generate patient number
        _uiState.update { it.copy(patientNumber = UUID.randomUUID().toString()) }
    }


    fun onPatientNumberChange(number: String) {
        _uiState.update { it.copy(patientNumber = number, patientNumberError = null) }
    }

    fun onFirstNameChange(name: String) {
        _uiState.update { it.copy(firstName = name, firstNameError = null) }
    }

    fun onLastNameChange(name: String) {
        _uiState.update { it.copy(lastName = name, lastNameError = null) }
    }

    fun onDobChange(dob: String) {
        _uiState.update { it.copy(dob = dob, dobError = null) }
    }

    fun onGenderChange(gender: String) {
        _uiState.update { it.copy(gender = gender, genderError = null) }
    }

    fun onRegistrationDateChange(date: String) {
        _uiState.update { it.copy(registrationDate = date) }
    }




    fun onSaveClicked(
        onCompleted: () -> Unit
    ) {
        if (!validateInputs()) {
            return // Validation failed, errors are set
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, registrationError = null) }

            // Check if patient ID already exists
            if (repository.doesPatientExist(_uiState.value.patientNumber)) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        patientNumberError = "Patient with this number already exists"
                    )
                }
                return@launch
            }

            // Create the entity
            val newPatient = PatientsEntity(
                uniqueId = _uiState.value.patientNumber,
                firstname = _uiState.value.firstName,
                lastname = _uiState.value.lastName,
                dateOfBirth = formatDateForDb(_uiState.value.dob),
                gender = _uiState.value.gender,
                patientId = null,
                regDate = formatDateForDb(_uiState.value.registrationDate),
            )

            // Save to repository (which saves to Room)
            val result = repository.registerPatient(newPatient)

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRegistrationSuccessful = true
                    )
                }

                onCompleted()
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        registrationError = error.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (_uiState.value.patientNumber.isBlank()) {
            _uiState.update { it.copy(patientNumberError = "Patient number cannot be empty") }
            isValid = false
        }

        if (_uiState.value.firstName.isBlank()) {
            _uiState.update { it.copy(firstNameError = "First name cannot be empty") }
            isValid = false
        }

        if (_uiState.value.lastName.isBlank()) {
            _uiState.update { it.copy(lastNameError = "Last name cannot be empty") }
            isValid = false
        }

        if (_uiState.value.dob.isBlank()) {
            _uiState.update { it.copy(dobError = "Date of birth cannot be empty") }
            isValid = false
        }

        if (_uiState.value.gender.isBlank()) {
            _uiState.update { it.copy(genderError = "Gender must be selected") }
            isValid = false
        }

        return isValid
    }

}