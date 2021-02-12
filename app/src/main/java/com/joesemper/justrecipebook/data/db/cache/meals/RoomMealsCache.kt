package com.joesemper.justrecipebook.data.db.cache.meals

import com.joesemper.justrecipebook.data.db.room.Database
import com.joesemper.justrecipebook.data.db.room.RoomMeal
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomMealsCache(val db: Database) : IMealsCache {

    override fun getMeals() = Single.fromCallable {
        db.mealDao.getAll().map { roomMeal ->
            Meal(
                idMeal = roomMeal.idMeal,
                strMeal = roomMeal.strMeal,
                strArea = roomMeal.strArea,
                strTags = roomMeal.strTags,
                strMealThumb = roomMeal.strMealThumb,
                strInstructions = roomMeal.strInstructions,
            )
        }
    }.subscribeOn(Schedulers.io())

    override fun putMeals(meals: List<Meal>) = Completable.fromAction {
        val roomMeal = meals.map { meal ->
            RoomMeal(
                idMeal = meal.idMeal,
                strMeal = meal.strMeal,
                strArea = meal.strArea,
                strTags = meal.strTags,
                strMealThumb = meal.strMealThumb,
                strInstructions = meal.strInstructions
            )
        }
        db.mealDao.insert(roomMeal)
    }.subscribeOn(Schedulers.io())

    override fun getMealById(id: String) = Single.fromCallable {
        db.mealDao.findById(id)
    }.subscribeOn(Schedulers.io())

    override fun putMeal(meal: Meal) = Completable.fromAction {
        val roomMeal = RoomMeal(
            idMeal = meal.idMeal,
            strMeal = meal.strMeal,
            strArea = meal.strArea,
            strTags = meal.strTags ?: "",
            strMealThumb = meal.strMealThumb,
            strInstructions = meal.strInstructions
        )
        db.mealDao.insert(roomMeal)
    }.subscribeOn(Schedulers.io())

    override fun deleteMeal(meal: Meal) = Completable.fromAction {
        db.mealDao.deleteById(meal.idMeal)
    }.subscribeOn(Schedulers.io())


}