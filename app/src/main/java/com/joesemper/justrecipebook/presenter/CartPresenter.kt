package com.joesemper.justrecipebook.presenter

import com.joesemper.justrecipebook.model.DataManager
import com.joesemper.justrecipebook.model.entity.Ingredient
import com.joesemper.justrecipebook.presenter.list.ICartListPresenter
import com.joesemper.justrecipebook.ui.fragments.cart.CartView
import com.joesemper.justrecipebook.ui.fragments.cart.adapter.CartItemView
import com.joesemper.justrecipebook.ui.util.constants.Constants
import com.joesemper.justrecipebook.util.logger.ILogger
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CartPresenter : MvpPresenter<CartView>() {

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var logger: ILogger

    var cartListPresenter = CartListPresenter()

    class CartListPresenter : ICartListPresenter {
        val cartIngredients = mutableListOf<Ingredient>()

        override var itemClickListener: ((CartItemView) -> Unit)? = null

        override var checkBoxClickListener: ((CartItemView) -> Unit)? = null

        override fun bindView(view: CartItemView) {
            val ingredient = cartIngredients[view.pos]

            ingredient.strIngredient.let {
                view.setIngredient(it)
                view.setImage(it)
            }

            if (ingredient.isBought) {
                view.setIngredientIsBought()
            } else {
                view.setIngredientIsNotBought()
            }
        }

        override fun getCount(): Int = cartIngredients.size

        fun clearList() = cartIngredients.clear()
        fun addIngredients(ingredients: List<Ingredient>) = cartIngredients.addAll(ingredients)
        fun getIngredient(pos: Int) = cartIngredients[pos]
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadCartIngredients()
        setOnClickListeners()
    }

    fun onItemSwiped(pos: Int){
        val ingredient = cartListPresenter.getIngredient(pos)
        viewState.vibrate()
        dataManager.deleteIngredient(ingredient).subscribe({
            loadCartIngredients()
        }, {
            logger.log(it)
        })
    }

    fun onClearCartClicked(): Boolean {
        viewState.createDeleteDialog()
        return true
    }

    fun clearAllSelected() {
        dataManager.deleteAllIngredients().subscribe( {
            loadCartIngredients()
        }, {
            logger.log(it)
        })
    }

    fun clearBoughtSelected() {
        dataManager.deleteBoughtIngredients().subscribe({
            loadCartIngredients()
        }, {
            logger.log(it)
        })
    }

    private fun loadCartIngredients() {
        dataManager.getAllCartIngredients()
            .observeOn(mainThreadScheduler)
            .subscribe({ ingredients ->
                updateCartList(ingredients)
            }, {
                logger.log(it)
            })
    }

    private fun updateCartList(ingredients: List<Ingredient>) {
        cartListPresenter.clearList()
        cartListPresenter.addIngredients(ingredients)
        viewState.updateList()
    }

    private fun setOnClickListeners() {
        cartListPresenter.itemClickListener = { cartItem ->
            displayIngredientData(cartItem)
        }
        cartListPresenter.checkBoxClickListener = { cartItem ->
            onCheckBoxClicked(cartItem)
        }
    }

    private fun displayIngredientData(item: CartItemView) {
        val currentIngredient = cartListPresenter.cartIngredients[item.pos]
        val url = Constants.INGREDIENT_IMG_BASE_URL + currentIngredient.strIngredient + Constants.EXTENSION
        val title = currentIngredient.strIngredient
        viewState.displayIngredientData(url, title)
    }

    private fun onCheckBoxClicked(cartItem: CartItemView) {
        val ingredient = cartListPresenter.cartIngredients[cartItem.pos]
        switchItemBoughtStatus(ingredient)
        updateIngredientInDb(ingredient)
        displayItemBoughtStatus(cartItem, ingredient.isBought)
    }

    private fun switchItemBoughtStatus(ingredient: Ingredient) {
        with(ingredient) {
            isBought = !isBought
        }
    }

    private fun displayItemBoughtStatus(item: CartItemView, isBought: Boolean) {
        if (isBought) {
            item.setIngredientIsBought()
        } else {
            item.setIngredientIsNotBought()
        }
    }

    private fun updateIngredientInDb(ingredient: Ingredient) {
        dataManager.updateIngredient(ingredient)
            .observeOn(mainThreadScheduler)
            .subscribe()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}