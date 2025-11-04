package dev.forsythe.patientmanagement.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


val paddingSmall = 4.dp

val paddingMediumSmall = 6.dp

val paddingMedium = 8.dp

val paddingMediumLarge = 12.dp

val paddingLarge = 16.dp


@Composable
fun VerticalSpacer(height: Int) {
    Spacer(
        modifier = Modifier.height(height.dp)
    )
}


@Composable
fun HorizontalSpacer(width: Int) {
    Spacer(
        modifier = Modifier.width(width.dp)
    )
}