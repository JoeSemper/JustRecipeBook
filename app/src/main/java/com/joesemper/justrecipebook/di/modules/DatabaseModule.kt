package com.joesemper.justrecipebook.di.modules

import androidx.room.Room
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.data.cache.Cache
import com.joesemper.justrecipebook.data.cache.ICache
import com.joesemper.justrecipebook.data.cache.meals.IMealsCache
import com.joesemper.justrecipebook.data.cache.ingredients.IIngredientsCache
import com.joesemper.justrecipebook.data.cache.ingredients.RoomIngredientsCache
import com.joesemper.justrecipebook.data.cache.meals.RoomMealsCache
import com.joesemper.justrecipebook.data.cache.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun database(app: App) = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()

    @Singleton
    @Provides
    fun mealsCache(database: Database): IMealsCache {
        return RoomMealsCache(database)
    }

    @Singleton
    @Provides
    fun ingredientsCache(database: Database): IIngredientsCache {
        return RoomIngredientsCache(database)
    }

    @Singleton
    @Provides
    fun getCache(mealsCache: IMealsCache, ingredientsCache: IIngredientsCache): ICache {
        return Cache(mealsCache, ingredientsCache)
    }

}