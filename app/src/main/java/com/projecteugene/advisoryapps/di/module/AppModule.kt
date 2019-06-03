package com.projecteugene.advisoryapps.di.module

import dagger.Module
import dagger.Provides
import android.app.Application
import android.content.Context
import javax.inject.Singleton

/**
 * Created by Eugene Low
 */
@Module
@Suppress("unused")
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }


}