package com.joesemper.justrecipebook.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Area(
    @Expose val strArea: String
): Parcelable
