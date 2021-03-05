package com.joesemper.justrecipebook.ui.util.provider

import android.content.SearchRecentSuggestionsProvider


class SuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.joesemper.justrecipebook.ui.utilite.provider.SuggestionProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }

}
