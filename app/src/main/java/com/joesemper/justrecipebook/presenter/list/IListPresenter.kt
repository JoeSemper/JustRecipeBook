package com.joesemper.justrecipebook.presenter.list

import com.joesemper.justrecipebook.ui.interfaces.IItemView

interface IListPresenter <V : IItemView>  {

    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}