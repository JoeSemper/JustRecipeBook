package com.joesemper.justrecipebook.data.cache

import com.joesemper.justrecipebook.data.cache.ingredients.IIngredientsCache
import com.joesemper.justrecipebook.data.cache.ingredients.RoomIngredientsCache
import com.joesemper.justrecipebook.data.cache.meals.IMealsCache
import com.joesemper.justrecipebook.data.cache.meals.RoomMealsCache
import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class Cache(val mealsCache: IMealsCache, val ingredientsCache: IIngredientsCache) : ICache {

    override fun getSavedMeals() = mealsCache.getMeals().flatMap { meals ->
        mapIngredientsToMeal(meals)
    }


    override fun putMeal(meal: Meal) = Completable.fromAction {
        mealsCache.putMeal(meal)
        ingredientsCache.putIngredients(meal, meal.ingredients!!.toList())
    }


    private fun mapIngredientsToMeal(meals: List<Meal>): Single<List<Meal>> {
        return Single.fromCallable {
            meals.map { meal ->
                meal.ingredients = getMealIngredients(meal)
                meal
            }
        }

    }

    private fun getMealIngredients(meal: Meal): MutableList<Ingredient> {
        var ingredientList: List<Ingredient>? = null
        ingredientsCache.getIngredients(meal).subscribe({ ingredients ->
            ingredientList = ingredients
        }, {

        })
        return ingredientList!!.toMutableList()
    }

}