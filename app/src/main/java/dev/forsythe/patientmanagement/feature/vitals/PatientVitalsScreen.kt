package dev.forsythe.patientmanagement.feature.vitals

import android.R.attr.height
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.forsythe.patientmanagement.R
import dev.forsythe.patientmanagement.core.ui.components.CircularProgressIndicatorPm
import dev.forsythe.patientmanagement.core.ui.components.FormDatePicker
import dev.forsythe.patientmanagement.core.ui.components.FormTextFieldWithUnit
import dev.forsythe.patientmanagement.core.ui.components.VerticalSpacer
import dev.forsythe.patientmanagement.core.ui.components.buttons.PrimaryButton
import dev.forsythe.patientmanagement.core.ui.components.buttons.SecondaryButton
import dev.forsythe.patientmanagement.core.ui.components.dialogs.DatePickerPm
import dev.forsythe.patientmanagement.core.ui.components.paddingMedium
import dev.forsythe.patientmanagement.core.ui.components.texts.ReadOnlyTextField
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes
import dev.forsythe.patientmanagement.core.ui.theme.PatientManagementTheme
import dev.forsythe.patientmanagement.utils.convertDateToMillis
import dev.forsythe.patientmanagement.utils.convertMillisToDate
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientVitalsScreen(
    navController: NavController,
    viewModel: VitalsViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    //state for Dialog
    var showVisitDatePicker by remember { mutableStateOf(false) }

    //Navigation
    LaunchedEffect(key1 = uiState.navigationEvent) {
        uiState.navigationEvent?.let { event ->
            when (event) {
                is VitalsNavigationEvent.ToAssessment -> {
                    navController.navigate(
                        NavRoutes.Assessment(
                            patientId = event.patientId, assessmentType = event.assessmentType, vitalId = event.vitalId
                        )
                    ) {
                        // Pop this screen off the back stack
                        popUpTo(NavRoutes.Vitals(patientId = event.patientId)) { inclusive = true }
                    }
                }
            }
            viewModel.onNavigationHandled() // Consume the event
        }
    }

    //errors
    LaunchedEffect(key1 = uiState.saveError) {
        uiState.saveError?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    // Date Picker
    if (showVisitDatePicker) {
        DatePickerPm(
            showPicker = true,
            initialDateMillis = convertDateToMillis(uiState.visitDate),
            onDateSelected = { millis ->
                viewModel.onVisitDateChange(convertMillisToDate(millis))
            },
            onDismissRequest = {
                showVisitDatePicker = false
            })
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Patient Vitals") }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        })
    }, snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface,
        ) {

        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            //verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //patient info
            VerticalSpacer(25)
            PatientInfoCard(patientName = uiState.patientName)

            VerticalSpacer(25)


            //visit date
            FormDatePicker(
                label = stringResource(R.string.visit_date),
                value = uiState.visitDate,
                onDateClick = {
                    showVisitDatePicker = true
                })

            VerticalSpacer(25)

            // Height and Weight
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormTextFieldWithUnit(
                    label = "Height",
                    value = uiState.height,
                    onValueChange = { viewModel.onHeightChange(it) },
                    unit = "M",
                    keyboardType = KeyboardType.Number,
                    isError = uiState.heightError != null,
                    errorMessage = uiState.heightError,
                    modifier = Modifier.weight(1f)
                )

                FormTextFieldWithUnit(
                    label = "Weight",
                    value = uiState.weight,
                    onValueChange = { viewModel.onWeightChange(it) },
                    unit = "KG",
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f),
                    isError = uiState.weightError != null,
                    errorMessage = uiState.weightError,
                )
            }


            VerticalSpacer(25)

            //BMi
            ReadOnlyTextField(
                label = "BMI",
                value = uiState.bmi
            )


            VerticalSpacer(25)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingMedium),
                contentAlignment = Alignment.BottomCenter
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
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isLoading
                    )
                    PrimaryButton(
                        text = "Save",
                        onClick = {
                            viewModel.onSaveClicked()
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isLoading
                    )
                }
            }

            if (uiState.isLoading) {
                VerticalSpacer(8)
                CircularProgressIndicatorPm(
                    isLoading = true,
                    displayText = "Saving Vitals..."
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PatientVitalsScreenPreview() {

    PatientManagementTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PatientVitalsScreen(
                navController = rememberNavController()
            )
        }
    }
}