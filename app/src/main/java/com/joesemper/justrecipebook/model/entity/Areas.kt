package com.joesemper.justrecipebook.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Areas(
    @Expose val meals: List<Area>
): Parcelable