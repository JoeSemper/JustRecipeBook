package com.joesemper.justrecipebook.data

import com.joesemper.justrecipebook.data.network.model.Category
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Single

interface DataManager {

    fun getMealById(id: String) : Single<Meal>
    fun getMealByCategory(category: String) : Single<List<Meal>>
    fun getAllCategories(): Single<List<Category>>
    fun getSingleRandomMeal(): Single<Meal>
    fun searchMealByName(query: String?): Single<List<Meal>>
}