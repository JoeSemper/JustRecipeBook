package com.joesemper.justrecipebook.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.SearchPresenter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class SearchFragment : MvpAppCompatFragment(), SearchFragmentView, BackButtonListener {


    companion object {
        fun newInstance() = SearchFragment()
    }

    val presenter: SearchPresenter by moxyPresenter {
        SearchPresenter().apply {
            App.instance.appComponent.inject(this)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return View.inflate(context, R.layout.fragment_search, null)
    }


    override fun init() {

    }

    override fun backPressed(): Boolean {
        return true
    }


}