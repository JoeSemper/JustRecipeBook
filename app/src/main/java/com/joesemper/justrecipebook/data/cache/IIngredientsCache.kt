package com.joesemper.justrecipebook.data.cache

import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IIngredientsCache {
    fun getIngredients(meal: Meal): Single<List<Ingredient>>
    fun putMeals(meal: Meal, ingredients: List<Ingredient>) : Completable
}