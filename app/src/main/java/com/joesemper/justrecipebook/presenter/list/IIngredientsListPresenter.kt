package com.joesemper.justrecipebook.presenter.list

import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientItemView
import com.joesemper.justrecipebook.ui.interfaces.IItemView

interface IIngredientsListPresenter: IListPresenter<IngredientItemView> {
    var addToCartClickListener: ((V: IngredientItemView) -> Unit)?
}