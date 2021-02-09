package com.joesemper.justrecipebook.di.modules

import com.joesemper.justrecipebook.data.AppDataManager
import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.cache.IMealsCache
import com.joesemper.justrecipebook.data.network.ApiManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDataManagerModule {

    @Provides
    fun getAppDataManagerModule(apiManager: ApiManager, cache: IMealsCache): DataManager =
        AppDataManager(apiManager, cache)
}