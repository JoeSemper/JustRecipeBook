package com.joesemper.justrecipebook.model.data.db.room

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
data class RoomIngredient (
    @PrimaryKey var ingredient: String,
    var measure: String?,
    var mealId: String
)