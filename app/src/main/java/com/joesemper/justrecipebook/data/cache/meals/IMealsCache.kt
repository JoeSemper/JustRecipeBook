package com.joesemper.justrecipebook.data.cache.meals

import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IMealsCache {
    fun getMeals(): Single<List<Meal>>
    fun putMeals(meals: List<Meal>) : Completable
    fun putMeal(meal: Meal) : Completable
}