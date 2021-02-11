package com.joesemper.justrecipebook.ui.fragments.meal.adapter

import com.joesemper.justrecipebook.ui.interfaces.IItemView

interface IngredientItemView : IItemView {
    fun setIngredient(name: String)
    fun setMeasure(measure: String)
}