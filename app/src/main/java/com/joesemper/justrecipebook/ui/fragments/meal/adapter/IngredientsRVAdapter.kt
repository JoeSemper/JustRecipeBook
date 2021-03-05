package com.joesemper.justrecipebook.ui.fragments.meal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.list.IIngredientsListPresenter
import com.joesemper.justrecipebook.ui.util.image.IImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_ingredient.view.*
import javax.inject.Inject
import javax.inject.Named

class IngredientsRVAdapter(val presenter: IIngredientsListPresenter) :
    RecyclerView.Adapter<IngredientsRVAdapter.ViewHolder>() {

    companion object {
        const val INGREDIENT_IMG_BASE_URL = "https://www.themealdb.com/images/ingredients/"
        const val EXTENSION = ".png"
    }

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

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

        override fun loadImage(imageName: String) = with(containerView) {
            imageLoader.loadInto(INGREDIENT_IMG_BASE_URL + imageName + EXTENSION, iv_ingredient)
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