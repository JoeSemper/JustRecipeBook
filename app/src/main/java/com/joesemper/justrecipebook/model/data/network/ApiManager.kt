package com.joesemper.justrecipebook.model.data.network

import com.joesemper.justrecipebook.model.entity.Area
import com.joesemper.justrecipebook.model.data.network.api.IDataSource
import com.joesemper.justrecipebook.model.entity.Category
import com.joesemper.justrecipebook.model.entity.Ingredient
import com.joesemper.justrecipebook.model.entity.Meal
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ApiManager(val api: IDataSource) : IApiManager {

    override fun getMealById(id: String): Single<Meal> {
        return api.getMealById(id).flatMap { meals ->
            Single.fromCallable {
                mapIngredientsToList(meals.meals.first())
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

    override fun getMealsByArea(area: String): Single<List<Meal>> {
        return api.getMealsByArea(area).flatMap { meals ->
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

    override fun getAllAreas(): Single<List<Area>> {
        return api.getAllAreas().flatMap { areas ->
            Single.fromCallable {
                areas.meals.toList()
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getSingleRandomMeal(): Single<Meal> {
        return api.getSingleRandomMeal().flatMap { meals ->
            Single.fromCallable {
                mapIngredientsToList(meals.meals[0])
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun searchMealsByName(query: String?): Single<List<Meal>> {
        return api.searchMealsByName(query).flatMap { meals ->
            Single.fromCallable {
                meals.meals.toList()
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getAllIngredients(): Single<List<Ingredient>> {
        return api.getAllIngredients().flatMap { ingredients ->
            Single.fromCallable {
                ingredients.meals.toList()
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun addIngredientsDescription(meal: Meal): Single<Meal> {
        mapIngredientsToList(meal)
        return getAllIngredients().flatMap { ingredients ->
            Single.fromCallable {
                ingredients.map { ingredient ->
                    meal.ingredients?.map { currentIngredient ->
                        if (currentIngredient.strIngredient == ingredient.strIngredient) {
                            currentIngredient.strDescription = ingredient.strDescription
                        }
                    }
                }
                meal
            }
        }
    }

    private fun mapIngredientsToList(meal: Meal): Meal {
        with(meal) {
            ingredients = mutableListOf()

            addIngredientToList(meal, strIngredient1, strMeasure1)
            addIngredientToList(meal, strIngredient2, strMeasure2)
            addIngredientToList(meal, strIngredient3, strMeasure3)
            addIngredientToList(meal, strIngredient4, strMeasure4)
            addIngredientToList(meal, strIngredient5, strMeasure5)
            addIngredientToList(meal, strIngredient6, strMeasure6)
            addIngredientToList(meal, strIngredient7, strMeasure7)
            addIngredientToList(meal, strIngredient8, strMeasure8)
            addIngredientToList(meal, strIngredient9, strMeasure9)
            addIngredientToList(meal, strIngredient10, strMeasure10)
            addIngredientToList(meal, strIngredient11, strMeasure11)
            addIngredientToList(meal, strIngredient12, strMeasure12)
            addIngredientToList(meal, strIngredient13, strMeasure13)
            addIngredientToList(meal, strIngredient14, strMeasure14)
            addIngredientToList(meal, strIngredient15, strMeasure15)
            addIngredientToList(meal, strIngredient16, strMeasure16)
            addIngredientToList(meal, strIngredient17, strMeasure17)
            addIngredientToList(meal, strIngredient18, strMeasure18)
            addIngredientToList(meal, strIngredient19, strMeasure19)
            addIngredientToList(meal, strIngredient20, strMeasure20)
            meal.strYoutubeId = parseUrl(meal.strYoutube)
        }
        return meal
    }

    private fun addIngredientToList(meal: Meal, ingredient: String?, measure: String?) {
        if (checkIngredientNotNull(ingredient)) {
            meal.ingredients?.add(
                Ingredient(
                    strIngredient = ingredient!!,
                    measure = measure
                )
            )
        }
    }


    private fun checkIngredientNotNull(ingredient: String?) = (ingredient != "")

    private fun parseUrl(url: String): String {
        return url.split("=")[1]
    }

}