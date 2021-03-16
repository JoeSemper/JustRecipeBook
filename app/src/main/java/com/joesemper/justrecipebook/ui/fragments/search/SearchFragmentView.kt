package com.joesemper.justrecipebook.ui.fragments.search

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchFragmentView: MvpView {
    fun init()
    fun updateList()
    fun showResult(text: String)
}