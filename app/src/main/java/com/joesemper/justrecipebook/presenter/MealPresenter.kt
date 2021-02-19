package com.joesemper.justrecipebook.presenter

import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.network.model.Ingredient
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.fragments.meal.MealView
import com.joesemper.justrecipebook.presenter.list.IIngredientsListPresenter
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientItemView
import com.joesemper.justrecipebook.util.logger.ILogger
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import kotlin.system.measureNanoTime

class MealPresenter(var currentMeal: Meal) : MvpPresenter<MealView>() {

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var logger: ILogger

    val inngredientsListPresenter = IngredientsListPresenter()

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
        if (currentMeal.isFavorite) {
            removeMealFromFavorite()
        } else {
            addMealToFavorite()
        }
        return true
    }

    fun addToMenuClicked(): Boolean {
        viewState.showResult("Add to menu clicked")
        return true
    }

    fun onWatchVideoClicked() {
        viewState.runVideo(currentMeal.strYoutubeId)
    }

    private fun loadFullMeal() {
        dataManager.getMealById(currentMeal.idMeal)
            .observeOn(mainThreadScheduler)
            .subscribe({ meal ->
                currentMeal = meal
                displayMeal(meal)
            }, {
                logger.log(it)
            })
    }

    private fun displayMeal(meal: Meal) {
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
            setImage(meal.strMealThumb)
            setTitle(meal.strMeal)
            setInstructions(meal.strInstructions)
            setRegion(meal.strArea)
            setIsFavorite(meal.isFavorite)
            showContent()
        }
    }

    private fun addMealToFavorite() {
        dataManager.putMealToFavorite(currentMeal)
            .observeOn(mainThreadScheduler)
            .subscribe({
                viewState.showResult("Added to favorite")
                viewState.setIsFavorite(true)
                currentMeal.isFavorite = true
            }, {
                logger.log(it)
            })
    }

    private fun removeMealFromFavorite() {
        dataManager.deleteMealFromFavorite(currentMeal)
            .observeOn(mainThreadScheduler)
            .subscribe({
                viewState.showResult("Removed from favorite")
                viewState.setIsFavorite(false)
                currentMeal.isFavorite = false
            }, {
                logger.log(it)
            })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}