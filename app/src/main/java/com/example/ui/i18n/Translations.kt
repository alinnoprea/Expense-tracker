package com.example.ui.i18n

object Translations {

    private val dictionary: Map<String, Map<AppLanguage, String>> = mapOf(
        "app_title" to mapOf(
            AppLanguage.EN to "Expense Tracker",
            AppLanguage.RO to "Tracker Cheltuieli"
        ),
        "app_subtitle" to mapOf(
            AppLanguage.EN to "Organic & Mindful Finance",
            AppLanguage.RO to "Finanțe Organice & Echilibrate"
        ),
        "total_expenses" to mapOf(
            AppLanguage.EN to "Total Expenses",
            AppLanguage.RO to "Total Cheltuieli"
        ),
        "logged_entries" to mapOf(
            AppLanguage.EN to "Logged Entries",
            AppLanguage.RO to "Inregistrări"
        ),
        "top_category" to mapOf(
            AppLanguage.EN to "Top Category",
            AppLanguage.RO to "Top Categorie"
        ),
        "average_expense" to mapOf(
            AppLanguage.EN to "Average Expense",
            AppLanguage.RO to "Cheltuială Medie"
        ),
        "category_breakdown" to mapOf(
            AppLanguage.EN to "Category Analytics",
            AppLanguage.RO to "Analiză Categorii"
        ),
        "recent_expenses" to mapOf(
            AppLanguage.EN to "Recent Logged Expenses",
            AppLanguage.RO to "Cheltuieli Recente"
        ),
        "add_expense" to mapOf(
            AppLanguage.EN to "Add Expense",
            AppLanguage.RO to "Adaugă Cheltuială"
        ),
        "edit_expense" to mapOf(
            AppLanguage.EN to "Edit Expense",
            AppLanguage.RO to "Editează Cheltuiala"
        ),
        "save_expense" to mapOf(
            AppLanguage.EN to "Save Expense",
            AppLanguage.RO to "Salvează Cheltuiala"
        ),
        "update_expense" to mapOf(
            AppLanguage.EN to "Update Expense",
            AppLanguage.RO to "Actualizează Cheltuiala"
        ),
        "delete" to mapOf(
            AppLanguage.EN to "Delete",
            AppLanguage.RO to "Șterge"
        ),
        "edit" to mapOf(
            AppLanguage.EN to "Edit",
            AppLanguage.RO to "Editează"
        ),
        "cancel" to mapOf(
            AppLanguage.EN to "Cancel",
            AppLanguage.RO to "Anulează"
        ),
        "amount" to mapOf(
            AppLanguage.EN to "Amount",
            AppLanguage.RO to "Sumă"
        ),
        "category" to mapOf(
            AppLanguage.EN to "Category",
            AppLanguage.RO to "Categorie"
        ),
        "date" to mapOf(
            AppLanguage.EN to "Date",
            AppLanguage.RO to "Dată"
        ),
        "description" to mapOf(
            AppLanguage.EN to "Description",
            AppLanguage.RO to "Descriere"
        ),
        "currency" to mapOf(
            AppLanguage.EN to "Currency",
            AppLanguage.RO to "Monedă"
        ),
        "search_placeholder" to mapOf(
            AppLanguage.EN to "Search expenses...",
            AppLanguage.RO to "Caută cheltuieli..."
        ),
        "all_categories" to mapOf(
            AppLanguage.EN to "All",
            AppLanguage.RO to "Toate"
        ),
        "no_expenses_title" to mapOf(
            AppLanguage.EN to "No Expenses Logged",
            AppLanguage.RO to "Nicio Cheltuială Înregistrată"
        ),
        "no_expenses_desc" to mapOf(
            AppLanguage.EN to "Start tracking your spending by tapping the add button below.",
            AppLanguage.RO to "Începe să îți monitorizezi cheltuielile apăsând butonul de adăugare."
        ),
        "amount_error" to mapOf(
            AppLanguage.EN to "Please enter a valid amount greater than 0",
            AppLanguage.RO to "Introduceți o sumă validă mai mare decât 0"
        ),
        "description_error" to mapOf(
            AppLanguage.EN to "Please enter a brief description",
            AppLanguage.RO to "Introduceți o scurtă descriere"
        ),
        "confirm_delete_title" to mapOf(
            AppLanguage.EN to "Delete Expense?",
            AppLanguage.RO to "Ștergi Cheltuiala?"
        ),
        "confirm_delete_msg" to mapOf(
            AppLanguage.EN to "Are you sure you want to remove this expense entry?",
            AppLanguage.RO to "Ești sigur că vrei să ștergi această cheltuială?"
        ),
        "language_toggle" to mapOf(
            AppLanguage.EN to "Language",
            AppLanguage.RO to "Limbă"
        ),
        "today" to mapOf(
            AppLanguage.EN to "Today",
            AppLanguage.RO to "Azi"
        ),
        "yesterday" to mapOf(
            AppLanguage.EN to "Yesterday",
            AppLanguage.RO to "Ieri"
        ),
        "pick_date" to mapOf(
            AppLanguage.EN to "Pick Date",
            AppLanguage.RO to "Alege Data"
        ),
        // Categories
        "cat_food" to mapOf(
            AppLanguage.EN to "Food & Grocery",
            AppLanguage.RO to "Mâncare & Alimente"
        ),
        "cat_transport" to mapOf(
            AppLanguage.EN to "Transportation",
            AppLanguage.RO to "Transport"
        ),
        "cat_housing" to mapOf(
            AppLanguage.EN to "Housing & Rent",
            AppLanguage.RO to "Locuință & Chirie"
        ),
        "cat_entertainment" to mapOf(
            AppLanguage.EN to "Entertainment",
            AppLanguage.RO to "Divertisment"
        ),
        "cat_utilities" to mapOf(
            AppLanguage.EN to "Utilities & Bills",
            AppLanguage.RO to "Utilități & Facturi"
        ),
        "cat_health" to mapOf(
            AppLanguage.EN to "Health & Wellness",
            AppLanguage.RO to "Sănătate & Îngrijire"
        ),
        "cat_shopping" to mapOf(
            AppLanguage.EN to "Shopping",
            AppLanguage.RO to "Cumpărături"
        ),
        "cat_other" to mapOf(
            AppLanguage.EN to "Other Expenses",
            AppLanguage.RO to "Alte Cheltuieli"
        ),
        // Budget & Spending Limits
        "monthly_budget" to mapOf(
            AppLanguage.EN to "Monthly Budget",
            AppLanguage.RO to "Buget Lunar"
        ),
        "set_budget" to mapOf(
            AppLanguage.EN to "Set Limit",
            AppLanguage.RO to "Setează Limită"
        ),
        "edit_budget" to mapOf(
            AppLanguage.EN to "Edit Budget Limit",
            AppLanguage.RO to "Editează Bugetul Lunar"
        ),
        "budget_limit" to mapOf(
            AppLanguage.EN to "Spending Limit",
            AppLanguage.RO to "Limită Cheltuieli"
        ),
        "budget_status_on_track" to mapOf(
            AppLanguage.EN to "On Track",
            AppLanguage.RO to "În Grafic"
        ),
        "budget_status_warning" to mapOf(
            AppLanguage.EN to "Near Limit",
            AppLanguage.RO to "Aproape de Limită"
        ),
        "budget_status_exceeded" to mapOf(
            AppLanguage.EN to "Budget Exceeded!",
            AppLanguage.RO to "Buget Depășit!"
        ),
        "spent_of_budget" to mapOf(
            AppLanguage.EN to "spent of",
            AppLanguage.RO to "cheltuiți din"
        ),
        "budget_remaining" to mapOf(
            AppLanguage.EN to "Remaining",
            AppLanguage.RO to "Rămas"
        ),
        "save" to mapOf(
            AppLanguage.EN to "Save",
            AppLanguage.RO to "Salvează"
        ),
        // Receipts
        "receipt_attachment" to mapOf(
            AppLanguage.EN to "Receipt Photo",
            AppLanguage.RO to "Poza Chitanță"
        ),
        "attach_receipt" to mapOf(
            AppLanguage.EN to "Attach Receipt Photo",
            AppLanguage.RO to "Agață Poză Chitanță"
        ),
        "change_receipt" to mapOf(
            AppLanguage.EN to "Change Photo",
            AppLanguage.RO to "Schimbă Poza"
        ),
        "remove_receipt" to mapOf(
            AppLanguage.EN to "Remove Photo",
            AppLanguage.RO to "Șterge Poza"
        ),
        "view_receipt" to mapOf(
            AppLanguage.EN to "View Receipt",
            AppLanguage.RO to "Vezi Chitanța"
        ),
        "no_receipt" to mapOf(
            AppLanguage.EN to "No Receipt Attached",
            AppLanguage.RO to "Fără Chitanță"
        ),
        // Ad Banner
        "sponsored" to mapOf(
            AppLanguage.EN to "Sponsored",
            AppLanguage.RO to "Sponsorizat"
        ),
        "ad_title" to mapOf(
            AppLanguage.EN to "Smart Financial Insights",
            AppLanguage.RO to "Analize Financiare Inteligente"
        ),
        "ad_desc" to mapOf(
            AppLanguage.EN to "Track savings & discover personalized deals.",
            AppLanguage.RO to "Urmărește economiile și descoperă oferte speciale."
        )
    )

    fun get(key: String, language: AppLanguage): String {
        val langMap = dictionary[key]
        return langMap?.get(language) ?: langMap?.get(AppLanguage.EN) ?: key
    }

    fun getCategoryName(categoryKey: String, language: AppLanguage): String {
        val key = "cat_${categoryKey.lowercase(java.util.Locale.ROOT)}"
        return get(key, language)
    }
}
