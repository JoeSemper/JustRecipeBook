package com.joesemper.justrecipebook.data

import com.joesemper.justrecipebook.data.db.IDbManager
import com.joesemper.justrecipebook.data.network.IApiManager
import com.joesemper.justrecipebook.data.network.model.Category
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Single

class AppDataManager(val apiManager: IApiManager, val cache: IDbManager): DataManager {

    override fun getMealById(id: String): Single<Meal> =
        apiManager.getMealById(id)

    override fun getMealByCategory(category: String): Single<List<Meal>> =
        apiManager.getMealsByCategory(category)

    override fun getAllCategories(): Single<List<Category>> =
        apiManager.getAllCategories()

    override fun getSingleRandomMeal(): Single<Meal> =
        apiManager.getSingleRandomMeal()

    override fun searchMealByName(query: String?): Single<List<Meal>> =
        apiManager.searchMealsByName(query)

    override fun putMealToFavorite(meal: Meal) = cache.putMeal(meal)

    override fun getFavoriteMeals(): Single<List<Meal>> {
        val meals = cache.getSavedMeals()
        return meals
    }
}