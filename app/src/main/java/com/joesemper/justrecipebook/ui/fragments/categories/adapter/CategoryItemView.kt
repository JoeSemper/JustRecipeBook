package com.joesemper.justrecipebook.ui.fragments.categories.adapter

import com.joesemper.justrecipebook.ui.interfaces.IItemView

interface CategoryItemView: IItemView {
    fun setCategory(text: String)
    fun loadImage(url: String)
}