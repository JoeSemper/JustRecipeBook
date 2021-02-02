package com.joesemper.justrecipebook.di

import com.joesemper.justrecipebook.di.modules.*
import com.joesemper.justrecipebook.ui.activities.MainActivity
import com.joesemper.justrecipebook.ui.activities.MainPresenter
import com.joesemper.justrecipebook.ui.adapters.meals.MealsRVAdapter
import com.joesemper.justrecipebook.ui.fragments.home.HomePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        AppDataManagerModule::class,
        CiceroneModule::class,
        ImageLoaderModule::class,

    ]
)

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(mealsRVAdapter: MealsRVAdapter)

}