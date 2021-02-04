package com.joesemper.justrecipebook.ui.navigation

import androidx.fragment.app.Fragment
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.fragments.home.HomeFragment
import com.joesemper.justrecipebook.ui.fragments.meal.MealFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class HomeScreen() : SupportAppScreen() {
        override fun getFragment() = HomeFragment.newInstance()
    }

    class MealScreen(val meal: Meal) : SupportAppScreen() {
        override fun getFragment() = MealFragment.newInstance(meal)
    }


}