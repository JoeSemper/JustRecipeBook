package com.joesemper.justrecipebook.model

import com.joesemper.justrecipebook.model.entity.Area
import com.joesemper.justrecipebook.model.entity.Category
import com.joesemper.justrecipebook.model.entity.Ingredient
import com.joesemper.justrecipebook.model.entity.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DataManager {

    fun getMealById(id: String): Single<Meal>
    fun getMealByCategory(category: String): Single<List<Meal>>
    fun getMealsByArea(area: String): Single<List<Meal>>
    fun getAllCategories(): Single<List<Category>>
    fun getAllAreas(): Single<List<Area>>
    fun getAllIngredients(): Single<List<Ingredient>>
    fun getSingleRandomMeal(): Single<Meal>
    fun searchMealByName(query: String?): Single<List<Meal>>
    fun putMealToFavorite(meal: Meal): Completable
    fun getFavoriteMeals(): Single<List<Meal>>
    fun deleteMealFromFavorite(meal: Meal): Completable
    fun getAllCartIngredients(): Single<List<Ingredient>>
    fun putIngredient(ingredient: Ingredient): Completable
    fun updateIngredient(ingredient: Ingredient): Completable
    fun deleteAllIngredients(): Completable
    fun deleteBoughtIngredients(): Completable
    fun deleteIngredient(ingredient: Ingredient): Completable
}