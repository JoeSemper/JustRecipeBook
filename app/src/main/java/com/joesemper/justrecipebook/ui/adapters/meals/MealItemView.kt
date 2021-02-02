package com.joesemper.justrecipebook.ui.adapters.meals

import com.joesemper.justrecipebook.ui.adapters.interfaces.IItemView


interface MealItemView : IItemView {
    fun setMealName(text: String)
    fun loadImage(url: String)
}