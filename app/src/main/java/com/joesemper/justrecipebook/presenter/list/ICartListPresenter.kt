package com.joesemper.justrecipebook.presenter.list

import com.joesemper.justrecipebook.ui.fragments.cart.adapter.CartItemView
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientItemView

interface ICartListPresenter: IListPresenter<CartItemView> {
    var checkBoxClickListener: ((V: CartItemView) -> Unit)?
}