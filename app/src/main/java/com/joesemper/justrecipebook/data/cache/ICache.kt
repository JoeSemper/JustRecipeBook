package com.joesemper.justrecipebook.data.cache

import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ICache {
    fun getSavedMeals() : Single<List<Meal>>
    fun putMeal(meal: Meal): Completable
}