package com.joesemper.justrecipebook.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.joesemper.justrecipebook.data.network.ApiManager
import com.joesemper.justrecipebook.data.network.IApiManager
import com.joesemper.justrecipebook.data.network.api.IDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Named("baseUrl")
    @Provides
    fun baseUrl(): String = "https://www.themealdb.com/api/json/v1/"

    @Named("apiKey")
    @Provides
    fun apiKey(): String = "1/"


    @Singleton
    @Provides
    fun gson() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Singleton
    @Provides
    fun api(
        @Named("baseUrl") baseUrl: String,
        @Named("apiKey") apiKey: String,
        gson: Gson
    ) = Retrofit.Builder()
        .baseUrl(baseUrl + apiKey)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(IDataSource::class.java)

    @Provides
    fun getApiManager(api: IDataSource): IApiManager = ApiManager(api)
}