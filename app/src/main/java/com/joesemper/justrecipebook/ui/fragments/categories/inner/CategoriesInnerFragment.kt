package com.joesemper.justrecipebook.ui.fragments.categories.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.CategoriesPresenter
import com.joesemper.justrecipebook.ui.fragments.categories.adapter.CategoriesRVAdapter
import kotlinx.android.synthetic.main.fragment_categories_inner.*

class CategoriesInnerFragment(val presenter: CategoriesPresenter): Fragment(), CategoriesInnerView {

    companion object {
        fun newInstance(presenter: CategoriesPresenter) = CategoriesInnerFragment(presenter)
    }

    private var adapter: CategoriesRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_categories_inner, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCategoriesCreated()
    }

    override fun init() {
        rv_categories.layoutManager = LinearLayoutManager(context)
        rv_categories.setHasFixedSize(true)
        adapter = CategoriesRVAdapter(presenter.categoriesListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        rv_categories.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }
}