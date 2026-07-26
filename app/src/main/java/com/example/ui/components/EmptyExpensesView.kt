package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.Translations
import com.example.ui.theme.SageForest
import com.example.ui.theme.SageLightContainer

@Composable
fun EmptyExpensesView(
    currentLanguage: AppLanguage,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .testTag("empty_expenses_view"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Organic Eco Leaf/Wallet Artwork using Compose Canvas
        val leafBgColor = SageLightContainer
        val leafBorderColor = SageForest

        Canvas(modifier = Modifier.size(100.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width * 0.4f

            // Background Circle
            drawCircle(
                color = leafBgColor,
                radius = radius,
                center = center
            )

            // Outline Ring
            drawCircle(
                color = leafBorderColor,
                radius = radius,
                center = center,
                style = Stroke(width = 4f)
            )

            // Wallet Body
            val wWidth = size.width * 0.4f
            val wHeight = size.height * 0.3f
            val wLeft = center.x - wWidth / 2
            val wTop = center.y - wHeight / 2

            drawRoundRect(
                color = leafBorderColor,
                topLeft = Offset(wLeft, wTop),
                size = Size(wWidth, wHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
            )

            // Leaf Accent Line
            drawLine(
                color = Color.White,
                start = Offset(center.x - 10f, center.y),
                end = Offset(center.x + 10f, center.y),
                strokeWidth = 4f
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = Translations.get("no_expenses_title", currentLanguage),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = Translations.get("no_expenses_desc", currentLanguage),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.testTag("empty_add_expense_btn")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = Translations.get("add_expense", currentLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}
