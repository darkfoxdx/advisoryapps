package com.projecteugene.advisoryapps.di.component

import android.app.Application
import com.projecteugene.advisoryapps.viewmodel.ListingViewModel
import com.projecteugene.advisoryapps.di.module.ViewModelModule
import com.projecteugene.advisoryapps.viewmodel.ListingUpdateViewModel
import com.projecteugene.advisoryapps.viewmodel.LoginViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Eugene Low
 */
@Singleton
@Component(modules = [ViewModelModule::class])
interface ViewModelInjector {
    fun inject(viewModel: ListingViewModel)
    fun inject(viewModel: LoginViewModel)
    fun inject(viewModel: ListingUpdateViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun apiModule(apiModule: ViewModelModule): Builder

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }
}