package com.joesemper.justrecipebook.data.network

import com.joesemper.justrecipebook.data.network.api.IDataSource
import com.joesemper.justrecipebook.data.network.model.Meal
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ApiHolder(val api: IDataSource) : ApiManager {

    override fun getMealById(id: String): Single<Meal> {
        return api.getMealById(id).flatMap { meals ->
            Single.fromCallable{
                meals.meals[0]
            }
        }
    }

    override fun getMealsByCategory(category: String): Single<List<Meal>> {
        return api.getMealsByCategory(category).flatMap {  meals ->
            Single.fromCallable{
                meals.meals.toList()
            }
        }.subscribeOn(Schedulers.io())
    }
}