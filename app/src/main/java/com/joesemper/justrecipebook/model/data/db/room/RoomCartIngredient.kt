package com.joesemper.justrecipebook.model.data.db.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomCartIngredient(
    @PrimaryKey var ingredient: String,
    var isBought: Boolean = false
)