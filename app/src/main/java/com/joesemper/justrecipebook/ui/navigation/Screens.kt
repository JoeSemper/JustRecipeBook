package com.joesemper.justrecipebook.ui.navigation

import com.joesemper.justrecipebook.ui.fragments.home.HomeFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class HomeScreen() : SupportAppScreen() {
        override fun getFragment() = HomeFragment.newInstance()
    }


}