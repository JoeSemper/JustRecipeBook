package com.joesemper.justrecipebook.model.data.db.cache.ingredients

import com.joesemper.justrecipebook.model.data.db.room.Database
import com.joesemper.justrecipebook.model.data.db.room.RoomIngredient
import com.joesemper.justrecipebook.model.entity.Ingredient
import com.joesemper.justrecipebook.model.entity.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RoomIngredientsCache(val db: Database) : IIngredientsCache {

    override fun getIngredients(meal: Meal) = Single.fromCallable {
        val roomMeal = db.mealDao.findById(meal.idMeal)
        return@fromCallable db.ingredientDao.findForMeal(roomMeal!!.idMeal).map {
            Ingredient(
                strIngredient = it.ingredient,
                measure = it.measure
            )
        }
    }

    override fun putIngredients(meal: Meal, ingredients: List<Ingredient>) =
        Completable.fromAction {
            val roomMeal = db.mealDao.findById(meal.idMeal)
            val roomIngredients = ingredients.map {
                RoomIngredient(it.strIngredient, it.measure, roomMeal!!.idMeal)
            }
            db.ingredientDao.insert(roomIngredients)
        }

    override fun deleteIngredients(meal: Meal) = Completable.fromAction {
        db.ingredientDao.deleteForMeal(meal.idMeal)
    }
}