package com.joesemper.justrecipebook.ui.activities

import android.os.Bundle
import android.view.View
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initInjection()
        initBottomNav()
    }

    private fun initInjection() {
        App.instance.appComponent.inject(this)
    }

    private fun initBottomNav() {
        bottom_nav.setOnNavigationItemSelectedListener { menuItem ->
            if (menuItem.isChecked) {
                return@setOnNavigationItemSelectedListener false
            }

            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.navigation_home -> presenter.onHomeClicked()
                R.id.navigation_categories -> presenter.onCategoriesClicked()
                R.id.navigation_favorites -> presenter.onFavoriteClicked()
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }

        presenter.backClicked()
    }

}