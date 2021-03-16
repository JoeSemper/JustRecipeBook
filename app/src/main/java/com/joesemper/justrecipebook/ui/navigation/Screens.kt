package com.joesemper.justrecipebook.ui.navigation

import androidx.fragment.app.Fragment
import com.joesemper.justrecipebook.data.model.Meal
import com.joesemper.justrecipebook.ui.fragments.cart.CartFragment
import com.joesemper.justrecipebook.ui.fragments.categories.CategoriesFragment
import com.joesemper.justrecipebook.ui.fragments.home.HomeFragment
import com.joesemper.justrecipebook.ui.fragments.meal.MealFragment
import com.joesemper.justrecipebook.ui.fragments.search.SearchFragment
import com.joesemper.justrecipebook.ui.util.constants.SearchType
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class HomeScreen(val searchType: SearchType, val query: String = "") : SupportAppScreen() {
        override fun getFragment() = HomeFragment.newInstance(searchType, query)
    }

    class SearchScreen() : SupportAppScreen(){
        override fun getFragment() = SearchFragment.newInstance()
    }

    class MealScreen(val meal: Meal) : SupportAppScreen() {
        override fun getFragment() = MealFragment.newInstance(meal)
    }

    class CategoriesScreen() : SupportAppScreen() {
        override fun getFragment() = CategoriesFragment.newInstance()
    }

    class CartScreen(): SupportAppScreen() {
        override fun getFragment() = CartFragment.newInstance()
    }

}