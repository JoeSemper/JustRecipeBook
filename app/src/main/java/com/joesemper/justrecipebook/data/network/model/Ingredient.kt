package com.joesemper.justrecipebook.data.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Ingredient(
    var ingredient: String?,
    var measure: String?
) : Parcelable

