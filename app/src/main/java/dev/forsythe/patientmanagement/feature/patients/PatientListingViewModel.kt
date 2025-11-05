package dev.forsythe.patientmanagement.feature.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.forsythe.patientmanagement.core.data.repo.PatientRepository
import dev.forsythe.patientmanagement.utils.convertMillisToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PatientListingViewModel(
    repository: PatientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientListingScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        // Set a default date for the filter UI
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        _uiState.update { it.copy(filterDate = today) }

        // Start collecting the patient list from the repository
        loadPatients(repository)
    }

    private fun loadPatients(repository: PatientRepository) {
        viewModelScope.launch {
            repository.getPatientListings()
                .catch { e ->
                    // Handle any errors from the Flow
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { patientList ->
                    // This 'collect' will automatically trigger
                    // every time the data changes in Room
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            patients = patientList
                        )
                    }
                }
        }
    }

    // This function is called from the UI's date picker
    fun onFilterDateChanged(dateMillis: Long) {
        val newDateString = convertMillisToDate(dateMillis)
        _uiState.update { it.copy(filterDate = newDateString) }

        // TODO: Implement filtering logic
        // This is where you would either filter the list client-side
        // or re-query the repository if it supported date filtering.
        // For now, it just updates the UI's date field.
    }

    fun onPatientClicked(patientId: String) {
       //TODO
    }
}