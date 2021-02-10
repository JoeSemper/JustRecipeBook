package com.joesemper.justrecipebook.di.modules

import com.joesemper.justrecipebook.data.AppDataManager
import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.cache.ICache
import com.joesemper.justrecipebook.data.cache.meals.IMealsCache
import com.joesemper.justrecipebook.data.network.ApiManager
import dagger.Module
import dagger.Provides

@Module
class AppDataManagerModule {

    @Provides
    fun getAppDataManagerModule(apiManager: ApiManager, cache: ICache): DataManager =
        AppDataManager(apiManager, cache)
}