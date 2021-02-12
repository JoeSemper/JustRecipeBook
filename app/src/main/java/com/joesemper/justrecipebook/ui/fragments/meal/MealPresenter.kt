package com.joesemper.justrecipebook.ui.fragments.meal

import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IIngredientsListPresenter
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientItemView
import com.joesemper.justrecipebook.util.logger.ILogger
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

    @Inject
    lateinit var logger: ILogger

    val inngredientsListPresenter = IngredientsListPresenter()

    private lateinit var fullMeal: Meal

    class IngredientsListPresenter : IIngredientsListPresenter {
        val ingredients = mutableListOf<Ingredient>()

        override var itemClickListener: ((IngredientItemView) -> Unit)? = null

        override fun bindView(view: IngredientItemView) {
            val ingredient = ingredients[view.pos]

            ingredient.ingredient.let { view.setIngredient(it) }
            ingredient.measure?.let { view.setMeasure(it) }
        }

        override fun getCount(): Int = ingredients.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadFullMeal()
    }

    fun onAddToFavoriteClicked(): Boolean {
        var result = false
        dataManager.putMealToFavorite(fullMeal)
            .observeOn(mainThreadScheduler)
            .subscribe({
                result = true
                viewState.showResult("Added to favorite")

            }, {
                logger.log(it)
            })
        return result
    }

    fun addToMenuClicked(): Boolean {
        viewState.showResult("Add to menu clicked")
        return true
    }

    private fun loadFullMeal() {
        dataManager.getMealById(meal.idMeal)
            .observeOn(mainThreadScheduler)
            .subscribe({ meal ->
                displayMeal(meal)
            }, {
                logger.log(it)
            })
    }

    private fun displayMeal(meal: Meal) {
        fullMeal = meal
        updateRV(meal)
        displayMealData(meal)
    }

    private fun updateRV(meal: Meal) {
        inngredientsListPresenter.ingredients.clear()
        meal.ingredients?.let { inngredientsListPresenter.ingredients.addAll(it) }
        viewState.updateList()
    }

    private fun displayMealData(meal: Meal) {
        with(viewState) {
            setTitle(meal.strMeal)
            setImage(meal.strMealThumb)
            setInstructions(meal.strInstructions)
            setRegion(meal.strArea)
            setIsFavorite(meal.isFavorite)
        }
    }



    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}