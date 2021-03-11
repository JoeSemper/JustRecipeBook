package com.joesemper.justrecipebook.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Ingredient(
    var ingredient: String,
    var measure: String? = "",
    var isBought: Boolean = false,
    var inCart: Boolean = false
) : Parcelable

