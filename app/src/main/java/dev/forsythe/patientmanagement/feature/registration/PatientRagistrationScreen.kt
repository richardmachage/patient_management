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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.forsythe.patientmanagement.R
import dev.forsythe.patientmanagement.core.model.Gender
import dev.forsythe.patientmanagement.core.ui.components.FormDatePicker
import dev.forsythe.patientmanagement.core.ui.components.FormDropdown
import dev.forsythe.patientmanagement.core.ui.components.FormTextField
import dev.forsythe.patientmanagement.core.ui.components.HorizontalSpacer
import dev.forsythe.patientmanagement.core.ui.components.VerticalSpacer
import dev.forsythe.patientmanagement.core.ui.components.buttons.PrimaryButton
import dev.forsythe.patientmanagement.core.ui.components.buttons.SecondaryButton
import dev.forsythe.patientmanagement.core.ui.components.paddingMedium
import dev.forsythe.patientmanagement.core.ui.theme.PatientManagementTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientRegistrationScreen(
    onNavigateBack: () -> Unit,
    onSavePatient: (/*...patient data...*/) -> Unit,
    onClose: () -> Unit
) {

    var patientNumber by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var registrationDate by remember { mutableStateOf("24/07/2024") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.new_patient_registration) )},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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
            FormTextField(
                label = stringResource(R.string.patient_number),
                value = patientNumber,
                onValueChange = { patientNumber = it},
                placeholder = stringResource(R.string.patient_number_placeholder)
            )

            VerticalSpacer(15)
            
            // first name
            FormTextField(
                label = stringResource(R.string.first_name),
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = stringResource(R.string.first_name_placeholder)
            )
            VerticalSpacer(15)

            //last name
            FormTextField(
                label = stringResource(R.string.last_name),
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = stringResource(R.string.last_name_placeholder)
            )
            VerticalSpacer(15)

            //registration date
            FormDatePicker(
                label = stringResource(R.string.registration_date),
                value = registrationDate,
                onDateClick = {
                    // TODO: Show Date Picker Dialog
                }
            )
            VerticalSpacer(15)


            //date of birth
            FormDatePicker(
                label = stringResource(R.string.date_of_birth),
                value = dob,
                placeholder = "DD/MM/YYYY",
                onDateClick = {
                    // TODO: Show Date Picker Dialog
                }
            )
            VerticalSpacer(15)

            //gender
            FormDropdown(
                label = "Gender",
                options = Gender.entries.map { it.name },
                selectedOption = gender,
                onOptionSelected = { gender = it },
                placeholder = stringResource(R.string.gender_placeholder)
            )
            VerticalSpacer(15)

            Box (
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = paddingMedium),
                contentAlignment = Alignment.BottomCenter
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = paddingMedium),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //close button
                    SecondaryButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.close),
                        onClick = {
                            //TODO close
                        })
                    HorizontalSpacer(10)
                    //save button
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.save),
                        onClick = {})
                }

            }
        }
    }



}

@Preview(showBackground = true)
@Composable
private fun PatientRegistrationScreenPreview() {
    // You'd wrap this in your AppTheme
    PatientManagementTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PatientRegistrationScreen(
                onNavigateBack = {},
                onSavePatient = {},
                onClose = {}
            )
        }
    }

}