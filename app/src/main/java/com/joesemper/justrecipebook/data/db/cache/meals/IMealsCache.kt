package com.joesemper.justrecipebook.data.db.cache.meals

import com.joesemper.justrecipebook.data.db.room.RoomMeal
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IMealsCache {
    fun getMeals(): Single<List<Meal>>
    fun putMeals(meals: List<Meal>) : Completable
    fun putMeal(meal: Meal)
    fun getMealById(id: String): Single<RoomMeal?>
}