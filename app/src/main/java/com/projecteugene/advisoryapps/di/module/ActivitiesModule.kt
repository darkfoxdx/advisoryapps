package com.projecteugene.advisoryapps.di.module

import com.projecteugene.advisoryapps.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Eugene Low
 */
@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}