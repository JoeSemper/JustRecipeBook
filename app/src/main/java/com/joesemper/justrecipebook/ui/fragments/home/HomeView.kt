package com.joesemper.justrecipebook.ui.fragments.home

import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.data.network.model.Meals
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeView: MvpView {


    fun init()
    fun updateList()
    fun showResult(text: String)
}