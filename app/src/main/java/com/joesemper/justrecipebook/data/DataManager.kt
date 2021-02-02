package com.joesemper.justrecipebook.data

import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.data.network.model.Meals
import io.reactivex.rxjava3.core.Single

interface DataManager {

    fun getMealById(id: String) : Single<Meal>
    fun getMealByCategory(category: String) : Single<List<Meal>>
}