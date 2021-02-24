package com.joesemper.justrecipebook.ui.fragments.categories.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.list.IAreaListPresenter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_area.view.*

class AreasRVAdapter(val presenter: IAreaListPresenter) :
    RecyclerView.Adapter<AreasRVAdapter.ViewHolder>() {


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, AreaItemView {

        override var pos = -1

        override fun setArea(text: String) = with(containerView) {
            tv_area.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_area, parent, false))

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }
}