package com.joesemper.justrecipebook.data.cache.room

import androidx.room.RoomDatabase
import com.joesemper.justrecipebook.data.cache.room.dao.IngredientDao
import com.joesemper.justrecipebook.data.cache.room.dao.MealDao


@androidx.room.Database(entities = [RoomMeal::class, RoomIngredient::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract val mealDao : MealDao
    abstract val ingredientDao: IngredientDao

    companion object {
        const val DB_NAME = "database.db"
    }
}