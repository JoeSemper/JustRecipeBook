package com.joesemper.justrecipebook

import com.joesemper.justrecipebook.model.DataManager
import com.joesemper.justrecipebook.model.entity.Meal
import com.joesemper.justrecipebook.presenter.HomePresenter
import com.joesemper.justrecipebook.ui.fragments.home.`HomeFragmentView$$State`
import com.joesemper.justrecipebook.ui.util.constants.SearchType
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.then
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.terrakok.cicerone.Router

class HomePresenterTest {

    private lateinit var presenter: HomePresenter

    @Mock
    private lateinit var mockDataManager: DataManager

    @Mock
    private lateinit var mockRouter: Router

    @Mock
    private lateinit var meal: Meal

    private val mockMealListPresenter = Mockito.mock(HomePresenter.MealsListPresenter::class.java)

    private val viewContract = Mockito.mock(`HomeFragmentView$$State`::class.java)

    private var mockMainThreadScheduler = Schedulers.trampoline()


    @Before
    fun setUp() {
        val searchType = SearchType.QUERY
        val query = "query"

        MockitoAnnotations.initMocks(this)

        presenter = HomePresenter(searchType, query)
        with(presenter) {
            dataManager = mockDataManager
            router = mockRouter
            mainThreadScheduler = mockMainThreadScheduler
            mealsListPresenter = mockMealListPresenter
            setViewState(viewContract)
        }
    }

    @Test
    fun onSearch_should_call_UpdateList() {
        val query = "query"
        val response: Single<List<Meal>> = Single.fromCallable { listOf(meal) }

        given(presenter.dataManager.searchMealByName(query)).willReturn(response)

        presenter.onSearch(query)

        then(presenter.viewState).should().updateList()
    }

    @Test
    fun onSearch_updateMealsList_MethodOrder() {
        val query = "query"
        val meals = listOf(meal)
        val response: Single<List<Meal>> = Single.fromCallable { meals }

        given(presenter.dataManager.searchMealByName(query)).willReturn(response)

        presenter.onSearch(query)

        val inOrder = inOrder(presenter.mealsListPresenter, presenter.viewState)
        inOrder.verify(presenter.mealsListPresenter).clearList()
        inOrder.verify(presenter.mealsListPresenter).addMeals(meals)
        inOrder.verify(presenter.viewState).updateList()
    }

    @Test
    fun backPressed_ReturnTrue(){
        assertTrue(presenter.backPressed())
    }

}