package com.joesemper.justrecipebook.presenter

import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.model.Ingredient
import com.joesemper.justrecipebook.data.model.Meal
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

    val ingredientsListPresenter = IngredientsListPresenter()

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
        displayMeal()
    }

    fun onAddToFavoriteClicked(): Boolean {
        addOrRemoveMealFromFavorite(currentMeal.isFavorite)
        return true
    }

    fun onAddToCartClicked():Boolean {return true}

    fun onWatchVideoClicked(): Boolean {
        viewState.playVideo(currentMeal.strYoutubeId)
        return true
    }


    fun onOptionsMenuCreated() {
        viewState.setIsFavorite(currentMeal.isFavorite)
    }

    private fun displayMeal() {
        loadFullMeal()
    }

    private fun loadFullMeal() {
        dataManager.getMealById(currentMeal.idMeal)
            .observeOn(mainThreadScheduler)
            .subscribe({ meal ->
                onMealLoaded(meal)
            }, {
                logger.log(it)
            })
    }

    private fun onMealLoaded(meal: Meal){
        currentMeal = meal
        displayMealData(meal)
    }

    private fun displayMealData(meal: Meal) {
        updateRVIngredients(meal)

        with(viewState) {
            initActionBar(meal.strMeal, meal.strArea)
            setImage(meal.strMealThumb)
            setOnPlayVideoClickListener(meal.strYoutube != "")
            setInstructions(meal.strInstructions)
            showContent()
        }
    }

    private fun updateRVIngredients(meal: Meal) {
        ingredientsListPresenter.ingredients.clear()
        meal.ingredients?.let { ingredientsListPresenter.ingredients.addAll(it) }
        viewState.updateList()
    }

    private fun addOrRemoveMealFromFavorite(isFavorite:Boolean) {
        currentMeal.isFavorite = !isFavorite
        viewState.setIsFavorite(!isFavorite)
        dataManager.putMealToFavorite(currentMeal)
            .observeOn(mainThreadScheduler)
            .subscribe({
                if(isFavorite) {
                    viewState.showResult("Removed from favorite")
                } else {
                    viewState.showResult("Added to favorite")
                }
            }, {
                logger.log(it)
            })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}