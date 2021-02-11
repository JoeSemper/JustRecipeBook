package com.joesemper.justrecipebook.data.db.cache.ingredients

import com.joesemper.justrecipebook.data.db.room.Database
import com.joesemper.justrecipebook.data.db.room.RoomIngredient
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

    override fun putIngredients(meal: Meal, ingredients: List<Ingredient>)  {
        val roomMeal = db.mealDao.findById(meal.idMeal)
        val roomIngredients = ingredients.map {
            RoomIngredient(it.ingredient, it.measure, roomMeal.idMeal)
        }
        db.ingredientDao.insert(roomIngredients)
    }
}