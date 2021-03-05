package com.joesemper.justrecipebook.ui.fragments.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.list.ICartListPresenter
import com.joesemper.justrecipebook.ui.util.constants.Constants.Companion.EXTENSION
import com.joesemper.justrecipebook.ui.util.constants.Constants.Companion.INGREDIENT_IMG_BASE_URL
import com.joesemper.justrecipebook.ui.util.image.IImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.tv_cart_ingredient
import kotlinx.android.synthetic.main.item_ingredient.view.*
import javax.inject.Inject

class CartRVAdapter(val presenter: ICartListPresenter) :
    RecyclerView.Adapter<CartRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer,
        CartItemView {

        override var pos = -1

        override fun setIngredient(ingredient: String) = with(containerView) {
            tv_cart_ingredient.text = ingredient
        }

        override fun setImage(imgName: String) = with(containerView) {
            imageLoader.loadInto(INGREDIENT_IMG_BASE_URL + imgName + EXTENSION, iv_ingredient)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRVAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: CartRVAdapter.ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }


}