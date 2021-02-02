package com.joesemper.justrecipebook.data.network

import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Single

interface ApiManager {

    fun getMealById(id: String): Single<Meal>

    fun getMealsByCategory(category: String): Single<List<Meal>>
}