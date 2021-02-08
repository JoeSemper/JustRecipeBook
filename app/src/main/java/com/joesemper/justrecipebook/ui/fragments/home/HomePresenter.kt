package com.joesemper.justrecipebook.ui.fragments.home

import android.util.Log
import com.joesemper.justrecipebook.BuildConfig.DEBUG
import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.adapters.meals.IMealsListPresenter
import com.joesemper.justrecipebook.ui.adapters.meals.MealItemView
import com.joesemper.justrecipebook.ui.navigation.Screens
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

class HomePresenter(val query: String?): MvpPresenter<HomeView>() {

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var router: Router

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
        if(query == "") {
            searchMealByQuery(query)
        }

        if (query != null) {
           searchMealByCategory(query)
        }
    }


    private fun searchMealByQuery(query: String?) {
        dataManager.searchMealByName(query)
            .observeOn(mainThreadScheduler)
            .subscribe({meals ->
                updateMealsList(meals)
            }, {
                log(it)
            })
    }

    private fun searchMealByCategory(query: String) {
        dataManager.getMealByCategory(query)
            .observeOn(mainThreadScheduler)
            .subscribe({meals ->
                updateMealsList(meals)
            }, {
                log(it)
            })
    }

    private fun setOnClickListeners(){
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

    private fun log(throwable: Throwable) {
        if(DEBUG) {
            Log.v("Meals", throwable.message.toString())
        }
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}