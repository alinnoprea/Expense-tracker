package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.ExpenseDao
import com.example.data.model.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Expense::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "expense_tracker_db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialData(database.expenseDao())
                    }
                }
            }

            suspend fun populateInitialData(expenseDao: ExpenseDao) {
                if (expenseDao.getExpenseCount() == 0) {
                    val now = System.currentTimeMillis()
                    val dayMs = 86400000L

                    val sampleExpenses = listOf(
                        Expense(
                            amount = 145.50,
                            categoryKey = "food",
                            dateMillis = now,
                            description = "Organic Farmer Market Grocery",
                            currency = "RON"
                        ),
                        Expense(
                            amount = 65.00,
                            categoryKey = "transport",
                            dateMillis = now - (1 * dayMs),
                            description = "Metro Monthly Pass & Fuel",
                            currency = "RON"
                        ),
                        Expense(
                            amount = 320.00,
                            categoryKey = "utilities",
                            dateMillis = now - (2 * dayMs),
                            description = "Green Energy Electricity Bill",
                            currency = "RON"
                        ),
                        Expense(
                            amount = 89.90,
                            categoryKey = "entertainment",
                            dateMillis = now - (3 * dayMs),
                            description = "Botanical Garden Ticket & Coffee",
                            currency = "RON"
                        ),
                        Expense(
                            amount = 210.00,
                            categoryKey = "shopping",
                            dateMillis = now - (4 * dayMs),
                            description = "Eco Linen Household Items",
                            currency = "RON"
                        )
                    )
                    sampleExpenses.forEach { expenseDao.insertExpense(it) }
                }
            }
        }
    }
}
