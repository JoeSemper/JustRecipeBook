package com.joesemper.justrecipebook.di.modules

import com.joesemper.justrecipebook.util.logger.ILogger
import com.joesemper.justrecipebook.util.logger.Logger
import dagger.Module
import dagger.Provides

@Module
class LoggerModule {

    @Provides
    fun getLogger() : ILogger = Logger()
}