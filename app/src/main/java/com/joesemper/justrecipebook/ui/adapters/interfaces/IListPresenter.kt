package com.joesemper.justrecipebook.ui.adapters.interfaces

interface IListPresenter <V : IItemView>  {

    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}