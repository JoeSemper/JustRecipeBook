package com.joesemper.justrecipebook.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.HomePresenter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class HomeFragment : MvpAppCompatFragment(), HomeView, BackButtonListener {


    companion object {
        fun newInstance() = HomeFragment()
    }

    val presenter: HomePresenter by moxyPresenter {
        HomePresenter().apply {
            App.instance.appComponent.inject(this)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return View.inflate(context, R.layout.fragment_home, null)
    }


    override fun init() {

    }

    override fun backPressed(): Boolean {
        return true
    }

}