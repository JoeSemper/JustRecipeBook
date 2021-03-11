package com.joesemper.justrecipebook.ui.fragments.meal.adapter


import android.view.View
import com.joesemper.justrecipebook.ui.interfaces.IItemView

interface IngredientItemView : IItemView {
    fun setIngredient(name: String)
    fun setMeasure(measure: String)
    fun loadImage(imageName: String)
    val addToCartClickListener: View.OnClickListener?
    fun setIngredientIsInCart(isInCart: Boolean)
    fun isInCart(): Boolean
}