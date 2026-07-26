package com.example.ui.components

import android.app.DatePickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Expense
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.Translations
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditExpenseSheet(
    sheetState: SheetState,
    editingExpense: Expense?,
    currentLanguage: AppLanguage,
    onDismissRequest: () -> Unit,
    onSaveExpense: (id: Int, amount: Double, categoryKey: String, dateMillis: Long, description: String, currency: String, receiptUri: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var amountText by remember { mutableStateOf("") }
    var selectedCategoryKey by remember { mutableStateOf(CategoryUtils.categories.first().key) }
    var selectedDateMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var descriptionText by remember { mutableStateOf("") }
    var selectedReceiptUri by remember { mutableStateOf<String?>(null) }
    var amountError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedReceiptUri = uri.toString()
        }
    }

    LaunchedEffect(editingExpense) {
        if (editingExpense != null) {
            amountText = if (editingExpense.amount % 1.0 == 0.0) {
                editingExpense.amount.toInt().toString()
            } else {
                editingExpense.amount.toString()
            }
            selectedCategoryKey = editingExpense.categoryKey
            selectedDateMillis = editingExpense.dateMillis
            descriptionText = editingExpense.description
            selectedReceiptUri = editingExpense.receiptUri
        } else {
            amountText = ""
            selectedCategoryKey = CategoryUtils.categories.first().key
            selectedDateMillis = System.currentTimeMillis()
            descriptionText = ""
            selectedReceiptUri = null
        }
        amountError = null
        descriptionError = null
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier.testTag("add_edit_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (editingExpense != null) Translations.get("edit_expense", currentLanguage)
                    else Translations.get("add_expense", currentLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                IconButton(onClick = onDismissRequest) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = Translations.get("cancel", currentLanguage)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Amount Input
            Text(
                text = "${Translations.get("amount", currentLanguage)} (RON)",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    amountText = it
                    amountError = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("amount_input"),
                placeholder = { Text("0.00") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                isError = amountError != null,
                supportingText = amountError?.let { { Text(it) } },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Category Selection
            Text(
                text = Translations.get("category", currentLanguage),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryUtils.categories.forEach { cat ->
                    val isSelected = selectedCategoryKey.equals(cat.key, ignoreCase = true)
                    val catName = Translations.getCategoryName(cat.key, currentLanguage)

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) cat.color.copy(alpha = 0.2f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                            .border(
                                width = if (isSelected) 1.5.dp else 0.dp,
                                color = if (isSelected) cat.color else Color.Transparent,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedCategoryKey = cat.key }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("category_chip_${cat.key}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(cat.color.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = cat.icon,
                                contentDescription = null,
                                tint = cat.color,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = catName,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) cat.color else MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Date Picker Row
            Text(
                text = Translations.get("date", currentLanguage),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val now = System.currentTimeMillis()
                val isToday = isSameDay(selectedDateMillis, now)
                val isYesterday = isSameDay(selectedDateMillis, now - 86400000L)

                // Today Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (isToday) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable { selectedDateMillis = now }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = Translations.get("today", currentLanguage),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isToday) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                // Yesterday Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (isYesterday) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable { selectedDateMillis = now - 86400000L }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = Translations.get("yesterday", currentLanguage),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isYesterday) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                // Custom Date Button
                val formattedCustomDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    .format(Date(selectedDateMillis))

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (!isToday && !isYesterday) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable {
                            val cal = Calendar.getInstance().apply {
                                timeInMillis = selectedDateMillis
                            }
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val newCal = Calendar.getInstance().apply {
                                        set(Calendar.YEAR, year)
                                        set(Calendar.MONTH, month)
                                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                    }
                                    selectedDateMillis = newCal.timeInMillis
                                },
                                cal.get(Calendar.YEAR),
                                cal.get(Calendar.MONTH),
                                cal.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = if (!isToday && !isYesterday) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (!isToday && !isYesterday) formattedCustomDate else Translations.get("pick_date", currentLanguage),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (!isToday && !isYesterday) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Description Input
            Text(
                text = Translations.get("description", currentLanguage),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = descriptionText,
                onValueChange = {
                    descriptionText = it
                    descriptionError = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("description_input"),
                placeholder = { Text(Translations.get("description", currentLanguage)) },
                singleLine = true,
                isError = descriptionError != null,
                supportingText = descriptionError?.let { { Text(it) } },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Receipt Attachment Section
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = Translations.get("receipt_attachment", currentLanguage),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (selectedReceiptUri != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black.copy(alpha = 0.05f)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = selectedReceiptUri,
                            contentDescription = Translations.get("receipt_attachment", currentLanguage),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = Translations.get("change_receipt", currentLanguage))
                        }

                        IconButton(
                            onClick = { selectedReceiptUri = null },
                            modifier = Modifier.testTag("remove_receipt_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = Translations.get("remove_receipt", currentLanguage),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            } else {
                OutlinedButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("attach_receipt_btn"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Translations.get("attach_receipt", currentLanguage),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Button(
                onClick = {
                    val parsedAmount = amountText.toDoubleOrNull()
                    if (parsedAmount == null || parsedAmount <= 0) {
                        amountError = Translations.get("amount_error", currentLanguage)
                        return@Button
                    }
                    if (descriptionText.isBlank()) {
                        descriptionError = Translations.get("description_error", currentLanguage)
                        return@Button
                    }

                    onSaveExpense(
                        editingExpense?.id ?: 0,
                        parsedAmount,
                        selectedCategoryKey,
                        selectedDateMillis,
                        descriptionText.trim(),
                        "RON",
                        selectedReceiptUri
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("save_expense_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (editingExpense != null) Translations.get("update_expense", currentLanguage)
                        else Translations.get("save_expense", currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

private fun isSameDay(millis1: Long, millis2: Long): Boolean {
    val cal1 = Calendar.getInstance().apply { timeInMillis = millis1 }
    val cal2 = Calendar.getInstance().apply { timeInMillis = millis2 }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}
