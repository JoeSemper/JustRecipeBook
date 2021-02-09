package com.joesemper.justrecipebook.data.cache

import com.joesemper.justrecipebook.data.cache.room.Database
import com.joesemper.justrecipebook.data.cache.room.RoomIngredient
import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomIngredientsCache(val db: Database): IIngredientsCache {

    override fun getIngredients(meal: Meal) = Single.fromCallable {
        val roomMeal = db.mealDao.findById(meal.idMeal)
        return@fromCallable db.ingredientDao.findForMeal(roomMeal.idMeal).map {
            Ingredient(it.ingredient, it.measure)
        }
    }.subscribeOn(Schedulers.io())

    override fun putMeals(meal: Meal, ingredients: List<Ingredient>) = Completable.fromAction {
        val roomMeal = db.mealDao.findById(meal.idMeal)
        val roomIngredients = ingredients.map {
            RoomIngredient(it.ingredient, it.measure, roomMeal.idMeal)
        }
        db.ingredientDao.insert(roomIngredients)
    }.subscribeOn(Schedulers.io())
}