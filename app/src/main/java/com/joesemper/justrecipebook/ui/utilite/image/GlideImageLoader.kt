package com.joesemper.justrecipebook.ui.utilite.image

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.joesemper.justrecipebook.R

class GlideImageLoader : IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }

    override fun loadIntoWithCallback(url: String, container: ImageView, listener: RequestListener<Drawable>) {
        Glide.with(container.context)
            .load(url)
            .listener(listener)
            .into(container)
    }
}