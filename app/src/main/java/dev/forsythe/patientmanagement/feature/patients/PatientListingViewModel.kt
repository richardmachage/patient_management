package dev.forsythe.patientmanagement.feature.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.forsythe.patientmanagement.core.data.repo.PatientRepository
import dev.forsythe.patientmanagement.utils.convertMillisToDate
import dev.forsythe.patientmanagement.utils.formatDateForDb
import dev.forsythe.patientmanagement.utils.getTodayDateString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PatientListingViewModel(
    private val repository: PatientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientListingScreenState())
    val uiState = _uiState.asStateFlow()

    private val _filterDate = MutableStateFlow<String?>(null)

    init {
        _uiState.update { it.copy(filterDate = "") }

        // Start collecting the patient list from the repository
        loadPatients()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadPatients() {
        viewModelScope.launch {

            _filterDate.flatMapLatest {dateInDbFormat->
                repository.getPatientListings(filterDate = dateInDbFormat)
            }
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect{patientList->
                    _uiState.update {
                        it.copy(
                            isLoading = false, patients = patientList
                        )
                    }
                }
        }
    }

    // This function is called from the UI's date picker
    fun onFilterDateChanged(dateMillis: Long) {
        val uiDateString = convertMillisToDate(dateMillis)

        val dbDateString = formatDateForDb(uiDateString)

        _uiState.update { it.copy(filterDate = uiDateString) }

        //Update the filterDate flow to triggers the query
        _filterDate.value = dbDateString
    }


    fun onClearFilter() {
        _uiState.update { it.copy(filterDate = "") } // Clear the UI
        _filterDate.value = null
    }
    fun onPatientClicked(patientId: String) {
       //TODO
    }
}