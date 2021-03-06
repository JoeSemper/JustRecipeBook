package com.joesemper.justrecipebook.ui.fragments.cart.adapter

import android.view.View
import com.joesemper.justrecipebook.ui.interfaces.IItemView

interface CartItemView: IItemView {
    fun setIngredient(ingredient: String)
    fun setImage(imgName: String)
    fun setIngredientIsBought()
    fun setIngredientIsNotBought()
}