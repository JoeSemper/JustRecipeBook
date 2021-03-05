package com.joesemper.justrecipebook.data.db.room

import androidx.room.RoomDatabase
import com.joesemper.justrecipebook.data.db.room.dao.CartIngredientDao
import com.joesemper.justrecipebook.data.db.room.dao.IngredientDao
import com.joesemper.justrecipebook.data.db.room.dao.MealDao


@androidx.room.Database(entities = [RoomMeal::class, RoomIngredient::class, RoomCartIngredient::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract val mealDao : MealDao
    abstract val ingredientDao: IngredientDao
    abstract val cartIngredientDao: CartIngredientDao

    companion object {
        const val DB_NAME = "database.db"
    }
}