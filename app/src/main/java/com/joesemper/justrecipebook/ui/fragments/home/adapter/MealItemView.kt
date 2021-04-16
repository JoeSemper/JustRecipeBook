package com.joesemper.justrecipebook.ui.fragments.home.adapter

import com.joesemper.justrecipebook.ui.interfaces.IItemView


interface MealItemView : IItemView {
    fun setMealName(text: String)
    fun loadImage(url: String)
}