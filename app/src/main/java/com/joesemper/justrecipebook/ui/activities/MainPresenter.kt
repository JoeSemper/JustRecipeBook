package com.joesemper.justrecipebook.ui.activities

import com.joesemper.justrecipebook.ui.navigation.Screens
import com.joesemper.justrecipebook.ui.utilite.constants.SearchType
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.HomeScreen(SearchType.QUERY))
    }

    fun onHomeClicked(): Boolean {
        router.navigateTo(Screens.HomeScreen(SearchType.QUERY))
        return true
    }

    fun onCategoriesClicked(): Boolean {
        router.navigateTo(Screens.CategoriesScreen())
        return true
    }

    fun onFavoriteClicked(): Boolean {
        router.navigateTo(Screens.HomeScreen(SearchType.FAVORITE))
        return true
    }

    fun backClicked() {
        router.exit()
    }
}