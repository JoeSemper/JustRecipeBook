package com.joesemper.justrecipebook.ui.interfaces

interface IListPresenter <V : IItemView>  {

    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}