package com.joesemper.justrecipebook.di.modules

import androidx.room.Room
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.data.cache.IMealsCache
import com.joesemper.justrecipebook.data.cache.IIngredientsCache
import com.joesemper.justrecipebook.data.cache.RoomIngredientsCache
import com.joesemper.justrecipebook.data.cache.RoomMealsCache
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
    fun ingredientsCache(database: Database): IIngredientsCache  {
        return RoomIngredientsCache(database)
    }

}