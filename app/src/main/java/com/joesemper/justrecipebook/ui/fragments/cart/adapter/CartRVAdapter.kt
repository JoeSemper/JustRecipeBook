package com.joesemper.justrecipebook.ui.fragments.cart.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.list.ICartListPresenter
import com.joesemper.justrecipebook.ui.util.constants.Constants.Companion.EXTENSION
import com.joesemper.justrecipebook.ui.util.constants.Constants.Companion.INGREDIENT_IMG_BASE_URL
import com.joesemper.justrecipebook.ui.util.image.IImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart_ingredient.view.*
import kotlinx.android.synthetic.main.item_ingredient.view.tv_cart_ingredient
import javax.inject.Inject

class CartRVAdapter(val presenter: ICartListPresenter) :
    RecyclerView.Adapter<CartRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer,
        CartItemView {

        override var pos = -1

        fun setCheckBoxClickListener(listener: View.OnClickListener) = with(containerView) {
            checkBox_cart_ingredient.setOnClickListener(listener)
        }

        override fun setIngredient(ingredient: String) = with(containerView) {
            tv_cart_ingredient.text = ingredient
        }

        override fun setImage(imgName: String) = with(containerView) {
            imageLoader.loadInto(INGREDIENT_IMG_BASE_URL + imgName + EXTENSION, iv_cart_ingredient)
        }

        override fun setIngredientIsBought() = with(containerView) {
            checkBox_cart_ingredient.isChecked = true
            tv_cart_ingredient.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            card_cart_ingredient.foreground = ContextCompat.getDrawable(context, R.color.foreground_tint_cart)
        }

        override fun setIngredientIsNotBought() = with(containerView) {
            checkBox_cart_ingredient.isChecked = false
            tv_cart_ingredient.paintFlags = Paint.ANTI_ALIAS_FLAG
            card_cart_ingredient.foreground = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRVAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_ingredient, parent, false)
        )

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: CartRVAdapter.ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        holder.setCheckBoxClickListener { presenter.checkBoxClickListener?.invoke(holder)}
        presenter.bindView(holder)
    }


}