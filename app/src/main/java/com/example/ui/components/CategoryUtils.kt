package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryInfo(
    val key: String,
    val icon: ImageVector,
    val color: Color
)

object CategoryUtils {

    val categories = listOf(
        CategoryInfo("food", Icons.Default.Fastfood, Color(0xFF6A994E)),
        CategoryInfo("transport", Icons.Default.DirectionsCar, Color(0xFF386641)),
        CategoryInfo("housing", Icons.Default.Home, Color(0xFFBC4749)),
        CategoryInfo("utilities", Icons.Default.Lightbulb, Color(0xFFDDA15E)),
        CategoryInfo("entertainment", Icons.Default.Tv, Color(0xFFA7C957)),
        CategoryInfo("health", Icons.Default.MedicalServices, Color(0xFF3A5A40)),
        CategoryInfo("shopping", Icons.Default.ShoppingCart, Color(0xFFB5838D)),
        CategoryInfo("other", Icons.Default.MoreHoriz, Color(0xFF6C757D))
    )

    fun getCategoryInfo(key: String): CategoryInfo {
        return categories.find { it.key.equals(key, ignoreCase = true) }
            ?: CategoryInfo(key, Icons.Default.Category, Color(0xFF5F6E60))
    }
}
