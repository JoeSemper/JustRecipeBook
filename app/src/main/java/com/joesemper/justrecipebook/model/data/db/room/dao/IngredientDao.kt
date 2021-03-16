package com.joesemper.justrecipebook.model.data.db.room.dao

import androidx.room.*
import com.joesemper.justrecipebook.model.data.db.room.RoomIngredient


@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: RoomIngredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredients: List<RoomIngredient>)

    @Update
    fun update(ingredient: RoomIngredient)

    @Update
    fun update(ingredients: List<RoomIngredient>)

    @Delete
    fun delete(ingredient: RoomIngredient)

    @Delete
    fun delete(ingredients: List<RoomIngredient>)

    @Query("SELECT * FROM RoomIngredient")
    fun getAll() : List<RoomIngredient>

    @Query("SELECT * FROM RoomIngredient WHERE mealId = :id")
    fun findForMeal(id : String) : List<RoomIngredient>

    @Query("DELETE FROM RoomIngredient WHERE mealId = :id")
    fun deleteForMeal(id : String)
}