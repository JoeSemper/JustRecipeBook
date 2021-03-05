package com.joesemper.justrecipebook.data.db

import com.joesemper.justrecipebook.data.model.Ingredient
import com.joesemper.justrecipebook.data.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IDbManager {
    fun getSavedMeals(): Single<List<Meal>>
    fun putMeal(meal: Meal): Completable
    fun isMealFavorite(meal: Meal): Single<Boolean>
    fun deleteMealFromFavorite(meal: Meal): Completable
    fun getAllCartIngredients(): Single<List<Ingredient>>
    fun putIngredient(ingredient: Ingredient): Completable
    fun updateIngredient(ingredient: Ingredient): Completable
    fun deleteIngredient(ingredient: Ingredient): Completable
}