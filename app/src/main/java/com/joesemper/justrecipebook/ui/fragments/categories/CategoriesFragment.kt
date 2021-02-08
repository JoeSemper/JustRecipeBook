package com.joesemper.justrecipebook.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.ui.adapters.categories.CategoriesRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import kotlinx.android.synthetic.main.fragment_categories.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class CategoriesFragment : MvpAppCompatFragment(), CategoriesView, BackButtonListener {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    val presenter: CategoriesPresenter by moxyPresenter {
        CategoriesPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var adapter: CategoriesRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_categories, null)

    override fun init() {
        initRV()
    }

    private fun initRV() {
        rv_categories.layoutManager = LinearLayoutManager(context)
        rv_categories.setHasFixedSize(true)
        adapter = CategoriesRVAdapter(presenter.categoriesListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        rv_categories.adapter = adapter
    }

    override fun updateLis() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}