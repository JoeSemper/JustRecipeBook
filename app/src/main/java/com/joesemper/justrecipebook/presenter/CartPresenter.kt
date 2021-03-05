package com.joesemper.justrecipebook.presenter

import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.model.Ingredient
import com.joesemper.justrecipebook.presenter.list.ICartListPresenter
import com.joesemper.justrecipebook.ui.fragments.cart.CartView
import com.joesemper.justrecipebook.ui.fragments.cart.adapter.CartItemView
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

    val cartListPresenter = CartListPresenter()

    class CartListPresenter : ICartListPresenter {
        val cartIngredients = mutableListOf<Ingredient>()

        override var itemClickListener: ((CartItemView) -> Unit)? = null


        override fun bindView(view: CartItemView) {
            val ingredient = cartIngredients[view.pos]

            ingredient.ingredient.let {
                view.setIngredient(it)
                view.setImage(it)
            }
        }

        override fun getCount(): Int = cartIngredients.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadCartIngredients()
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
        cartListPresenter.cartIngredients.clear()
        cartListPresenter.cartIngredients.addAll(ingredients)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}