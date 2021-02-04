package com.joesemper.justrecipebook.data

import com.joesemper.justrecipebook.data.network.ApiManager
import com.joesemper.justrecipebook.data.network.model.Category
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Single

class AppDataManager(val apiManager: ApiManager): DataManager {

    override fun getMealById(id: String): Single<Meal> =
        apiManager.getMealById(id)

    override fun getMealByCategory(category: String): Single<List<Meal>> =
        apiManager.getMealsByCategory(category)

    override fun getAllCategories(): Single<List<Category>> =
        apiManager.getAllCategories()

    override fun getSingleRandomMeal(): Single<Meal> =
        apiManager.getSingleRandomMeal()

    override fun searchMealByName(query: String): Single<List<Meal>> =
        apiManager.searchMealsByName(query)
}