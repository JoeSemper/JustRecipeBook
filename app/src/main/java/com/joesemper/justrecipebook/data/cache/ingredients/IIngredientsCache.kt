package com.joesemper.justrecipebook.data.cache.ingredients

import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IIngredientsCache {
    fun getIngredients(meal: Meal): Single<List<Ingredient>>
    fun putIngredients(meal: Meal, ingredients: List<Ingredient>) : Completable
}