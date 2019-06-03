package com.projecteugene.advisoryapps

import android.app.Activity
import android.app.Application
import com.projecteugene.advisoryapps.di.component.DaggerApplicationInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Eugene Low
 */
class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        DaggerApplicationInjector.builder()
            .applicationBind(this)
            .build()
            .inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

}