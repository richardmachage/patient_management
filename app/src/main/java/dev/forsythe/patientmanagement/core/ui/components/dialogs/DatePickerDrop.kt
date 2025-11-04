package dev.forsythe.patientmanagement.core.ui.components.dialogs

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import dev.forsythe.patientmanagement.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerPm(
    showPicker: Boolean,
    initialDateMillis: Long? = null,
    onDateSelected: (Long) -> Unit,
    onDismissRequest: () -> Unit
) {
    // 1. State holder for the DatePicker
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { selected ->
                        onDateSelected(selected)
                    }
                    onDismissRequest()
                }) {
                    Text(
                        text = stringResource(R.string.ok)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = stringResource(R.string.cancel)
                    )
                }
            },
            properties = DialogProperties()
        ) {
            //The picker shown inside the dialog
            DatePicker(
                state = datePickerState,
                /*showModeToggle = false,
                title = {
                    Text(text = "Select date")
                }*/
            )
        }
    }
}