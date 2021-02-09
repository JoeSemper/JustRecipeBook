package com.joesemper.justrecipebook.data.cache.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomMeal::class,
        parentColumns = ["idMeal"],
        childColumns = ["mealId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class RoomIngredient (
    @PrimaryKey var ingredient: String,
    var measure: String?,
    var mealId: String
)