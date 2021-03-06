package com.joesemper.justrecipebook.model.data.db.room.dao

import androidx.room.*
import com.joesemper.justrecipebook.model.data.db.room.RoomCartIngredient

@Dao
interface CartIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: RoomCartIngredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredients: List<RoomCartIngredient>)

    @Update
    fun update(ingredient: RoomCartIngredient)

    @Update
    fun update(ingredients: List<RoomCartIngredient>)

    @Delete
    fun delete(ingredient: RoomCartIngredient)

    @Delete
    fun delete(ingredients: List<RoomCartIngredient>)

    @Query("SELECT * FROM RoomCartIngredient")
    fun getAll() : List<RoomCartIngredient>

    @Query("DELETE FROM RoomCartIngredient")
    fun deleteAll()

    @Query("DELETE FROM RoomCartIngredient WHERE isBought = 1")
    fun deleteBought()

}