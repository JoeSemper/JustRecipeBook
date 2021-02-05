package com.joesemper.justrecipebook.data.network

import com.joesemper.justrecipebook.data.network.model.Category
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.data.network.model.Meals
import io.reactivex.rxjava3.core.Single

interface ApiManager {

    fun getMealById(id: String): Single<Meal>
    fun getMealsByCategory(category: String): Single<List<Meal>>
    fun getAllCategories(): Single<List<Category>>
    fun getSingleRandomMeal(): Single<Meal>
    fun searchMealsByName(query: String?): Single<List<Meal>>
}