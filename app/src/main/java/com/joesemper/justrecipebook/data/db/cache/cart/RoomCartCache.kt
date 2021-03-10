package com.joesemper.justrecipebook.data.db.cache.cart

import com.joesemper.justrecipebook.data.db.room.Database
import com.joesemper.justrecipebook.data.db.room.RoomCartIngredient
import com.joesemper.justrecipebook.data.model.Ingredient
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RoomCartCache(val db: Database): ICartCache {

    override fun getAllIngredients() = Single.fromCallable {
        db.cartIngredientDao.getAll().map {
            Ingredient(ingredient = it.ingredient, isBought = it.isBought)
        }
    }

    override fun putIngredient(ingredient: Ingredient) = Completable.fromAction {
        val roomIngredient = RoomCartIngredient(ingredient = ingredient.ingredient)
        db.cartIngredientDao.insert(roomIngredient)
    }

    override fun updateIngredient(ingredient: Ingredient) = Completable.fromAction {
        val roomIngredient = RoomCartIngredient(
            ingredient = ingredient.ingredient,
            isBought = ingredient.isBought)
        db.cartIngredientDao.update(roomIngredient)
    }

    override fun deleteIngredient(ingredient: Ingredient) = Completable.fromAction {
        val roomIngredient = RoomCartIngredient(ingredient = ingredient.ingredient)
        db.cartIngredientDao.delete(roomIngredient)
    }

    override fun deleteAllIngredients() = Completable.fromAction {
        db.cartIngredientDao.deleteAll()
    }

    override fun deleteBoughtIngredients() = Completable.fromAction {
        db.cartIngredientDao.deleteBought()
    }
}