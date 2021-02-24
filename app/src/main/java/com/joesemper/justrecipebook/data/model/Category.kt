package com.joesemper.justrecipebook.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Category(
    @Expose val idCategory: String,
    @Expose val strCategory: String,
    @Expose val strCategoryThumb: String,
    @Expose val strCategoryDescription: String
) : Parcelable
