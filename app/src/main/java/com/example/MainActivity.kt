package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.google.android.gms.ads.MobileAds
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.ExpenseTrackerTheme
import com.example.ui.viewmodel.ExpenseViewModel

class MainActivity : ComponentActivity() {

    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Google Mobile Ads SDK asynchronously
        MobileAds.initialize(this) {}

        setContent {
            ExpenseTrackerTheme {
                HomeScreen(viewModel = expenseViewModel)
            }
        }
    }
}

