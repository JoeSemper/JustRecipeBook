package com.joesemper.justrecipebook

import android.app.Application
import com.joesemper.justrecipebook.di.AppComponent
import com.joesemper.justrecipebook.di.DaggerAppComponent
import com.joesemper.justrecipebook.di.modules.AppModule

class App : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}