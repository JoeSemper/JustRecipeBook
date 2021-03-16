package com.joesemper.justrecipebook.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Ingredient(
    @Expose val idIngredient: String = "",
    @Expose val strIngredient: String,
    @Expose var strDescription: String? ="",
    var measure: String? = "",
    var isBought: Boolean = false,
    var inCart: Boolean = false
) : Parcelable

