package dev.forsythe.patientmanagement.feature.assessment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.forsythe.patientmanagement.core.model.GeneralHealth
import dev.forsythe.patientmanagement.core.ui.components.CircularProgressIndicatorPm
import dev.forsythe.patientmanagement.core.ui.components.FormDatePicker
import dev.forsythe.patientmanagement.core.ui.components.VerticalSpacer
import dev.forsythe.patientmanagement.core.ui.components.buttons.FormToggleButtonGroup
import dev.forsythe.patientmanagement.core.ui.components.buttons.PrimaryButton
import dev.forsythe.patientmanagement.core.ui.components.buttons.SecondaryButton
import dev.forsythe.patientmanagement.core.ui.components.dialogs.DatePickerPm
import dev.forsythe.patientmanagement.core.ui.components.paddingMedium
import dev.forsythe.patientmanagement.core.ui.components.texts.FormTextArea
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes
import dev.forsythe.patientmanagement.core.ui.theme.PatientManagementTheme
import dev.forsythe.patientmanagement.feature.vitals.PatientInfoCard
import dev.forsythe.patientmanagement.utils.convertDateToMillis
import dev.forsythe.patientmanagement.utils.convertMillisToDate
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreen(
   navController: NavController,
   viewModel: AssessmentViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var showVisitDatePicker by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = uiState.isSaveSuccessful) {
        if (uiState.isSaveSuccessful) {
            // On success, go to the patient list and clear the back stack
            navController.navigate(NavRoutes.PatientsList) {
                popUpTo(NavRoutes.PatientsList) { inclusive = true }
            }
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(key1 = uiState.saveError) {
        uiState.saveError?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    if (showVisitDatePicker) {
        DatePickerPm(
            showPicker = true,
            initialDateMillis = convertDateToMillis(uiState.visitDate),
            onDateSelected = { millis ->
                viewModel.onVisitDateChange(convertMillisToDate(millis))
            },
            onDismissRequest = {
                showVisitDatePicker = false
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
        TopAppBar(title = { Text("General Assessment") }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                )
            }
        })
    }) { paddingValues ->

        if (uiState.isLoading) {
            VerticalSpacer(8)
            CircularProgressIndicatorPm(
                isLoading = true,
                displayText = "Saving Assessment..."
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            VerticalSpacer(25)
            PatientInfoCard(patientName = uiState.patientName)

            VerticalSpacer(25)

            //visit Date
            FormDatePicker(
                label = "Visit Date",
                value = uiState.visitDate,
                onDateClick = {
                    showVisitDatePicker = true
                })
            VerticalSpacer(25)

            FormToggleButtonGroup(
                label = "General health?",
                options = GeneralHealth.entries.map { it.name },
                selectedOption = uiState.generalHealth?.name,
                onOptionSelected = { viewModel.onGeneralHealthChange(GeneralHealth.valueOf(it)) })
            VerticalSpacer(25)



            /*FormToggleButtonGroup(
                label = "Have you ever been on a diet to lose weight?",
                options = listOf("Yes", "No"),
                selectedOption = onDiet,
                onOptionSelected = { onDiet = it })
*/

            when (uiState.assessmentType) {
                AssessmentType.GENERAL -> {
                    FormToggleButtonGroup(
                        label = "Have you ever been on a diet to lose weight?",
                        options = listOf("Yes", "No"),
                        selectedOption = if (uiState.onDiet) "Yes" else "No",
                        onOptionSelected = {
                            // Map String back to Boolean
                            viewModel.onDietChange(it == "Yes")
                        },
                    )
                }

                AssessmentType.OVERWEIGHT -> {
                    FormToggleButtonGroup(
                        label = "Are you currently taking any drugs?",
                        options = listOf("Yes", "No"),
                        selectedOption = if (uiState.onDrugs) "Yes" else "No",
                        onOptionSelected = {
                            // Map String back to Boolean
                            viewModel.onDrugsChange(it == "Yes")
                        },
                    )
                }

                AssessmentType.UNKNOWN -> {
                    // This case should not be reachable if Vitals logic is correct
                    Text(
                        "Error: Unknown assessment type.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            VerticalSpacer(25)

            //comments
            FormTextArea(
                label = "Comments",
                value = uiState.comments,
                onValueChange = { viewModel.onCommentsChange(it)},
                placeholder = "Enter any additional comments here..."
            )

            VerticalSpacer(25)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingMedium),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SecondaryButton(
                            text = "Close", onClick = {
                                navController.navigateUp()
                            }, modifier = Modifier.weight(1f)
                        )

                        //save button
                        PrimaryButton(
                            text = "Save",
                            onClick = {
                                viewModel.onSaveClicked()
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun GeneralAssessmentScreenPreview() {

    PatientManagementTheme {

        Surface(color = MaterialTheme.colorScheme.background) {
            AssessmentScreen(
                navController = rememberNavController()
            )
        }
    }
}