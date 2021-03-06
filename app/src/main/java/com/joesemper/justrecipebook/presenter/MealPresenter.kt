package com.joesemper.justrecipebook.presenter

import com.joesemper.justrecipebook.model.DataManager
import com.joesemper.justrecipebook.model.entity.Ingredient
import com.joesemper.justrecipebook.model.entity.Meal
import com.joesemper.justrecipebook.presenter.list.IIngredientsListPresenter
import com.joesemper.justrecipebook.ui.fragments.meal.MealView
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientItemView
import com.joesemper.justrecipebook.ui.util.constants.Constants.Companion.EXTENSION
import com.joesemper.justrecipebook.ui.util.constants.Constants.Companion.INGREDIENT_IMG_BASE_URL
import com.joesemper.justrecipebook.ui.util.constants.Constants.Companion.MEAL_BASE_URL
import com.joesemper.justrecipebook.util.logger.ILogger
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

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
            with(ingredients[view.pos]) {
                strIngredient.let {
                    view.setIngredient(it)
                    view.loadImage(it)
                }
                measure?.let {
                    view.setMeasure(it)
                }
                inCart.let {
                    view.isInCart = it
                }
            }
        }

        override fun getCount(): Int = ingredients.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        displayMealData()
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

    private fun onMealLoaded(meal: Meal) {
        currentMeal = meal
        showIngredients()
        showInstruction()
        setOnClickListeners()
    }

    private fun displayMealData() {
        with(viewState) {
            initActionBar(currentMeal.strMeal)
            setImage(currentMeal.strMealThumb)
        }
    }

    private fun showIngredients() {
        viewState.initIngredients()
        displayIngredientList()
    }

    private fun displayIngredientList() {
        loadCartIngredientsList()
    }

    private fun loadCartIngredientsList() {
        dataManager.getAllCartIngredients()
            .observeOn(mainThreadScheduler)
            .subscribe({ cartIngredients ->
                onCartIngredientsLoaded(cartIngredients)
            }, {
                logger.log(it)
            })
    }

    private fun onCartIngredientsLoaded(cartIngredients: List<Ingredient>) {
        mapIngredientsToCurrentMeal(cartIngredients)
        updateIngredientsList()
        viewState.showContent()
    }

    private fun mapIngredientsToCurrentMeal(cartIngredients: List<Ingredient>) {
        currentMeal.ingredients?.map { mealIngredient ->
            cartIngredients.forEach { cartIngredient ->
                if (cartIngredient.strIngredient == mealIngredient.strIngredient) {
                    mealIngredient.inCart = true
                }
            }
        }
    }

    private fun showInstruction() {
        with(viewState) {
            initInstruction()
            setInstruction(currentMeal.strInstructions)
            setInstructionHeader(currentMeal.strMeal)
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

    fun onShareClicked(): Boolean {
        viewState.shareRecipe("$MEAL_BASE_URL${currentMeal.idMeal}", currentMeal.strMeal)
        return true
    }

    private fun setOnClickListeners() {
        ingredientsListPresenter.itemClickListener = { item ->
            displayIngredientData(item)
        }

        ingredientsListPresenter.addToCartClickListener = { item ->
            addOrRemoveIngredientFromCart(item)
        }
    }

    private fun displayIngredientData(item: IngredientItemView) {
        val currentIngredient = ingredientsListPresenter.ingredients[item.pos]
        val url = INGREDIENT_IMG_BASE_URL + currentIngredient.strIngredient + EXTENSION
        val title = currentIngredient.strIngredient
        viewState.displayIngredientData(url, title)
    }

    private fun addOrRemoveIngredientFromCart(item: IngredientItemView) {
        if (!item.isInCart) {
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
                item.isInCart = true
                viewState.showResult("${ingredient.strIngredient} added to cart")
            }, {
                logger.log(it)
            })
    }

    private fun removeIngredientFromCart(item: IngredientItemView) {
        val ingredient = ingredientsListPresenter.ingredients[item.pos]
        dataManager.deleteIngredient(ingredient)
            .observeOn(mainThreadScheduler)
            .subscribe({
                item.isInCart = false
                viewState.showResult("${ingredient.strIngredient} removed from cart")
            }, {
                logger.log(it)
            })
    }

    private fun updateIngredientsList() {
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
                    viewState.showResult("${currentMeal.strMeal} removed from favorite")
                } else {
                    viewState.showResult("${currentMeal.strMeal} added to favorite")
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