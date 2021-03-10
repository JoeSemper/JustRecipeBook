package com.joesemper.justrecipebook.data

import com.joesemper.justrecipebook.data.db.IDbManager
import com.joesemper.justrecipebook.data.model.Area
import com.joesemper.justrecipebook.data.network.IApiManager
import com.joesemper.justrecipebook.data.model.Category
import com.joesemper.justrecipebook.data.model.Ingredient
import com.joesemper.justrecipebook.data.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class AppDataManager(val apiManager: IApiManager, val cache: IDbManager) : DataManager {

    override fun getMealById(id: String): Single<Meal> =
        isMealFavorite(id).flatMap { isFavorite ->
            return@flatMap if (isFavorite) {
                cache.getMealById(id)
            } else {
                apiManager.getMealById(id)
            }
        }

    private fun isMealFavorite(mealId: String) = cache.isMealFavorite(mealId)

    override fun getMealByCategory(category: String) = apiManager.getMealsByCategory(category)

    override fun getMealsByArea(area: String) = apiManager.getMealsByArea(area)

    override fun getAllCategories() = apiManager.getAllCategories()

    override fun getAllAreas() = apiManager.getAllAreas()

    override fun getSingleRandomMeal() = apiManager.getSingleRandomMeal()

    override fun searchMealByName(query: String?) = apiManager.searchMealsByName(query)

    override fun putMealToFavorite(meal: Meal) = cache.putMeal(meal)

    override fun getFavoriteMeals(): Single<List<Meal>> = cache.getSavedMeals()

    override fun deleteMealFromFavorite(meal: Meal) = cache.deleteMealFromFavorite(meal)

    override fun getAllCartIngredients() = cache.getAllCartIngredients()

    override fun putIngredient(ingredient: Ingredient) = cache.putIngredient(ingredient)

    override fun updateIngredient(ingredient: Ingredient) = cache.updateIngredient(ingredient)

    override fun deleteIngredient(ingredient: Ingredient) = cache.deleteIngredient(ingredient)

    override fun deleteAllIngredients() = cache.deleteAllCartIngredients()

    override fun deleteBoughtIngredients() = cache.deleteBoughtIngredients()
}