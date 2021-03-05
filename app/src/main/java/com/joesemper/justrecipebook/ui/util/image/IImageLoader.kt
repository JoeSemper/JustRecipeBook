package com.joesemper.justrecipebook.ui.util.image

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.RequestListener

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
    fun loadIntoWithCallback(url: String, container: ImageView, listener: RequestListener<Drawable>)
}