package dev.forsythe.patientmanagement.feature.vitals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.forsythe.patientmanagement.core.data.repo.PatientRepository
import dev.forsythe.patientmanagement.core.data.room.entities.vitals.VitalsEntity
import dev.forsythe.patientmanagement.utils.formatDateForDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

class VitalsViewModel(
    private val repository: PatientRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Get the patientId from navigation
    private val patientId: String = checkNotNull(savedStateHandle["patientId"])

    private val _uiState = MutableStateFlow(PatientVitalsScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        // Set the default visit date
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        _uiState.update { it.copy(visitDate = today) }

        // Load the patient's name for the info card
        loadPatientName()
    }

    private fun loadPatientName() {
        viewModelScope.launch {
            // NOW this is correct
            val patient = repository.getPatient(patientId)
            if (patient != null) {
                _uiState.update {
                    it.copy(patientName = "${patient.firstname} ${patient.lastname}")
                }
            } else {
                _uiState.update { it.copy(patientName = "Patient not found") }
            }
        }
    }


    // --- Event Handlers ---

    fun onVisitDateChange(date: String) {
        _uiState.update { it.copy(visitDate = date, visitDateError = null) }
    }

    fun onHeightChange(height: String) {
        _uiState.update { it.copy(height = height, heightError = null) }
        calculateBmi()
    }

    fun onWeightChange(weight: String) {
        _uiState.update { it.copy(weight = weight, weightError = null) }
        calculateBmi()
    }

    private fun calculateBmi() {
        val heightStr = _uiState.value.height
        val weightStr = _uiState.value.weight

        // Your VitalsEntity uses height in m, so we'll do the same
        val heightInMeters = heightStr.toDoubleOrNull()
        val weightInKg = weightStr.toDoubleOrNull()

        if (heightInMeters != null && weightInKg != null && heightInMeters > 0) {
            val bmi = weightInKg / (heightInMeters * heightInMeters)
            _uiState.update { it.copy(bmi = String.format("%.1f", bmi)) }
        } else {
            _uiState.update { it.copy(bmi = "") }
        }
    }

    fun onSaveClicked() {
        // the height in meters for validation
        val heightDouble = _uiState.value.height.toDoubleOrNull()
        val weightDouble = _uiState.value.weight.toDoubleOrNull()

        if (!validateInputs(heightDouble, weightDouble)) {
            return // Validation failed
        }

        val bmiDouble = _uiState.value.bmi.toDoubleOrNull() ?: 0.0

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, saveError = null) }
            
            val newVitals = VitalsEntity(
                id = UUID.randomUUID().toString(),
                patientId = patientId, // The ID from navigation
                visitDate = formatDateForDb(_uiState.value.visitDate),
                height = heightDouble!!, // Saving as meters
                weight = weightDouble!!
            )
            
            val result = repository.saveVitals(newVitals)
            
            result.onSuccess {
                // Conditional Navigation Logic
                val assessmentType = if (bmiDouble <= 25.0) "GENERAL" else "OVERWEIGHT"
                
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        navigationEvent = VitalsNavigationEvent.ToAssessment(
                            patientId = patientId,
                            assessmentType = assessmentType
                        )
                    ) 
                }
            }.onFailure { error ->
                _uiState.update { 
                    it.copy(isLoading = false, saveError = error.message) 
                }
            }
        }
    }
    
    // Call this from the UI after handling the navigation
    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = null) }
    }

    private fun validateInputs(heightInM: Double?, weight: Double?): Boolean {
        var isValid = true
        if (_uiState.value.visitDate.isBlank()) {
            _uiState.update { it.copy(visitDateError = "Visit date cannot be empty") }
            isValid = false
        }
        // Assuming height is in meters, check for a reasonable value
        if (heightInM == null || heightInM <= 0 || heightInM > 3) {
            _uiState.update { it.copy(heightError = "Invalid height (in meters)") }
            isValid = false
        }
        if (weight == null || weight <= 0) {
            _uiState.update { it.copy(weightError = "Invalid weight") }
            isValid = false
        }
        return isValid
    }

}