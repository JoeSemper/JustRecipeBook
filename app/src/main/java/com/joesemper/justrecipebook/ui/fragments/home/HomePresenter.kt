package com.joesemper.justrecipebook.ui.fragments.home

import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.adapters.meals.IMealsListPresenter
import com.joesemper.justrecipebook.ui.adapters.meals.MealItemView
import com.joesemper.justrecipebook.ui.navigation.Screens
import com.joesemper.justrecipebook.util.constants.SearchType
import com.joesemper.justrecipebook.util.logger.ILogger
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

class HomePresenter(val searchType: SearchType?, val query: String?) : MvpPresenter<HomeView>() {

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var logger: ILogger

    val mealsListPresenter = MealsListPresenter()

    class MealsListPresenter : IMealsListPresenter {
        val meals = mutableListOf<Meal>()
        override var itemClickListener: ((MealItemView) -> Unit)? = null

        override fun bindView(view: MealItemView) {
            val meal = meals[view.pos]

            meal.strMeal.let { view.setMealName(it) }
            meal.strMealThumb.let { view.loadImage(it) }
        }

        override fun getCount(): Int = meals.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        setOnClickListeners()
    }

    fun onSearch(query: String?) {
        searchMealByQuery(query)
    }

    private fun loadData() {
        when (searchType) {
            SearchType.QUERY -> searchMealByQuery(query)
            SearchType.CATEGORY -> searchMealByCategory()
            else -> searchMealByQuery(query)
        }
    }


    private fun searchMealByQuery(query: String?) {
        dataManager.searchMealByName(query)
            .observeOn(mainThreadScheduler)
            .subscribe({ meals ->
                updateMealsList(meals)
            }, {
                logger.log(it)
            })
    }

    private fun searchMealByCategory() {
        if (query != null)
            dataManager.getMealByCategory(query)
                .observeOn(mainThreadScheduler)
                .subscribe({ meals ->
                    updateMealsList(meals)
                }, {
                    logger.log(it)
                })
    }

    private fun setOnClickListeners() {
        mealsListPresenter.itemClickListener = { mealItemView ->
            val screen = getScreenByPosition(mealItemView.pos)
            router.navigateTo(screen)
        }
    }

    private fun getScreenByPosition(pos: Int): Screen {
        return Screens.MealScreen(mealsListPresenter.meals[pos])
    }

    private fun updateMealsList(meals: List<Meal>) {
        mealsListPresenter.meals.clear()
        mealsListPresenter.meals.addAll(meals)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}