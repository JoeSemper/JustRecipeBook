package com.joesemper.justrecipebook.ui.fragments.categories.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.CategoriesPresenter
import com.joesemper.justrecipebook.ui.fragments.categories.adapter.AreasRVAdapter
import kotlinx.android.synthetic.main.fragment_aries_inner.*

class AriesInnerFragment(val presenter: CategoriesPresenter):Fragment(), AriesInnerView {

    companion object {
        fun newInstance(presenter: CategoriesPresenter) = AriesInnerFragment(presenter)
    }

    private var adapter: AreasRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_aries_inner, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAreasCreated()
    }

    override fun init() {
        rv_areas.layoutManager = GridLayoutManager(context, 2)
        rv_areas.setHasFixedSize(true)
        adapter = AreasRVAdapter(presenter.areasListPresenter)
        rv_areas.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }
}