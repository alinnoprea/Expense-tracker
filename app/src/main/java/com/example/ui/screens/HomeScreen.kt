package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AdBannerCard
import com.example.ui.components.AddEditExpenseSheet
import com.example.ui.components.BudgetCard
import com.example.ui.components.CategoryAnalyticsSection
import com.example.ui.components.CategoryUtils
import com.example.ui.components.EditBudgetDialog
import com.example.ui.components.EmptyExpensesView
import com.example.ui.components.ExpenseDashboardCard
import com.example.ui.components.ExpenseListItem
import com.example.ui.components.LanguageToggleSwitch
import com.example.ui.components.ReceiptViewerDialog
import com.example.ui.i18n.Translations
import com.example.ui.theme.SageForest
import com.example.ui.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ExpenseViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsStateWithLifecycle()
    val filteredExpenses by viewModel.filteredExpenses.collectAsStateWithLifecycle()
    val totalAmount by viewModel.totalExpensesAmount.collectAsStateWithLifecycle()
    val categoryTotals by viewModel.categoryTotals.collectAsStateWithLifecycle()
    val topCategoryKey by viewModel.topCategory.collectAsStateWithLifecycle()
    val averageAmount by viewModel.averageExpenseAmount.collectAsStateWithLifecycle()

    val monthlyBudgetLimit by viewModel.monthlyBudgetLimit.collectAsStateWithLifecycle()
    val currentMonthTotalAmount by viewModel.currentMonthTotalAmount.collectAsStateWithLifecycle()
    val isBudgetDialogOpen by viewModel.isBudgetDialogOpen.collectAsStateWithLifecycle()
    val viewingReceiptUri by viewModel.viewingReceiptUri.collectAsStateWithLifecycle()

    val selectedCategoryFilter by viewModel.selectedCategoryFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val isAddEditSheetOpen by viewModel.isAddEditSheetOpen.collectAsStateWithLifecycle()
    val editingExpense by viewModel.editingExpense.collectAsStateWithLifecycle()
    val expenseToDelete by viewModel.expenseToDelete.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_scaffold"),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SageForest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = Translations.get("app_title", language),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = Translations.get("app_subtitle", language),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                },
                actions = {
                    LanguageToggleSwitch(
                        currentLanguage = language,
                        onLanguageSelected = { viewModel.setLanguage(it) },
                        modifier = Modifier.padding(end = 12.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openAddExpense() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(12.dp)
                    .testTag("add_expense_fab")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Translations.get("add_expense", language),
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input Field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_field"),
                    placeholder = {
                        Text(
                            text = Translations.get("search_placeholder", language),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }

            // Category Filter Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // All Chip
                item {
                    val isAllSelected = selectedCategoryFilter == null
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isAllSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surface
                            )
                            .clickable { viewModel.setCategoryFilter(null) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("filter_chip_all"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = Translations.get("all_categories", language),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isAllSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                items(CategoryUtils.categories) { cat ->
                    val isSelected = selectedCategoryFilter.equals(cat.key, ignoreCase = true)
                    val catName = Translations.getCategoryName(cat.key, language)

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) cat.color
                                else MaterialTheme.colorScheme.surface
                            )
                            .clickable {
                                if (isSelected) viewModel.setCategoryFilter(null)
                                else viewModel.setCategoryFilter(cat.key)
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("filter_chip_${cat.key}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = cat.icon,
                            contentDescription = null,
                            tint = if (isSelected) Color.White else cat.color,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = catName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }

            // Main List View with Dashboard & Logged Items
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("expense_list_lazy_column"),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Dashboard Card Header
                item {
                    ExpenseDashboardCard(
                        totalAmount = totalAmount,
                        entryCount = filteredExpenses.size,
                        topCategoryKey = topCategoryKey,
                        averageAmount = averageAmount,
                        currentLanguage = language
                    )
                }

                // Monthly Budget & Spending Limit Progress Card
                item {
                    BudgetCard(
                        currentMonthSpent = currentMonthTotalAmount,
                        budgetLimit = monthlyBudgetLimit,
                        currentLanguage = language,
                        onEditBudgetClick = { viewModel.openBudgetDialog() }
                    )
                }

                // Category Analytics Breakdown
                if (categoryTotals.isNotEmpty()) {
                    item {
                        CategoryAnalyticsSection(
                            categoryTotals = categoryTotals,
                            totalAmount = totalAmount,
                            selectedCategoryFilter = selectedCategoryFilter,
                            onCategorySelected = { viewModel.setCategoryFilter(it) },
                            currentLanguage = language
                        )
                    }
                }

                // Non-intrusive Ad Banner
                item {
                    AdBannerCard(currentLanguage = language)
                }

                // Header for Logged Items
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = Translations.get("recent_expenses", language),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )

                        Text(
                            text = "${filteredExpenses.size} ${Translations.get("logged_entries", language).lowercase()}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                // Empty State or List of Expenses
                if (filteredExpenses.isEmpty()) {
                    item {
                        EmptyExpensesView(
                            currentLanguage = language,
                            onAddClick = { viewModel.openAddExpense() },
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                } else {
                    items(
                        items = filteredExpenses,
                        key = { it.id }
                    ) { expense ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            ExpenseListItem(
                                expense = expense,
                                currentLanguage = language,
                                onEditClick = { viewModel.openEditExpense(it) },
                                onDeleteClick = { viewModel.requestDeleteExpense(it) },
                                onReceiptClick = { viewModel.openReceiptViewer(it) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Add / Edit Modal Bottom Sheet
    if (isAddEditSheetOpen) {
        AddEditExpenseSheet(
            sheetState = sheetState,
            editingExpense = editingExpense,
            currentLanguage = language,
            onDismissRequest = { viewModel.closeAddEditSheet() },
            onSaveExpense = { id, amount, categoryKey, dateMillis, description, currency, receiptUri ->
                viewModel.saveExpense(id, amount, categoryKey, dateMillis, description, currency, receiptUri)
            }
        )
    }

    // Edit Budget Limit Dialog
    if (isBudgetDialogOpen) {
        EditBudgetDialog(
            currentBudget = monthlyBudgetLimit,
            currentLanguage = language,
            onDismiss = { viewModel.closeBudgetDialog() },
            onConfirm = { newLimit -> viewModel.setMonthlyBudget(newLimit) }
        )
    }

    // Fullscreen Receipt Photo Viewer Dialog
    if (viewingReceiptUri != null) {
        ReceiptViewerDialog(
            receiptUri = viewingReceiptUri!!,
            currentLanguage = language,
            onDismiss = { viewModel.closeReceiptViewer() }
        )
    }

    // Delete Confirmation Dialog
    if (expenseToDelete != null) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDeleteDialog() },
            title = {
                Text(
                    text = Translations.get("confirm_delete_title", language),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Text(
                    text = Translations.get("confirm_delete_msg", language),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.confirmDeleteExpense() },
                    modifier = Modifier.testTag("confirm_delete_btn")
                ) {
                    Text(
                        text = Translations.get("delete", language),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissDeleteDialog() }) {
                    Text(text = Translations.get("cancel", language))
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
