package com.joesemper.justrecipebook.model.data.db.cache.ingredients

import com.joesemper.justrecipebook.model.entity.Ingredient
import com.joesemper.justrecipebook.model.entity.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IIngredientsCache {
    fun getIngredients(meal: Meal): Single<List<Ingredient>>
    fun putIngredients(meal: Meal, ingredients: List<Ingredient>): Completable
    fun deleteIngredients(meal: Meal): Completable
}