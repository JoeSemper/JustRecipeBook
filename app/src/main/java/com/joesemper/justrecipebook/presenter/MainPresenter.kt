package com.joesemper.justrecipebook.presenter

import com.joesemper.justrecipebook.ui.activities.MainView
import com.joesemper.justrecipebook.ui.navigation.Screens
import com.joesemper.justrecipebook.ui.util.constants.SearchType
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.HomeScreen())
    }

    fun onHomeClicked(): Boolean {
        router.newRootScreen(Screens.HomeScreen())
        return true
    }

    fun onSearchClicked(): Boolean {
        router.newRootScreen(Screens.SearchScreen(SearchType.QUERY))
        return true
    }

    fun onCategoriesClicked(): Boolean {
        router.newRootScreen(Screens.CategoriesScreen())
        return true
    }

    fun onFavoriteClicked(): Boolean {
        router.newRootScreen(Screens.SearchScreen(SearchType.FAVORITE))
        return true
    }

    fun onCartClicked(): Boolean {
        router.newRootChain(Screens.CartScreen())
        return true
    }

    fun backClicked() {
        router.exit()
    }
}