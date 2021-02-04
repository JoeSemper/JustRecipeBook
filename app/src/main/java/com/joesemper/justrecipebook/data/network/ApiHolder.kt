package com.joesemper.justrecipebook.data.network

import com.joesemper.justrecipebook.data.network.api.IDataSource
import com.joesemper.justrecipebook.data.network.model.Category
import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ApiHolder(val api: IDataSource) : ApiManager {

    override fun getMealById(id: String): Single<Meal> {
        return api.getMealById(id).flatMap { meals ->
            Single.fromCallable {
                mapIngredientsToList(meals.meals[0])
//                meals.meals[0]
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getMealsByCategory(category: String): Single<List<Meal>> {
        return api.getMealsByCategory(category).flatMap { meals ->
            Single.fromCallable {
                meals.meals.toList()
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getAllCategories(): Single<List<Category>> {
        return api.getAllCategories().flatMap { categories ->
            Single.fromCallable {
                categories.categories.toList()
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getSingleRandomMeal(): Single<Meal> {
        return api.getSingleRandomMeal().flatMap { meals ->
            Single.fromCallable {
                mapIngredientsToList(meals.meals[0])
//                meals.meals[0]
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun searchMealsByName(query: String): Single<List<Meal>> {
        return api.searchMealsByName(query).flatMap { meals ->
            Single.fromCallable {
                meals.meals.toList()
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun mapIngredientsToList(meal: Meal): Meal {
        with(meal) {
            ingredients = mutableListOf()
            if (checkNull(strIngredient1)) {
                ingredients.add(Ingredient(strIngredient1, strMeasure1))
            }
            if (checkNull(strIngredient2)) {
                ingredients.add(Ingredient(strIngredient2, strMeasure2))
            }
            if (checkNull(strIngredient3)) {
                ingredients.add(Ingredient(strIngredient3, strMeasure3))
            }
            if (checkNull(strIngredient4)) {
                ingredients.add(Ingredient(strIngredient4, strMeasure4))
            }
            if (checkNull(strIngredient5)) {
                ingredients.add(Ingredient(strIngredient5, strMeasure5))
            }
            if (checkNull(strIngredient6)) {
                ingredients.add(Ingredient(strIngredient6, strMeasure6))
            }
            if (checkNull(strIngredient7)) {
                ingredients.add(Ingredient(strIngredient7, strMeasure7))
            }
            if (checkNull(strIngredient8)) {
                ingredients.add(Ingredient(strIngredient8, strMeasure8))
            }
            if (checkNull(strIngredient9)) {
                ingredients.add(Ingredient(strIngredient9, strMeasure9))
            }
            if (checkNull(strIngredient10)) {
                ingredients.add(Ingredient(strIngredient10, strMeasure10))
            }
            if (checkNull(strIngredient11)) {
                ingredients.add(Ingredient(strIngredient11, strMeasure11))
            }
            if (checkNull(strIngredient12)) {
                ingredients.add(Ingredient(strIngredient12, strMeasure12))
            }
            if (checkNull(strIngredient13)) {
                ingredients.add(Ingredient(strIngredient13, strMeasure13))
            }
            if (checkNull(strIngredient14)) {
                ingredients.add(Ingredient(strIngredient14, strMeasure14))
            }
            if (checkNull(strIngredient15)) {
                ingredients.add(Ingredient(strIngredient15, strMeasure15))
            }
            if (checkNull(strIngredient16)) {
                ingredients.add(Ingredient(strIngredient16, strMeasure16))
            }
            if (checkNull(strIngredient17)) {
                ingredients.add(Ingredient(strIngredient17, strMeasure17))
            }
            if (checkNull(strIngredient18)) {
                ingredients.add(Ingredient(strIngredient18, strMeasure18))
            }
            if (checkNull(strIngredient19)) {
                ingredients.add(Ingredient(strIngredient19, strMeasure19))
            }
            if (checkNull(strIngredient20)) {
                ingredients.add(Ingredient(strIngredient20, strMeasure20))
            }


        }

        return meal
    }

//    private fun addToList(item: String?, list: MutableList<Ingredient>) {
//        if (checkNull(item)) {
//            list.add(Ingredient(strIngredient20, strMeasure20))
//        }
//    }

    private fun checkNull(ingredient: String?): Boolean {
        return !(ingredient == "" || ingredient == null)
    }
}