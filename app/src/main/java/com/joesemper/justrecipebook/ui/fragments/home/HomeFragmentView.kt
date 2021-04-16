package com.joesemper.justrecipebook.ui.fragments.home

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeFragmentView: MvpView {
    fun init()
    fun updateList()
    fun showResult(text: String)
}