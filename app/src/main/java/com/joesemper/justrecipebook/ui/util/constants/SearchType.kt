package com.joesemper.justrecipebook.ui.util.constants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class SearchType : Parcelable {
    QUERY(),
    CATEGORY(),
    AREA(),
    FAVORITE(),

}