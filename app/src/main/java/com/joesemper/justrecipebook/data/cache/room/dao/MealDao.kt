package com.joesemper.justrecipebook.data.cache.room.dao

import androidx.room.*
import com.joesemper.justrecipebook.data.cache.room.RoomMeal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meal: RoomMeal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meals: List<RoomMeal>)

    @Update
    fun update(meal: RoomMeal)

    @Update
    fun update(meals: List<RoomMeal>)

    @Delete
    fun delete(meal: RoomMeal)

    @Delete
    fun delete(meals: List<RoomMeal>)

    @Query("SELECT * FROM RoomMeal")
    fun getAll() : List<RoomMeal>

    @Query("SELECT * FROM RoomMeal WHERE idMeal = :id LIMIT 1")
    fun findById(id : String) : RoomMeal
}