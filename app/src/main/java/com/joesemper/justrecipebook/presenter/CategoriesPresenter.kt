package com.joesemper.justrecipebook.presenter

import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.model.Area
import com.joesemper.justrecipebook.data.model.Areas
import com.joesemper.justrecipebook.data.model.Category
import com.joesemper.justrecipebook.presenter.list.IAreaListPresenter
import com.joesemper.justrecipebook.ui.fragments.categories.CategoriesView
import com.joesemper.justrecipebook.ui.fragments.categories.adapter.CategoryItemView
import com.joesemper.justrecipebook.presenter.list.ICategoryListPresenter
import com.joesemper.justrecipebook.ui.fragments.categories.adapter.AreaItemView
import com.joesemper.justrecipebook.ui.navigation.Screens
import com.joesemper.justrecipebook.ui.utilite.constants.SearchType
import com.joesemper.justrecipebook.util.logger.ILogger
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

class CategoriesPresenter : MvpPresenter<CategoriesView>() {

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var logger: ILogger

    val categoriesListPresenter = CategoriesListPresenter()
    val areasListPresenter = AreasListPresenter()

    class CategoriesListPresenter : ICategoryListPresenter {
        val categories = mutableListOf<Category>()

        override var itemClickListener: ((CategoryItemView) -> Unit)? = null

        override fun bindView(view: CategoryItemView) {
            val category = categories[view.pos]

            category.strCategory.let { view.setCategory(it) }
            category.strCategoryThumb.let { view.loadImage(it) }
        }

        override fun getCount(): Int = categories.size
    }

    class AreasListPresenter : IAreaListPresenter {
        val areas = mutableListOf<Area>()

        override var itemClickListener: ((AreaItemView) -> Unit)? = null

        override fun bindView(view: AreaItemView) {
            val area = areas[view.pos]

            area.strArea.let { view.setArea(it) }
        }

        override fun getCount(): Int = areas.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        setOnClickListeners()
    }

    fun onCategoriesCreated() {
        viewState.initCategories()
        loadCategories()
    }

    fun onAreasCreated() {
        viewState.initAries()
        loadAreas()
    }

    private fun loadCategories() {
        dataManager.getAllCategories()
            .observeOn(mainThreadScheduler)
            .subscribe({ categories ->
                updateCategoriesList(categories)
            }, {
                logger.log(it)
            })
    }

    private fun loadAreas() {
        dataManager.getAllAreas()
            .observeOn(mainThreadScheduler)
            .subscribe({ areas ->
                updateAreasList(areas)
            }, {
                logger.log(it)
            })
    }

    private fun setOnClickListeners() {
        categoriesListPresenter.itemClickListener = { categoryItemView ->
            val screen = getScreenByPosition(categoryItemView.pos)
            router.navigateTo(screen)
        }

        areasListPresenter.itemClickListener = { areaItemView ->
            val screen = getScreenByPosition(areaItemView.pos)
            router.navigateTo(screen)
        }
    }

    private fun updateCategoriesList(categories: List<Category>) {
        categoriesListPresenter.categories.clear()
        categoriesListPresenter.categories.addAll(categories)
        viewState.updateCategoriesList()
    }

    private fun updateAreasList(areas: List<Area>) {
        areasListPresenter.areas.clear()
        areasListPresenter.areas.addAll(areas)
        viewState.updateAriesList()
    }


    private fun getScreenByPosition(pos: Int): Screen {
        val query = categoriesListPresenter.categories[pos].strCategory
        val category = SearchType.CATEGORY
        return Screens.HomeScreen(category, query)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}

