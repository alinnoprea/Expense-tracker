package com.example.data.repository

import com.example.data.dao.ExpenseDao
import com.example.data.model.Expense
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()

    fun getExpenseById(id: Int): Flow<Expense?> = expenseDao.getExpenseById(id)

    suspend fun insert(expense: Expense): Long = expenseDao.insertExpense(expense)

    suspend fun update(expense: Expense) = expenseDao.updateExpense(expense)

    suspend fun delete(expense: Expense) = expenseDao.deleteExpense(expense)

    suspend fun deleteById(id: Int) = expenseDao.deleteExpenseById(id)

    suspend fun clearAll() = expenseDao.clearAllExpenses()
}
