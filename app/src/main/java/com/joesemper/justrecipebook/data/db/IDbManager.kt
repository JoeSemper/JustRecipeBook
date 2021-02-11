package com.joesemper.justrecipebook.data.db

import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IDbManager {
    fun getSavedMeals() : Single<List<Meal>>
    fun putMeal(meal: Meal): Completable
}