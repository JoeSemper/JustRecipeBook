package com.joesemper.justrecipebook.ui.adapters.ingredients

import com.joesemper.justrecipebook.ui.adapters.interfaces.IItemView

interface IngredientItemView : IItemView {
    fun setIngredient(name: String)
    fun setMeasure(measure: String)
}