package com.joesemper.justrecipebook.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
@Parcelize
class Meals(
    @Expose
    val meals: Array<Meal>
):Parcelable
