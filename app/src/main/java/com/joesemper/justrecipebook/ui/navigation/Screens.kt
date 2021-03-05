package com.joesemper.justrecipebook.ui.navigation

import com.joesemper.justrecipebook.data.model.Meal
import com.joesemper.justrecipebook.ui.fragments.categories.CategoriesFragment
import com.joesemper.justrecipebook.ui.fragments.home.HomeFragment
import com.joesemper.justrecipebook.ui.fragments.meal.MealFragment
import com.joesemper.justrecipebook.ui.util.constants.SearchType
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class HomeScreen(val searchType: SearchType, val query: String = "") : SupportAppScreen() {
        override fun getFragment() = HomeFragment.newInstance(searchType, query)
    }

    class MealScreen(val meal: Meal) : SupportAppScreen() {
        override fun getFragment() = MealFragment.newInstance(meal)
    }

    class CategoriesScreen() : SupportAppScreen() {
        override fun getFragment() = CategoriesFragment.newInstance()
    }

}