package com.joesemper.justrecipebook.ui.fragments.cart

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CartView: MvpView {
    fun init()
    fun updateList()
    fun updateItem(pos: Int)
    fun showContent()
    fun showResult(text: String)
    fun createDeleteDialog()
    fun vibrate()
    fun displayIngredientData(imgUrl: String, title: String)
}