package com.joesemper.justrecipebook.ui.fragments.home

import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.adapters.meals.IMealsListPresenter
import com.joesemper.justrecipebook.ui.adapters.meals.MealItemView
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HomePresenter: MvpPresenter<HomeView>() {

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
    }

    private fun loadData() {
        dataManager.getMealByCategory("Seafood")
            .observeOn(mainThreadScheduler)
            .subscribe({meals ->
                mealsListPresenter.meals.clear()
                mealsListPresenter.meals.addAll(meals)
                viewState.updateList()
            }, {
                viewState.showResult(it.message.toString())
            })
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}