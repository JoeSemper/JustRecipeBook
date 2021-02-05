package com.joesemper.justrecipebook.ui.fragments.meal

import android.util.Log
import com.joesemper.justrecipebook.BuildConfig.DEBUG
import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.adapters.ingredients.IIngredientsListPresenter
import com.joesemper.justrecipebook.ui.adapters.ingredients.IngredientItemView
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MealPresenter(val meal: Meal) : MvpPresenter<MealView>() {

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var router: Router

    val inngredientsListPresenter = IngredientsListPresenter()

    class IngredientsListPresenter : IIngredientsListPresenter {
        val ingredients = mutableListOf<Ingredient>()

        override var itemClickListener: ((IngredientItemView) -> Unit)? = null

        override fun bindView(view: IngredientItemView) {
            val ingredient = ingredients[view.pos]

            ingredient.ingredient?.let { view.setIngredient(it) }
            ingredient.measure?.let { view.setMeasure(it) }
        }

        override fun getCount(): Int = ingredients.size
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadFullMeal()
    }

    private fun loadFullMeal() {
        dataManager.getMealById(meal.idMeal)
            .observeOn(mainThreadScheduler).subscribe({ meal ->
                displayMeal(meal)
            }, {
                if (DEBUG) {
                    Log.v("Meal", it.message.toString())
                }
            })
    }

    private fun displayMeal(fullMeal: Meal) {
        initRV(fullMeal)
        initMealData(fullMeal)
    }

    private fun initRV(fullMeal: Meal) {
        inngredientsListPresenter.ingredients.clear()
        inngredientsListPresenter.ingredients.addAll(fullMeal.ingredients)
    }

    private fun initMealData(fullMeal: Meal){
        with(viewState) {
            setTitle(fullMeal.strMeal)
            setImage(fullMeal.strMealThumb)
            setInstructions(fullMeal.strInstructions)
            setRegion(fullMeal.strArea)
        }
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}