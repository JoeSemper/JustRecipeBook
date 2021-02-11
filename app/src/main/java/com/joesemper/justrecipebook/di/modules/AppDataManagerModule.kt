package com.joesemper.justrecipebook.di.modules

import com.joesemper.justrecipebook.data.AppDataManager
import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.db.IDbManager
import com.joesemper.justrecipebook.data.network.IApiManager
import dagger.Module
import dagger.Provides

@Module
class AppDataManagerModule {

    @Provides
    fun getAppDataManagerModule(apiManager: IApiManager, cache: IDbManager): DataManager =
        AppDataManager(apiManager, cache)
}