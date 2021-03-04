package com.joesemper.justrecipebook.ui.fragments.meal

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MealView: MvpView {
    fun init()
    fun showResult(text:String)
    fun setImage(url: String)
    fun initIngredients()
    fun initInstruction()
    fun setInstruction(text: String)
    fun updateIngredientsList()
    fun setIsFavorite(isFavorite: Boolean)
    fun showContent()
    fun playVideo(id: String)
    fun initActionBar(title: String, subtitle:String)
}