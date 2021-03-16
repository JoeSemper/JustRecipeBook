package com.joesemper.justrecipebook.ui.fragments.home

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeView: MvpView {
    fun init()
}