package com.joesemper.justrecipebook.data.db

import android.util.Log
import com.joesemper.justrecipebook.data.db.cache.ingredients.IIngredientsCache
import com.joesemper.justrecipebook.data.db.cache.meals.IMealsCache
import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DbManager(val mealsCache: IMealsCache, val ingredientsCache: IIngredientsCache) : IDbManager {

    override fun getSavedMeals() = mealsCache.getMeals().flatMap { meals ->
        mapIngredientsToMeal(meals)
    }.subscribeOn(Schedulers.io())


    override fun putMeal(meal: Meal) = Completable.fromAction {
        put(meal)
    }.subscribeOn(Schedulers.io())

    override fun deleteMealFromFavorite(meal: Meal)= Completable.fromAction {
        delete(meal)
    }.subscribeOn(Schedulers.io())


    override fun isMealFavorite(meal: Meal) =
        mealsCache.getMealById(meal.idMeal).flatMap { roomMeal ->
            Single.fromCallable {
                roomMeal != null
            }
        }.subscribeOn(Schedulers.io())


    private fun mapIngredientsToMeal(meals: List<Meal>) = Single.fromCallable {
        meals.map { meal ->
            getMealIngredients(meal).subscribe({ ingredients ->
                meal.ingredients = ingredients as MutableList<Ingredient>?
            }, {
                Log.v("mytag", it.message.toString())
            })
            meal
        }
    }.subscribeOn(Schedulers.io())

    private fun getMealIngredients(meal: Meal) = ingredientsCache.getIngredients(meal)

    private fun put(meal: Meal) {
        mealsCache.putMeal(meal).subscribe()
        ingredientsCache.putIngredients(meal, meal.ingredients!!.toList()).subscribe()
    }

    private fun delete(meal: Meal) {
        mealsCache.deleteMeal(meal).subscribe()
        ingredientsCache.deleteIngredients(meal).subscribe()
    }

}