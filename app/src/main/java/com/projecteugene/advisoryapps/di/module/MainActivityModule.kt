package com.projecteugene.advisoryapps.di.module

import android.accounts.AccountManager
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Eugene Low
 */
@Module
object MainActivityModule {
    @Provides
    @JvmStatic
    internal fun provideAccountManager(context: Context): AccountManager {
        return AccountManager.get(context)
    }
}