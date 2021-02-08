package com.joesemper.justrecipebook.ui.adapters.categories

import com.joesemper.justrecipebook.ui.adapters.interfaces.IItemView

interface CategoryItemView: IItemView {
    fun setCategory(text: String)
    fun loadImage(url: String)
}