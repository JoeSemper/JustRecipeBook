package com.joesemper.justrecipebook.ui.fragments.meal

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MealView: MvpView {
    fun init()
    fun showResult(text:String)
    fun updateList()
    fun setImage(url: String)
    fun setInstructions(instruction: String)
    fun setIsFavorite(isFavorite: Boolean)
    fun showContent()
    fun playVideo(id: String)
    fun setOnPlayVideoClickListener(isExists: Boolean)
    fun initActionBar(title: String, subtitle:String)
}