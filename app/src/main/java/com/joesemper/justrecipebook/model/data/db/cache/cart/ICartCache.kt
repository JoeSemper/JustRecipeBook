package com.joesemper.justrecipebook.model.data.db.cache.cart

import com.joesemper.justrecipebook.model.entity.Ingredient
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ICartCache {
    fun getAllIngredients(): Single<List<Ingredient>>
    fun putIngredient(ingredient: Ingredient): Completable
    fun updateIngredient(ingredient: Ingredient): Completable
    fun deleteIngredient(ingredient: Ingredient): Completable
    fun deleteAllIngredients(): Completable
    fun deleteBoughtIngredients(): Completable
}