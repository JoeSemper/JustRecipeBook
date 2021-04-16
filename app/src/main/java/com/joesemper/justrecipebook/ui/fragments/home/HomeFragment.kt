package com.joesemper.justrecipebook.ui.fragments.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.HomePresenter
import com.joesemper.justrecipebook.ui.fragments.home.adapter.MealsRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import com.joesemper.justrecipebook.ui.util.constants.SearchType
import kotlinx.android.synthetic.main.fragment_search.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class HomeFragment : MvpAppCompatFragment(), HomeFragmentView, BackButtonListener {

    companion object {
        private const val MEAL_ARG = "MEAL"
        private const val SEARCH_ARG = "SEARCH"

        fun newInstance(searchType: SearchType, query: String = "") =
            HomeFragment().apply {
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

    private var mealsAdapter: MealsRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return View.inflate(context, R.layout.fragment_search, null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView

        initSearch(searchView)
    }

    override fun init() {
        initRV()
        initActionBar()
    }

    private fun initRV() {
        mealsAdapter = MealsRVAdapter(presenter.mealsListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        with(rv_meals) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = mealsAdapter
        }

    }

    private fun initSearch(searchView: SearchView) {
        val listener = QueryListener(presenter, searchView)
        searchView.setOnQueryTextListener(listener)
//        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setIconifiedByDefault(false)
        search_view.setIconifiedByDefault(false)
        search_view.queryHint = "meal, ingredient ..."
        search_view.setOnQueryTextListener(listener)
    }

    private fun initActionBar() {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar_home)
        }
    }

    override fun updateList() {
        mealsAdapter?.notifyDataSetChanged()
    }

    override fun showResult(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    private class QueryListener(val presenter: HomePresenter, val searchView: SearchView) :
        SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            presenter.onSearch(query)
            searchView.clearFocus()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            presenter.onSearch(newText)
            return true
        }
    }
}