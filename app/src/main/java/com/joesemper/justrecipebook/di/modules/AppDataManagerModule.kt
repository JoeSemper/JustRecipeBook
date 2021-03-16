package com.joesemper.justrecipebook.di.modules

import com.joesemper.justrecipebook.model.AppDataManager
import com.joesemper.justrecipebook.model.DataManager
import com.joesemper.justrecipebook.model.data.db.IDbManager
import com.joesemper.justrecipebook.model.data.network.IApiManager
import dagger.Module
import dagger.Provides

@Module
class AppDataManagerModule {

    @Provides
    fun getAppDataManagerModule(apiManager: IApiManager, cache: IDbManager): DataManager =
        AppDataManager(apiManager, cache)
}