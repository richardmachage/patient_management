package dev.forsythe.patientmanagement.feature.assessment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.forsythe.patientmanagement.core.data.repo.PatientRepository
import dev.forsythe.patientmanagement.core.data.room.entities.assessment.AssessmentEntity
import dev.forsythe.patientmanagement.core.model.GeneralHealth
import dev.forsythe.patientmanagement.utils.formatDateForDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

class AssessmentViewModel(
    private val repository: PatientRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Get the navigation arguments
    private val patientId: String = checkNotNull(savedStateHandle["patientId"])
    private val assessmentTypeString: String = checkNotNull(savedStateHandle["assessmentType"])

    private val vitalId : String = checkNotNull(savedStateHandle["vitalId"])


    private val _uiState = MutableStateFlow(AssessmentScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        // Set the default visit date
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val assessmentType = when (assessmentTypeString) {
            "GENERAL" -> AssessmentType.GENERAL
            "OVERWEIGHT" -> AssessmentType.OVERWEIGHT
            else -> AssessmentType.UNKNOWN
        }
        val title = when (assessmentType) {
            AssessmentType.GENERAL -> "General Assessment"
            AssessmentType.OVERWEIGHT -> "Overweight Assessment"
            AssessmentType.UNKNOWN -> "Assessment"
        }

        _uiState.update {
            it.copy(
                visitDate = today,
                assessmentType = assessmentType,
                title = title
            )
        }

        // Load the patient's name
        loadPatientName()
    }

    private fun loadPatientName() {
        viewModelScope.launch {
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


    fun onVisitDateChange(date: String) {
        _uiState.update { it.copy(visitDate = date, visitDateError = null) }
    }

    fun onGeneralHealthChange(status: GeneralHealth) {
        _uiState.update { it.copy(generalHealth = status, generalHealthError = null) }
    }

    fun onCommentsChange(text: String) {
        _uiState.update { it.copy(comments = text) }
    }


    fun onDietChange(answer: Boolean) {
        _uiState.update {
            it.copy(
                onDiet = answer,
                conditionalQuestionError = null
            )
        }
    }

    fun onDrugsChange(answer: Boolean) {
        _uiState.update {
            it.copy(
                onDrugs = answer,
                conditionalQuestionError = null
            )
        }
    }


    fun onSaveClicked() {
        if (!validateInputs()) {
            return // Validation failed
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, saveError = null) }

            val currentState = _uiState.value
            
            // Create the single, unified entity
            val newAssessment = AssessmentEntity(
                id = UUID.randomUUID().toString(),
                vitalId = vitalId,
                visitDate = formatDateForDb(currentState.visitDate),
                generalHealth = currentState.generalHealth?: GeneralHealth.Poor,
                comments = currentState.comments,
                onDiet = currentState.onDiet,
                usingDrugs = currentState.onDrugs,
                patientId = patientId
            )

            val result = repository.saveAssessment(newAssessment)

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSaveSuccessful = true
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        saveError = error.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }

    // Call this from the UI after handling the navigation
    fun onNavigationHandled() {
        _uiState.update { it.copy(isSaveSuccessful = false) }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        val currentState = _uiState.value

        if (currentState.visitDate.isBlank()) {
            _uiState.update { it.copy(visitDateError = "Visit date cannot be empty") }
            isValid = false
        }

        if (currentState.generalHealth == null) {
            _uiState.update { it.copy(generalHealthError = "General health must be selected") }
            isValid = false
        }

        
        return isValid
    }
}