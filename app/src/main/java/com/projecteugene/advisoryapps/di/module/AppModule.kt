package com.projecteugene.advisoryapps.di.module

import android.accounts.AccountManager
import com.projecteugene.advisoryapps.activity.MainActivity
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