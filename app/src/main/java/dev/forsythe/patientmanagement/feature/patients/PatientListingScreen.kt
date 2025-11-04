package dev.forsythe.patientmanagement.feature.patients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.forsythe.patientmanagement.core.ui.components.EmptyState
import dev.forsythe.patientmanagement.core.ui.components.FormDatePicker
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes
import dev.forsythe.patientmanagement.core.ui.theme.PatientManagementTheme
import dev.forsythe.patientmanagement.feature.patients.model.PatientListingItem
import dev.forsythe.patientmanagement.feature.patients.model.mockPatients

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListingScreen(
    navController: NavController,
    patients: List<PatientListingItem> = emptyList(),
) {
    // --- State ---
    // This would typically live in a ViewModel
    var filterDate by remember { mutableStateOf("October 26, 2023") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Patient Records") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick ={
                    navController.navigate(NavRoutes.Registration)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add new patient"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormDatePicker(
                label = "Filter by visit date",
                value = filterDate,
                onDateClick = {
                    // TODO: Show Date Picker Dialog
                }
            )

            if (patients.isEmpty()) {
                EmptyState(
                    message = "No records found",
                    subMessage = "Select a different date or add a new patient."
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(patients) { patient ->
                        PatientListItem(
                            patient = patient,
                            onClick = {
                                //TODO onPatientClick(patient.patientId)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PatientListingScreenPreview() {
    PatientManagementTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PatientListingScreen(
                patients = mockPatients,
                navController = rememberNavController(),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PatientListingScreenEmptyPreview() {

    PatientManagementTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PatientListingScreen(
                patients = emptyList(),
                navController = rememberNavController(),
            )
        }
    }
}