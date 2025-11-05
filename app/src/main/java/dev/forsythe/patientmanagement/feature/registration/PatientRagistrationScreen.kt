package dev.forsythe.patientmanagement.feature.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.forsythe.patientmanagement.R
import dev.forsythe.patientmanagement.core.model.Gender
import dev.forsythe.patientmanagement.core.ui.components.CircularProgressIndicatorPm
import dev.forsythe.patientmanagement.core.ui.components.FormDatePicker
import dev.forsythe.patientmanagement.core.ui.components.FormDropdown
import dev.forsythe.patientmanagement.core.ui.components.FormTextField
import dev.forsythe.patientmanagement.core.ui.components.HorizontalSpacer
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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientRegistrationScreen(
    navController: NavController,
    viewModel: PatientRegistrationViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    //for the datePicker
    var openDatePicker by remember { mutableStateOf<OpenDatePicker?>(null) }

    LaunchedEffect(key1 = uiState.isRegistrationSuccessful) {
        if (uiState.isRegistrationSuccessful) {
            // Navigate on success
            navController.navigate(NavRoutes.Vitals(patientId = uiState.patientNumber)) //{
                // Clear this screen from the back stack
              //  popUpTo(NavRoutes.Registration) { inclusive = true }
           // }
        }
    }

    LaunchedEffect(key1 = uiState.registrationError) {
        uiState.registrationError?.let { error ->
            snackBarHostState.showSnackbar(message = error)
        }
    }


    openDatePicker?.let { pickerType ->

        // Determine which date string to use and which function to call
        val (initialDateString, onDateSelected) = when (pickerType) {
            OpenDatePicker.REGISTRATION_DATE -> uiState.registrationDate to viewModel::onRegistrationDateChange
            OpenDatePicker.DATE_OF_BIRTH -> uiState.dob to viewModel::onDobChange
        }

        DatePickerPm(
            showPicker = true,
            initialDateMillis = remember(initialDateString) {
                convertDateToMillis(initialDateString)
            },
            onDateSelected = { millis ->
                val newDateString = convertMillisToDate(millis)
                onDateSelected(newDateString) // Call the correct VM function
            },
            onDismissRequest = {
                openDatePicker = null // Close the dialog
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.new_patient_registration) )},
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {


            //patient number
            ReadOnlyTextField(
                label = stringResource(R.string.patient_number),
                value = uiState.patientNumber,
            )
            /*FormTextField(
                label = stringResource(R.string.patient_number),
                value = patientNumber,
                onValueChange = { patientNumber = it},
                placeholder = stringResource(R.string.patient_number_placeholder),
            )*/

            VerticalSpacer(15)
            
            // first name
            FormTextField(
                label = stringResource(R.string.first_name),
                value = uiState.firstName,
                onValueChange = { viewModel.onFirstNameChange(it) },
                placeholder = stringResource(R.string.first_name_placeholder)
            )
            VerticalSpacer(15)

            //last name
            FormTextField(
                label = stringResource(R.string.last_name),
                value = uiState.lastName,
                onValueChange = { viewModel.onLastNameChange(it) },
                placeholder = stringResource(R.string.last_name_placeholder)
            )
            VerticalSpacer(15)

            //registration date
            FormDatePicker(
                label = stringResource(R.string.registration_date),
                value = uiState.registrationDate,
                onDateClick = {
                    openDatePicker = OpenDatePicker.REGISTRATION_DATE
                }
            )
            VerticalSpacer(15)


            //date of birth
            FormDatePicker(
                label = stringResource(R.string.date_of_birth),
                value = uiState.dob,
                placeholder = "DD/MM/YYYY",
                onDateClick = {
                    openDatePicker = OpenDatePicker.DATE_OF_BIRTH
                }
            )
            VerticalSpacer(15)

            //gender
            FormDropdown(
                label = "Gender",
                options = Gender.entries.map { it.name },
                selectedOption = uiState.gender,
                onOptionSelected = { viewModel.onGenderChange(it) },
                placeholder = stringResource(R.string.gender_placeholder)
            )
            VerticalSpacer(15)

            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingMedium),
                contentAlignment = Alignment.BottomCenter
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingMedium),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //close button
                    SecondaryButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.close),
                        onClick = {
                            navController.navigateUp()
                        })
                    HorizontalSpacer(10)
                    //save button
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.save),
                        onClick = {

                            viewModel.onSaveClicked(
                                onCompleted = {
                                    navController.navigate(NavRoutes.Vitals(patientId = uiState.patientNumber))
                                }
                            )
                        },
                        enabled = !uiState.isLoading
                    )
                }

                if (uiState.isLoading) {
                    CircularProgressIndicatorPm(isLoading = true, displayText = "Loading")
                }
            }
        }
    }
}



private enum class OpenDatePicker {
    REGISTRATION_DATE,
    DATE_OF_BIRTH
}



@Preview(showBackground = true)
@Composable
private fun PatientRegistrationScreenPreview() {
    PatientManagementTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
           /* PatientRegistrationScreen(
                navController = rememberNavController()
            )*/
        }
    }

}