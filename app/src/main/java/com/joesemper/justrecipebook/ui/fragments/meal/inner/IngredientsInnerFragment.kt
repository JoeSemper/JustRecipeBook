package com.joesemper.justrecipebook.ui.fragments.meal.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.MealPresenter
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientsRVAdapter
import kotlinx.android.synthetic.main.fragment_ingredients_inner.*

class IngredientsInnerFragment(val presenter: MealPresenter): Fragment(), IngredientsInnerView {

    companion object {
        fun newInstance(presenter: MealPresenter) = IngredientsInnerFragment(presenter)
    }

    private var ingredientsAdapter: IngredientsRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = View.inflate(context, R.layout.fragment_ingredients_inner, null)

    override fun init() {
        ingredientsAdapter = IngredientsRVAdapter(presenter.ingredientsListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        with(rv_ingredients) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = ingredientsAdapter
        }
    }

    override fun updateList() {
        ingredientsAdapter?.notifyDataSetChanged()
    }
}