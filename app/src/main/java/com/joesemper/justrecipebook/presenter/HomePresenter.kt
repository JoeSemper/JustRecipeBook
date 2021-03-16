package com.joesemper.justrecipebook.presenter

import com.joesemper.justrecipebook.model.DataManager
import com.joesemper.justrecipebook.ui.fragments.home.HomeView
import com.joesemper.justrecipebook.util.logger.ILogger
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HomePresenter : MvpPresenter<HomeView>() {

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var logger: ILogger

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }


    fun onSearch(text: String?) {

    }





}