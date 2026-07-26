package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val categoryKey: String, // e.g. "food", "transport", "housing", "entertainment", "utilities", "health", "shopping", "other"
    val dateMillis: Long = System.currentTimeMillis(),
    val description: String,
    val currency: String = "RON",
    val receiptUri: String? = null
)
