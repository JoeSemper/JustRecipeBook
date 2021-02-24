package com.joesemper.justrecipebook.data.network.api

import com.joesemper.justrecipebook.data.model.Areas
import com.joesemper.justrecipebook.data.model.Categories
import com.joesemper.justrecipebook.data.model.Meals
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IDataSource {

    @GET("lookup.php?")
    fun getMealById(@Query("i") id: String) : Single<Meals>

    @GET("search.php?")
    fun searchMealsByName(@Query("s") name: String?) : Single<Meals>

    @GET("random.php")
    fun getSingleRandomMeal() : Single<Meals>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c") category: String) : Single<Meals>

    @GET("filter.php?")
    fun getMealsByArea(@Query("a") area: String): Single<Meals>

    @GET("categories.php")
    fun getAllCategories(): Single<Categories>

    @GET("list.php?c=list")
    fun getAllAreas(): Single<Areas>

}