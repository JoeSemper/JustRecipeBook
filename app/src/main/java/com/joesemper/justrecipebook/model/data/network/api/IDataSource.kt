package com.joesemper.justrecipebook.model.data.network.api

import com.joesemper.justrecipebook.model.entity.Areas
import com.joesemper.justrecipebook.model.entity.Categories
import com.joesemper.justrecipebook.model.entity.Ingredients
import com.joesemper.justrecipebook.model.entity.Meals
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSource {

    @GET("lookup.php?")
    fun getMealById(@Query("i") id: String): Single<Meals>

    @GET("search.php?")
    fun searchMealsByName(@Query("s") name: String?): Single<Meals>

    @GET("random.php")
    fun getSingleRandomMeal(): Single<Meals>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c") category: String): Single<Meals>

    @GET("filter.php?")
    fun getMealsByArea(@Query("a") area: String): Single<Meals>

    @GET("categories.php")
    fun getAllCategories(): Single<Categories>

    @GET("list.php?")
    fun getAllAreas(@Query("a") param: String = "list"): Single<Areas>

    @GET("list.php")
    fun getAllIngredients(@Query("i") param: String = "list"): Single<Ingredients>
}