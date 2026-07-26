package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.Translations
import com.example.ui.theme.SageForest
import java.util.Locale

@Composable
fun BudgetCard(
    currentMonthSpent: Double,
    budgetLimit: Double,
    currentLanguage: AppLanguage,
    onEditBudgetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (budgetLimit > 0) (currentMonthSpent / budgetLimit).coerceIn(0.0, 1.0).toFloat() else 0f
    val percentage = if (budgetLimit > 0) ((currentMonthSpent / budgetLimit) * 100).toInt() else 0
    val remaining = (budgetLimit - currentMonthSpent).coerceAtLeast(0.0)

    val (statusTextKey, statusColor, statusBgColor) = when {
        currentMonthSpent > budgetLimit -> Triple(
            "budget_status_exceeded",
            Color(0xFFD32F2F),
            Color(0xFFFFEBEE)
        )
        currentMonthSpent >= budgetLimit * 0.85 -> Triple(
            "budget_status_warning",
            Color(0xFFE65100),
            Color(0xFFFFF3E0)
        )
        else -> Triple(
            "budget_status_on_track",
            SageForest,
            Color(0xFFE8F5E9)
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("budget_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SageForest.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PieChart,
                            contentDescription = null,
                            tint = SageForest,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = Translations.get("monthly_budget", currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = "${String.format(Locale.US, "%.0f", currentMonthSpent)} ${Translations.get("spent_of", currentLanguage)} ${String.format(Locale.US, "%.0f RON", budgetLimit)}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Status badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(statusBgColor)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = Translations.get(statusTextKey, currentLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = statusColor
                            )
                        )
                    }

                    IconButton(
                        onClick = onEditBudgetClick,
                        modifier = Modifier.testTag("edit_budget_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = Translations.get("edit_budget", currentLanguage),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = statusColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$percentage% ${Translations.get("budget_limit", currentLanguage).lowercase()}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Text(
                    text = "${Translations.get("budget_remaining", currentLanguage)}: ${String.format(Locale.US, "%.2f RON", remaining)}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (remaining > 0) SageForest else Color(0xFFD32F2F)
                    )
                )
            }
        }
    }
}
