package com.joesemper.justrecipebook.di.modules

import android.widget.ImageView
import com.joesemper.justrecipebook.ui.utilite.image.GlideImageLoader
import com.joesemper.justrecipebook.ui.utilite.image.IImageLoader
import dagger.Module
import dagger.Provides

@Module
class ImageLoaderModule {

    @Provides
    fun getImageLoader(): IImageLoader<ImageView> = GlideImageLoader()
}