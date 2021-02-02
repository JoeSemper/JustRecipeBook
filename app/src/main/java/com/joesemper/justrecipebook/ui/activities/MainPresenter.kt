package com.joesemper.justrecipebook.ui.activities

import com.joesemper.justrecipebook.ui.navigation.Screens
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

    fun backClicked() {
        router.exit()
    }
}