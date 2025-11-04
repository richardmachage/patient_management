package dev.forsythe.patientmanagement.feature.vitals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.forsythe.patientmanagement.R
import dev.forsythe.patientmanagement.core.ui.components.FormDatePicker
import dev.forsythe.patientmanagement.core.ui.components.FormTextFieldWithUnit
import dev.forsythe.patientmanagement.core.ui.components.VerticalSpacer
import dev.forsythe.patientmanagement.core.ui.components.buttons.PrimaryButton
import dev.forsythe.patientmanagement.core.ui.components.buttons.SecondaryButton
import dev.forsythe.patientmanagement.core.ui.components.paddingMedium
import dev.forsythe.patientmanagement.core.ui.components.texts.ReadOnlyTextField
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes
import dev.forsythe.patientmanagement.core.ui.theme.PatientManagementTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientVitalsScreen(
    navController: NavController,
) {
    // --- State ---
    // These would typically live in a ViewModel
    var visitDate by remember { mutableStateOf("23/10/2023") }
    var height by remember { mutableStateOf("") } // Start empty to show error
    var weight by remember { mutableStateOf("70") }
    var bmi by remember { mutableStateOf("22.9") } // This would be calculated
    var heightError by remember { mutableStateOf<String?>(null) }

    // Mockup shows an error, so let's simulate it
    // In real code, you'd validate this on save
    heightError = if (height.isBlank()) "This field cannot be empty." else null


    Scaffold(topBar = {
        TopAppBar(title = { Text("Patient Vitals") }, navigationIcon = {
            IconButton(onClick = {navController.navigateUp()}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        })
    }, bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface,
        ) {

        }
    }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
            //verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //patient info
            VerticalSpacer(25)
            PatientInfoCard(patientName = "patientName")

            VerticalSpacer(25)


            FormDatePicker(
                label = stringResource(R.string.visit_date), value = visitDate, onDateClick = {
                    // TODO: Show Date Picker Dialog
                })

            VerticalSpacer(25)

            // Height and Weight
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormTextFieldWithUnit(
                    label = "Height",
                    value = height,
                    onValueChange = { height = it },
                    unit = "CM",
                    keyboardType = KeyboardType.Number,
                    isError = heightError == null,
                    errorMessage = heightError,
                    modifier = Modifier.weight(1f)
                )

                FormTextFieldWithUnit(
                    label = "Weight",
                    value = weight,
                    onValueChange = { weight = it },
                    unit = "KG",
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f)
                )
            }


            VerticalSpacer(25)

            ReadOnlyTextField(
                label = "BMI", value = bmi
            )


            Box (
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = paddingMedium),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SecondaryButton(
                        text = "Close", onClick = {
                            navController.navigateUp()
                        }, modifier = Modifier.weight(1f)
                    )
                    PrimaryButton(
                        text = "Save",
                        onClick = {
                            navController.navigate(NavRoutes.PatientsList)
                        }, modifier = Modifier.weight(1f)
                    )
                }
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