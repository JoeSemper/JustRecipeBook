package com.joesemper.justrecipebook.ui.navigation

import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.fragments.categories.CategoriesFragment
import com.joesemper.justrecipebook.ui.fragments.home.HomeFragment
import com.joesemper.justrecipebook.ui.fragments.meal.MealFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class HomeScreen(val query: String) : SupportAppScreen() {
        override fun getFragment() = HomeFragment.newInstance(query)
    }

    class MealScreen(val meal: Meal) : SupportAppScreen() {
        override fun getFragment() = MealFragment.newInstance(meal)
    }

    class CategoriesScreen() : SupportAppScreen() {
        override fun getFragment() = CategoriesFragment.newInstance()
    }


}