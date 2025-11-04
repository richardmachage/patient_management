package dev.forsythe.patientmanagement.feature.patients

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.forsythe.patientmanagement.core.model.BmiStatus
import java.lang.ProcessBuilder.Redirect.to

@Composable
fun BmiStatusChip(
    status: BmiStatus,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, contentColor) = when (status) {
        BmiStatus.Normal      -> Color.Green to Color.White
        BmiStatus.Overweight  -> Color.Red.copy(alpha = 0.3f) to Color.Red
        BmiStatus.Underweight -> Color.Blue.copy(alpha = 0.3f) to Color.Blue
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50.dp),
        color = backgroundColor,
        contentColor = contentColor
    ) {

        Text(
            text = status.name,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}