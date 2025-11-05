package dev.forsythe.patientmanagement.core.ui.components

import android.R.attr.label
import android.R.attr.singleLine
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormDatePicker(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onDateClick: () -> Unit,
    placeholder: String = "",
    onClearClick: (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = {}, // Not editable
            readOnly = true,
            placeholder = { Text(placeholder) },
            trailingIcon = {
                Row {

                    IconButton(
                        onClick = onDateClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Select Date"
                        )
                    }

                    if (onClearClick != null && value.isNotEmpty()) {
                        IconButton(onClick = onClearClick) {
                            Icon(
                                imageVector = Icons.Default.Clear, contentDescription = "Clear date"
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                //.clickable(onClick = onDateClick)
            , // Make the whole field clickable
            singleLine = true,
            shape = RoundedCornerShape(30)

        )
    }
}