package com.joesemper.justrecipebook.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Ingredients(
    @Expose val meals: List<Ingredient>
) : Parcelable