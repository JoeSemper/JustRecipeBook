package com.joesemper.justrecipebook.presenter

import android.widget.Button
import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.model.Ingredient
import com.joesemper.justrecipebook.data.model.Meal
import com.joesemper.justrecipebook.ui.fragments.meal.MealView
import com.joesemper.justrecipebook.presenter.list.IIngredientsListPresenter
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientItemView
import com.joesemper.justrecipebook.ui.interfaces.IItemView
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

        override var addToCartClickListener: ((IngredientItemView) -> Unit)? = null

        override fun bindView(view: IngredientItemView) {
            val ingredient = ingredients[view.pos]

            ingredient.ingredient.let {
                view.setIngredient(it)
                view.loadImage(it)
            }

            ingredient.measure?.let {
                view.setMeasure(it)
            }

            ingredient.inCart.let {
                view.setIngredientIsInCart(it)
            }
        }


        override fun getCount(): Int = ingredients.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFullMeal()
        setOnClickListeners()
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

    private fun onMealLoaded(meal: Meal) {
        currentMeal = meal
        viewState.init()
        displayMealData(meal)
    }

    private fun displayMealData(meal: Meal) {
        with(viewState) {
            initActionBar(meal.strMeal, meal.strArea)
            setImage(meal.strMealThumb)
        }
    }

    fun onIngredientsReady() {
        viewState.initIngredients()
        displayIngredientList()
        viewState.showContent()
    }

    private fun displayIngredientList() {
        loadIngredientsCartList()
    }

    private fun loadIngredientsCartList() {
        dataManager.getAllCartIngredients()
            .observeOn(mainThreadScheduler)
            .subscribe({ cartIngredients ->
                currentMeal.ingredients?.map { mealIngredient ->
                    cartIngredients.forEach { cartIngredient ->
                        if (cartIngredient.ingredient == mealIngredient.ingredient) {
                            mealIngredient.inCart = true
                        }
                    }
                    updateIngredients()
                }
            }, {
                logger.log(it)
            })
    }

    fun onInstructionReady() {
        with(viewState) {
            initInstruction()
            setInstruction(currentMeal.strInstructions)
        }
    }

    fun onAddToFavoriteClicked(): Boolean {
        addOrRemoveMealFromFavorite(currentMeal.isFavorite)
        return true
    }

    fun onWatchVideoClicked(): Boolean {
        viewState.playVideo(currentMeal.strYoutubeId)
        return true
    }

    fun onOptionsMenuCreated() {
        viewState.setIsFavorite(currentMeal.isFavorite)
    }

    private fun setOnClickListeners() {
        ingredientsListPresenter.addToCartClickListener = { item ->
            onAddToCartClicked(item)
        }
    }

    private fun onAddToCartClicked(item: IngredientItemView) {
        if (!item.isInCart()) {
            removeIngredientFromCart(item)
        } else {
            addIngredientToCart(item)
        }
    }

    private fun addIngredientToCart(item: IngredientItemView) {
        val ingredient = ingredientsListPresenter.ingredients[item.pos]
        dataManager.putIngredient(ingredient)
            .observeOn(mainThreadScheduler)
            .subscribe({
                item.setIngredientIsInCart(true)
                viewState.showResult("${ingredient.ingredient} added to cart")
            }, {
                logger.log(it)
            })
    }

    private fun removeIngredientFromCart(item: IngredientItemView) {
        val ingredient = ingredientsListPresenter.ingredients[item.pos]
        dataManager.deleteIngredient(ingredient)
            .observeOn(mainThreadScheduler)
            .subscribe({
                item.setIngredientIsInCart(false)
                viewState.showResult("${ingredient.ingredient} removed from cart")
            }, {
                logger.log(it)
            })
    }

    private fun updateIngredients() {
        ingredientsListPresenter.ingredients.clear()
        currentMeal.ingredients?.let { ingredientsListPresenter.ingredients.addAll(it) }
        viewState.updateIngredientsList()
    }

    private fun addOrRemoveMealFromFavorite(isFavorite: Boolean) {
        currentMeal.isFavorite = !isFavorite
        viewState.setIsFavorite(!isFavorite)
        dataManager.putMealToFavorite(currentMeal)
            .observeOn(mainThreadScheduler)
            .subscribe({
                if (isFavorite) {
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