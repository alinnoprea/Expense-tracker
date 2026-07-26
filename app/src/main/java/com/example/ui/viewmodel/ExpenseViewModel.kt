package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.Expense
import com.example.data.repository.ExpenseRepository
import com.example.ui.i18n.AppLanguage
import java.util.Calendar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    private val prefs = application.getSharedPreferences("expense_tracker_prefs", Context.MODE_PRIVATE)

    init {
        val expenseDao = AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    private fun loadSavedLanguage(): AppLanguage {
        val saved = prefs.getString("app_language", AppLanguage.EN.name)
        return try {
            AppLanguage.valueOf(saved ?: AppLanguage.EN.name)
        } catch (e: Exception) {
            AppLanguage.EN
        }
    }

    val language = MutableStateFlow(loadSavedLanguage())
    val preferredCurrency = MutableStateFlow(prefs.getString("preferred_currency", "USD") ?: "USD")
    val isWelcomeCompleted = MutableStateFlow(prefs.getBoolean("welcome_completed", false))

    val selectedCategoryFilter = MutableStateFlow<String?>(null)
    val searchQuery = MutableStateFlow("")

    val isAddEditSheetOpen = MutableStateFlow(false)
    val editingExpense = MutableStateFlow<Expense?>(null)
    val expenseToDelete = MutableStateFlow<Expense?>(null)

    // Monthly Budget Limit State
    val monthlyBudgetLimit = MutableStateFlow(prefs.getFloat("monthly_budget_limit", 2500f).toDouble())
    val isBudgetDialogOpen = MutableStateFlow(false)

    // Fullscreen Receipt Viewer State
    val viewingReceiptUri = MutableStateFlow<String?>(null)

    val allExpenses: StateFlow<List<Expense>> = repository.allExpenses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Current Month Expenses Total
    val currentMonthTotalAmount: StateFlow<Double> = allExpenses.map { list ->
        val calNow = Calendar.getInstance()
        val currentMonth = calNow.get(Calendar.MONTH)
        val currentYear = calNow.get(Calendar.YEAR)

        list.filter { expense ->
            val calExpense = Calendar.getInstance().apply { timeInMillis = expense.dateMillis }
            calExpense.get(Calendar.MONTH) == currentMonth && calExpense.get(Calendar.YEAR) == currentYear
        }.sumOf { it.amount }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    fun setMonthlyBudget(limit: Double) {
        monthlyBudgetLimit.value = limit
        prefs.edit().putFloat("monthly_budget_limit", limit.toFloat()).apply()
        isBudgetDialogOpen.value = false
    }

    fun openBudgetDialog() {
        isBudgetDialogOpen.value = true
    }

    fun closeBudgetDialog() {
        isBudgetDialogOpen.value = false
    }

    fun openReceiptViewer(uri: String) {
        viewingReceiptUri.value = uri
    }

    fun closeReceiptViewer() {
        viewingReceiptUri.value = null
    }

    val filteredExpenses: StateFlow<List<Expense>> = combine(
        allExpenses,
        selectedCategoryFilter,
        searchQuery
    ) { expenses, category, query ->
        expenses.filter { expense ->
            val matchesCategory = category == null || expense.categoryKey.equals(category, ignoreCase = true)
            val matchesQuery = query.isBlank() ||
                    expense.description.contains(query, ignoreCase = true) ||
                    expense.amount.toString().contains(query)
            matchesCategory && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val totalExpensesAmount: StateFlow<Double> = allExpenses.map { list ->
        list.sumOf { it.amount }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val categoryTotals: StateFlow<Map<String, Double>> = allExpenses.map { list ->
        list.groupBy { it.categoryKey }
            .mapValues { entry -> entry.value.sumOf { it.amount } }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )

    val topCategory: StateFlow<String?> = categoryTotals.map { totals ->
        totals.maxByOrNull { it.value }?.key
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val averageExpenseAmount: StateFlow<Double> = allExpenses.map { list ->
        if (list.isEmpty()) 0.0 else list.map { it.amount }.average()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    fun toggleLanguage() {
        val newLang = if (language.value == AppLanguage.EN) AppLanguage.RO else AppLanguage.EN
        setLanguage(newLang)
    }

    fun setLanguage(lang: AppLanguage) {
        language.value = lang
        prefs.edit().putString("app_language", lang.name).apply()
    }

    fun setPreferredCurrency(currency: String) {
        preferredCurrency.value = currency
        prefs.edit().putString("preferred_currency", currency).apply()
    }

    fun completeWelcome(lang: AppLanguage, currency: String, budgetLimit: Double) {
        setLanguage(lang)
        setPreferredCurrency(currency)
        setMonthlyBudget(budgetLimit)
        isWelcomeCompleted.value = true
        prefs.edit().putBoolean("welcome_completed", true).apply()
    }

    fun setCategoryFilter(categoryKey: String?) {
        selectedCategoryFilter.value = categoryKey
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun openAddExpense() {
        editingExpense.value = null
        isAddEditSheetOpen.value = true
    }

    fun openEditExpense(expense: Expense) {
        editingExpense.value = expense
        isAddEditSheetOpen.value = true
    }

    fun closeAddEditSheet() {
        isAddEditSheetOpen.value = false
        editingExpense.value = null
    }

    fun requestDeleteExpense(expense: Expense) {
        expenseToDelete.value = expense
    }

    fun dismissDeleteDialog() {
        expenseToDelete.value = null
    }

    fun confirmDeleteExpense() {
        expenseToDelete.value?.let { expense ->
            viewModelScope.launch {
                repository.delete(expense)
                expenseToDelete.value = null
            }
        }
    }

    fun saveExpense(
        id: Int = 0,
        amount: Double,
        categoryKey: String,
        dateMillis: Long,
        description: String,
        currency: String = preferredCurrency.value,
        receiptUri: String? = null
    ) {
        viewModelScope.launch {
            val expense = Expense(
                id = id,
                amount = amount,
                categoryKey = categoryKey,
                dateMillis = dateMillis,
                description = description,
                currency = currency,
                receiptUri = receiptUri
            )
            if (id == 0) {
                repository.insert(expense)
            } else {
                repository.update(expense)
            }
            closeAddEditSheet()
        }
    }
}
