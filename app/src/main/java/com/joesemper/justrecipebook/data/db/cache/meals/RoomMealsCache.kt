package com.joesemper.justrecipebook.data.db.cache.meals

import com.joesemper.justrecipebook.data.db.room.Database
import com.joesemper.justrecipebook.data.db.room.RoomMeal
import com.joesemper.justrecipebook.data.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RoomMealsCache(val db: Database) : IMealsCache {

    override fun getMeals() = Single.fromCallable {
        db.mealDao.getAll().map { roomMeal ->
            getMealByRoomMeal(roomMeal)
        }
    }

    override fun putMeals(meals: List<Meal>) = Completable.fromAction {
        val roomMeal = meals.map { meal ->
            getRoomMealByMeal(meal)
        }
        db.mealDao.insert(roomMeal)
    }

    override fun getMealById(id: String) = Single.fromCallable {
        val roomMeal = db.mealDao.findById(id)
        getMealByRoomMeal(roomMeal)
    }

    override fun putMeal(meal: Meal) = Completable.fromAction {
        val roomMeal = getRoomMealByMeal(meal)
        db.mealDao.insert(roomMeal)
    }

    override fun deleteMeal(meal: Meal) = Completable.fromAction {
        db.mealDao.deleteById(meal.idMeal)
    }

    private fun getMealByRoomMeal(roomMeal: RoomMeal) = Meal(
        idMeal = roomMeal.idMeal,
        strMeal = roomMeal.strMeal,
        strArea = roomMeal.strArea,
        strTags = roomMeal.strTags,
        strCategory = roomMeal.strCategory,
        strMealThumb = roomMeal.strMealThumb,
        strInstructions = roomMeal.strInstructions,
        strYoutube = roomMeal.strYoutube,
        strYoutubeId = roomMeal.strYoutubeId,
        isFavorite = true,

        )

    private fun getRoomMealByMeal(meal: Meal) = RoomMeal(
        idMeal = meal.idMeal,
        strMeal = meal.strMeal,
        strArea = meal.strArea,
        strCategory = meal.strCategory,
        strTags = meal.strTags ?: "",
        strMealThumb = meal.strMealThumb,
        strInstructions = meal.strInstructions,
        strYoutube = meal.strYoutube,
        strYoutubeId = meal.strYoutubeId,

        )

}