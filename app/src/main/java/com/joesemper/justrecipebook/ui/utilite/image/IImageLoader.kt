package com.joesemper.justrecipebook.ui.utilite.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}