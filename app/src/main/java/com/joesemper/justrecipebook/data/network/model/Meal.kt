package com.joesemper.justrecipebook.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Meal(
    @Expose val idMeal: String,
    @Expose val strMeal: String,
    @Expose val strInstructions: String,
    @Expose val strMealThumb: String,

): Parcelable