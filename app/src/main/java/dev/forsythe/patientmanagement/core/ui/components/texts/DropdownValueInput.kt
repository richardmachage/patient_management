package dev.forsythe.patientmanagement.core.ui.components.texts

import android.R.attr.label
import android.R.attr.singleLine
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownValueInput(
    modifier: Modifier = Modifier,
    initialValue: String = "",
    // List of options for the dropdown
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    // State to hold the current text input value
    var text by remember { mutableStateOf(initialValue) }
    // State to control the expanded/collapsed state of the dropdown menu
    var expanded by remember { mutableStateOf(false) }

    // ExposedDropdownMenuBox is the foundation for a searchable/editable dropdown
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier.fillMaxWidth(0.5f)
    ) {

        OutlinedTextField(
            // 'menuAnchor() modifier to anchor the dropdown to this TextField
            value = text,
            onValueChange = { newValue ->
                text = newValue
                onValueChange(newValue)
            },
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            ),
            shape = RoundedCornerShape(50),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // Change to Text or NumberDecimal as needed
            
            // 2. Trailing Icon: The arrow down button
            trailingIcon = {
                IconButton(
                    modifier = Modifier.menuAnchor(
                        enabled = true,
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable
                    ),

                    onClick = {
                      //  expanded = !expanded
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",
                    )
                }
            },
            
            singleLine = true,
            label = null,
            placeholder = {
                Text(
                    text = if (options.isEmpty())"" else "Select",
                    style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )

        //The Dropdown Menu
        if (options.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            // Update both the text state and notify the caller
                            text = selectionOption
                            onValueChange(selectionOption)
                            // Close the dropdown after selection
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DropdownValueInputPreview() {
    MaterialTheme {
        DropdownValueInput(
            options = listOf("Option 1", "Option 2", "Option 3"),
            onValueChange = {}
        )
    }
}