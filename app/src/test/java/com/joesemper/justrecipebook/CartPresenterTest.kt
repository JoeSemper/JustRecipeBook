package com.joesemper.justrecipebook

import com.joesemper.justrecipebook.model.DataManager
import com.joesemper.justrecipebook.model.entity.Ingredient
import com.joesemper.justrecipebook.presenter.CartPresenter
import com.joesemper.justrecipebook.ui.fragments.cart.`CartView$$State`
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.terrakok.cicerone.Router

class CartPresenterTest {

    private lateinit var presenter: CartPresenter

    @Mock
    private lateinit var mockDataManager: DataManager

    @Mock
    private lateinit var mockRouter: Router

    private var mockMainThreadScheduler = Schedulers.trampoline()

    private val mockCartListPresenter = Mockito.mock(CartPresenter.CartListPresenter::class.java)

    private val viewContract = Mockito.mock(`CartView$$State`::class.java)


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = CartPresenter()
        with(presenter) {
            dataManager = mockDataManager
            router = mockRouter
            mainThreadScheduler = mockMainThreadScheduler
            cartListPresenter = mockCartListPresenter
            setViewState(viewContract)
        }
    }

    @Test
    fun onItemSwipe_updateCartList_MethodOrder(){
        val ingredient = Ingredient("","")
        val ingredients = listOf(ingredient)
        val deleteResponse = Completable.fromCallable {  }
        val getAllResponse = Single.fromCallable { ingredients }

        `when`(presenter.cartListPresenter.getIngredient(0)).thenReturn(ingredient)
        given(presenter.dataManager.deleteIngredient(ingredient)).willReturn(deleteResponse)
        given(presenter.dataManager.getAllCartIngredients()).willReturn(getAllResponse)

        presenter.onItemSwiped(0)

        val inOrder = inOrder(presenter.cartListPresenter, presenter.viewState)
        inOrder.verify(presenter.cartListPresenter).clearList()
        inOrder.verify(presenter.cartListPresenter).addIngredients(ingredients)
        inOrder.verify(presenter.viewState).updateList()
    }

    @Test
    fun clearCartClicked_createDeleteDialog(){
        presenter.onClearCartClicked()
        verify(presenter.viewState).createDeleteDialog()
    }

    @Test
    fun clearAllSelected_call_updateList(){
        val ingredient = Ingredient("","")
        val ingredients = listOf(ingredient)
        val deleteResponse = Completable.fromCallable {  }
        val getAllResponse = Single.fromCallable { ingredients }

        given(presenter.dataManager.deleteAllIngredients()).willReturn(deleteResponse)
        given(presenter.dataManager.getAllCartIngredients()).willReturn(getAllResponse)

        presenter.clearAllSelected()

        then(presenter.viewState).should().updateList()
    }

    @Test
    fun clearBoughtSelected_call_updateList(){
        val ingredient = Ingredient("","")
        val ingredients = listOf(ingredient)
        val deleteResponse = Completable.fromCallable {  }
        val getAllResponse = Single.fromCallable { ingredients }

        given(presenter.dataManager.deleteBoughtIngredients()).willReturn(deleteResponse)
        given(presenter.dataManager.getAllCartIngredients()).willReturn(getAllResponse)

        presenter.clearBoughtSelected()

        then(presenter.viewState).should().updateList()
    }
}