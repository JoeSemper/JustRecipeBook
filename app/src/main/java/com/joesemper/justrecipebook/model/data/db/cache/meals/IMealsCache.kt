package com.joesemper.justrecipebook.model.data.db.cache.meals

import com.joesemper.justrecipebook.model.entity.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IMealsCache {
    fun getMeals(): Single<List<Meal>>
    fun putMeals(meals: List<Meal>): Completable
    fun isMealFavorite(id: String): Single<Boolean>
    fun putMeal(meal: Meal): Completable
    fun deleteMeal(meal: Meal): Completable
    fun getMealById(id: String): Single<Meal>
}