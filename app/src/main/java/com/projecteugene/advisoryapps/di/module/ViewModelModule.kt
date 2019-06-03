package com.projecteugene.advisoryapps.di.module

import android.accounts.AccountManager
import android.app.Application
import com.projecteugene.advisoryapps.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

/**
 * Created by Eugene Low
 */
@Module
@Suppress("unused")
object ViewModelModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitClient(): Retrofit {
        return ApiService.getInstance()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideAccountManager(context: Application): AccountManager {
        return AccountManager.get(context)
    }
}