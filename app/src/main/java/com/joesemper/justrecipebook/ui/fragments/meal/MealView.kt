package com.joesemper.justrecipebook.ui.fragments.meal

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MealView: MvpView {
    fun init()
    fun updateList()
    fun setTitle(title: String)
    fun setImage(url: String)
    fun setIngredients(ingredients : List<String>)
    fun setInstructions(instruction: String)
}