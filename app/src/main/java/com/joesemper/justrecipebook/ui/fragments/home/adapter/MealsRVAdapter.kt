package com.joesemper.justrecipebook.ui.fragments.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.ui.utilite.image.IImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_meal.view.*
import javax.inject.Inject

class MealsRVAdapter(val presenter: IMealsListPresenter) :
    RecyclerView.Adapter<MealsRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, MealItemView {

        override var pos = -1

        override fun setMealName(text: String) = with(containerView) {
            tv_meal_header.text = text
        }

        private val callbackListener = GlideLoadListener(containerView)

        override fun loadImage(url: String) = with(containerView) {
            imageLoader.loadIntoWithCallback(url, iv_meal_image, callbackListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false))

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }

    class GlideLoadListener(val containerView: View): RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            with(containerView) {
                iv_meal_image.visibility = View.VISIBLE
                tv_meal_header.visibility = View.VISIBLE
                progressBar2.visibility = View.GONE
            }
            return false
        }
    }
}