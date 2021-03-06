package com.joesemper.justrecipebook.ui.fragments.categories

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CategoriesView: MvpView {
    fun init()
    fun initCategories()
    fun initAries()
    fun updateCategoriesList()
    fun updateAriesList()
    fun showContent()
}