package com.projecteugene.advisoryapps.di.component

import android.app.Application
import com.projecteugene.advisoryapps.MainApplication
import com.projecteugene.advisoryapps.di.module.ActivitiesModule
import com.projecteugene.advisoryapps.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Eugene Low
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivitiesModule::class])
interface ApplicationComponent {

    fun inject(application: MainApplication)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder

    }
}