package com.joesemper.justrecipebook.di.modules

import androidx.room.Room
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.data.db.DbManager
import com.joesemper.justrecipebook.data.db.IDbManager
import com.joesemper.justrecipebook.data.db.cache.cart.ICartCache
import com.joesemper.justrecipebook.data.db.cache.cart.RoomCartCache
import com.joesemper.justrecipebook.data.db.cache.meals.IMealsCache
import com.joesemper.justrecipebook.data.db.cache.ingredients.IIngredientsCache
import com.joesemper.justrecipebook.data.db.cache.ingredients.RoomIngredientsCache
import com.joesemper.justrecipebook.data.db.cache.meals.RoomMealsCache
import com.joesemper.justrecipebook.data.db.room.Database
import com.joesemper.justrecipebook.util.logger.ILogger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun database(app: App) =
        Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()

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
    fun cartCache(database: Database): ICartCache {
        return RoomCartCache(database)
    }

    @Singleton
    @Provides
    fun getCache(
        mealsCache: IMealsCache,
        ingredientsCache: IIngredientsCache,
        cartCache: ICartCache,
        logger: ILogger
    ): IDbManager {
        return DbManager(mealsCache, ingredientsCache, cartCache, logger)
    }

}