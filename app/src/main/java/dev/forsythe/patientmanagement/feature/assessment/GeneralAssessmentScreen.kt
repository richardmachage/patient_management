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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.forsythe.patientmanagement.core.ui.components.FormDatePicker
import dev.forsythe.patientmanagement.core.ui.components.VerticalSpacer
import dev.forsythe.patientmanagement.core.ui.components.buttons.FormToggleButtonGroup
import dev.forsythe.patientmanagement.core.ui.components.buttons.PrimaryButton
import dev.forsythe.patientmanagement.core.ui.components.buttons.SecondaryButton
import dev.forsythe.patientmanagement.core.ui.components.paddingMedium
import dev.forsythe.patientmanagement.core.ui.components.texts.FormTextArea
import dev.forsythe.patientmanagement.core.ui.navigation.NavRoutes
import dev.forsythe.patientmanagement.core.ui.theme.PatientManagementTheme
import dev.forsythe.patientmanagement.feature.vitals.PatientInfoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreen(
   navController: NavController,
) {
    // --- State ---
    // These would typically live in a ViewModel
    var visitDate by remember { mutableStateOf("2023-10-27") }
    var generalHealth by remember { mutableStateOf("Good") }
    var onDiet by remember { mutableStateOf<String?>(null) }
    var comments by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(title = { Text("General Assessment") }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                )
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            VerticalSpacer(25)
            PatientInfoCard(patientName = "patientName")

            VerticalSpacer(25)

            FormDatePicker(
                label = "Visit Date", value = visitDate, onDateClick = {
                    // TODO: Show Date Picker Dialog
                })
            VerticalSpacer(25)

            FormToggleButtonGroup(
                label = "General health?",
                options = listOf("Good", "Poor"),
                selectedOption = generalHealth,
                onOptionSelected = { generalHealth = it })
            VerticalSpacer(25)

            FormToggleButtonGroup(
                label = "Have you ever been on a diet to lose weight?",
                options = listOf("Yes", "No"),
                selectedOption = onDiet,
                onOptionSelected = { onDiet = it })
            VerticalSpacer(25)

            FormTextArea(
                label = "Comments",
                value = comments,
                onValueChange = { comments = it },
                placeholder = "Enter any additional comments here..."
            )


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
                                navController.navigate(NavRoutes.Vitals(patientId = "thhsggsnn"))
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