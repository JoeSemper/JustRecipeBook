package com.joesemper.justrecipebook.ui.adapters.ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.justrecipebook.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsRVAdapter(val presenter: IIngredientsListPresenter) :
    RecyclerView.Adapter<IngredientsRVAdapter.ViewHolder>() {

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, IngredientItemView {

        override var pos = -1

        override fun setIngredient(name: String) = with(containerView) {
            tv_ingredient.text = name
        }

        override fun setMeasure(measure: String) = with(containerView) {
            tv_measure.text = measure
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        )

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }
}