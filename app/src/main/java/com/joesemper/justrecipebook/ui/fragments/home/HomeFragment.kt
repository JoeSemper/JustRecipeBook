package com.joesemper.justrecipebook.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.ui.fragments.home.adapter.MealsRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import com.joesemper.justrecipebook.ui.utilite.constants.SearchType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class HomeFragment : MvpAppCompatFragment(), HomeView, BackButtonListener {


    companion object {
        private const val MEAL_ARG = "MEAL"
        private const val SEARCH_ARG = "SEARCH"

        fun newInstance(searchType: SearchType, query: String = "") = HomeFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SEARCH_ARG, searchType)
                putString(MEAL_ARG, query)
            }
        }
    }

    val presenter: HomePresenter by moxyPresenter {
        val query = arguments?.getString(MEAL_ARG)
        val searchType = arguments?.getParcelable<SearchType>(SEARCH_ARG)
        HomePresenter(searchType, query).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var adapter: MealsRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.fragment_home, null)
    }


    override fun init() {
        initRV()
        initSearch()
    }

    private fun initRV() {
        rv_meals.layoutManager = GridLayoutManager(context, 3)
        rv_meals.setHasFixedSize(true)
        adapter = MealsRVAdapter(presenter.mealsListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        rv_meals.adapter = adapter
    }

    private fun initSearch() {
        val listener: SearchView.OnQueryTextListener = QueryListener(presenter)
        search_view.setOnQueryTextListener(listener)
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun showResult(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    private class QueryListener(val presenter: HomePresenter) : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            presenter.onSearch(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            presenter.onSearch(newText)
            return true
        }
    }
}