package com.joesemper.justrecipebook.data.db.room.dao

import androidx.room.*
import com.joesemper.justrecipebook.data.db.room.RoomCartIngredient
import com.joesemper.justrecipebook.data.db.room.RoomIngredient

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


}