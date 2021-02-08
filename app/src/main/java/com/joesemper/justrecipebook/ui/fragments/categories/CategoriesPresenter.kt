package com.joesemper.justrecipebook.ui.fragments.categories

import com.joesemper.justrecipebook.data.DataManager
import com.joesemper.justrecipebook.data.network.model.Category
import com.joesemper.justrecipebook.ui.adapters.categories.CategoryItemView
import com.joesemper.justrecipebook.ui.adapters.categories.ICategoryListPresenter
import com.joesemper.justrecipebook.ui.navigation.Screens
import com.joesemper.justrecipebook.util.constants.SearchType
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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        setOnClickListeners()
    }

    private fun loadData() {
        dataManager.getAllCategories()
            .observeOn(mainThreadScheduler)
            .subscribe({ categories ->
                updateCategoriesList(categories)
            }, {
                logger.log(it)
            })
    }

    private fun setOnClickListeners() {
        categoriesListPresenter.itemClickListener = { categoryItemView ->
            val screen = getScreenByPosition(categoryItemView.pos)
            router.navigateTo(screen)
        }
    }

    private fun updateCategoriesList(categories: List<Category>) {
        categoriesListPresenter.categories.clear()
        categoriesListPresenter.categories.addAll(categories)
        viewState.updateLis()
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

