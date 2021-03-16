package com.joesemper.justrecipebook.model.data.network

import com.joesemper.justrecipebook.model.entity.*
import io.reactivex.rxjava3.core.Single

interface IApiManager {

    fun getMealById(id: String): Single<Meal>
    fun getMealsByCategory(category: String): Single<List<Meal>>
    fun getMealsByArea(area: String): Single<List<Meal>>
    fun getAllCategories(): Single<List<Category>>
    fun getAllAreas(): Single<List<Area>>
    fun getAllIngredients(): Single<List<Ingredient>>
    fun getSingleRandomMeal(): Single<Meal>
    fun searchMealsByName(query: String?): Single<List<Meal>>
}