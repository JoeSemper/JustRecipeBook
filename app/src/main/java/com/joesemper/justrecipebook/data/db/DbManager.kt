package com.joesemper.justrecipebook.data.db

import com.joesemper.justrecipebook.data.db.cache.cart.ICartCache
import com.joesemper.justrecipebook.data.db.cache.ingredients.IIngredientsCache
import com.joesemper.justrecipebook.data.db.cache.meals.IMealsCache
import com.joesemper.justrecipebook.data.model.Ingredient
import com.joesemper.justrecipebook.data.model.Meal
import com.joesemper.justrecipebook.util.logger.ILogger
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DbManager(
    val mealsCache: IMealsCache,
    val ingredientsCache: IIngredientsCache,
    val cartCache: ICartCache,
    val logger: ILogger
) : IDbManager {

    override fun getSavedMeals() = mealsCache.getMeals().flatMap { meals ->
        mapIngredientsToMeal(meals)
    }.subscribeOn(Schedulers.io())

    override fun putMeal(meal: Meal) = Completable.fromAction {
        putMealToDb(meal)
    }.subscribeOn(Schedulers.io())

    override fun deleteMealFromFavorite(meal: Meal) = Completable.fromAction {
        deleteMealFromDb(meal)
    }.subscribeOn(Schedulers.io())

    override fun isMealFavorite(mealId: String) =
        mealsCache.isMealFavorite(mealId).subscribeOn(Schedulers.io())

    override fun getMealById(mealId: String): Single<Meal> =
        mealsCache.getMealById(mealId).flatMap { meal ->
            mapIngredientsToMeal(listOf(meal)).map { it[0] }
        }.subscribeOn(Schedulers.io())

    private fun mapIngredientsToMeal(meals: List<Meal>) = Single.fromCallable {
        meals.map { meal ->
            getMealIngredients(meal).subscribe({ ingredients ->
                meal.ingredients = ingredients as MutableList<Ingredient>?
            }, {
                logger.log(it)
            })
            meal
        }
    }

    private fun getMealIngredients(meal: Meal) = ingredientsCache.getIngredients(meal)

    private fun putMealToDb(meal: Meal) {
        mealsCache.putMeal(meal).subscribe({}, {
            logger.log(it)
        })
        ingredientsCache.putIngredients(meal, meal.ingredients!!.toList()).subscribe({}, {
            logger.log(it)
        })
    }

    private fun deleteMealFromDb(meal: Meal) {
        mealsCache.deleteMeal(meal).subscribe({}, {
            logger.log(it)
        })
        ingredientsCache.deleteIngredients(meal).subscribe({}, {
            logger.log(it)
        })
    }

    override fun getAllCartIngredients() = cartCache
        .getAllIngredients()
        .subscribeOn(Schedulers.io())

    override fun putIngredient(ingredient: Ingredient) = cartCache
        .putIngredient(ingredient)
        .subscribeOn(Schedulers.io())

    override fun updateIngredient(ingredient: Ingredient) = cartCache
        .updateIngredient(ingredient)
        .subscribeOn(Schedulers.io())

    override fun deleteIngredient(ingredient: Ingredient) = cartCache
        .deleteIngredient(ingredient)
        .subscribeOn(Schedulers.io())

    override fun deleteAllCartIngredients() = cartCache
        .deleteAllIngredients()
        .subscribeOn(Schedulers.io())

    override fun deleteBoughtIngredients() = cartCache
        .deleteBoughtIngredients()
        .subscribeOn(Schedulers.io())

}