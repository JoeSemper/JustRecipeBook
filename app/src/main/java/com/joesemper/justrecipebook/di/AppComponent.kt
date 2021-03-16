package com.joesemper.justrecipebook.di

import com.joesemper.justrecipebook.data.db.IDbManager
import com.joesemper.justrecipebook.di.modules.*
import com.joesemper.justrecipebook.presenter.*
import com.joesemper.justrecipebook.ui.activities.MainActivity
import com.joesemper.justrecipebook.ui.fragments.cart.adapter.CartRVAdapter
import com.joesemper.justrecipebook.ui.fragments.categories.adapter.CategoriesRVAdapter
import com.joesemper.justrecipebook.ui.fragments.dialog.ingredient.IngredientDialogFragment
import com.joesemper.justrecipebook.ui.fragments.home.adapter.MealsRVAdapter
import com.joesemper.justrecipebook.ui.fragments.meal.MealFragment
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientsRVAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        AppDataManagerModule::class,
        CiceroneModule::class,
        ImageLoaderModule::class,
        LoggerModule::class,
        DatabaseModule::class,

    ]
)

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(mealsRVAdapter: MealsRVAdapter)
    fun inject(mealPresenter: MealPresenter)
    fun inject(mealFragment: MealFragment)
    fun inject(categoriesPresenter: CategoriesPresenter)
    fun inject(categoriesRVAdapter: CategoriesRVAdapter)
    fun inject(ingredientsRVAdapter: IngredientsRVAdapter)
    fun inject(dbManager: IDbManager)
    fun inject(cartPresenter: CartPresenter)
    fun inject(cartRVAdapter: CartRVAdapter)
    fun inject(ingredientDialog: IngredientDialogFragment)
}