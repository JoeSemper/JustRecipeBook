package com.joesemper.justrecipebook.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.HomePresenter
import com.joesemper.justrecipebook.ui.fragments.home.adapter.MealsRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import com.joesemper.justrecipebook.ui.util.constants.SearchType
import kotlinx.android.synthetic.main.fragment_main.*
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
        return View.inflate(context, R.layout.fragment_main, null)
    }

    override fun init() {
        initRV()
        initActionBar()
        initSearch()
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

    private fun initSearch() {
        text_input_layout_search.setEndIconOnClickListener {
            val query = text_input_search.text.toString()
            hideKeyboard(it)
            presenter.onSearch(query)
        }
    }

    private fun initActionBar() {
        with(requireActivity()) {
            setActionBar(toolbar_home )
//            setSupportActionBar(toolbar_home)
        }
    }

    override fun updateList() {
        mealsAdapter?.notifyDataSetChanged()
    }

    override fun showResult(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun hideSearch() {
        text_input_layout_search.visibility = GONE
    }

    private fun hideKeyboard(view: View) {
        if (view.isFocused) {
            view.clearFocus()
            val manager =
                ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            manager?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun backPressed(): Boolean = presenter.backPressed()
}