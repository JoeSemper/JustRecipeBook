package com.joesemper.justrecipebook.model.data.db.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomMeal(
    @PrimaryKey var idMeal: String,
    var strMeal: String,
    var strInstructions: String,
    var strMealThumb: String,
    var strTags: String?,
    var strArea: String,
    var strCategory: String,
    var strYoutube: String,
    var strYoutubeId: String,
)