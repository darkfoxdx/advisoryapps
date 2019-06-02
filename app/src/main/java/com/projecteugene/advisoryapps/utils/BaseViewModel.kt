package com.projecteugene.advisoryapps.utils

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.projecteugene.advisoryapps.di.component.DaggerViewModelInjector
import com.projecteugene.advisoryapps.di.component.ViewModelInjector
import com.projecteugene.advisoryapps.di.module.ViewModelModule
import com.projecteugene.advisoryapps.model.ApiResult
import com.projecteugene.advisoryapps.viewmodel.ListingUpdateViewModel
import com.projecteugene.advisoryapps.viewmodel.ListingViewModel
import com.projecteugene.advisoryapps.viewmodel.LoginViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Eugene Low
 */

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {
    lateinit var disposable: Disposable
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .apiModule(ViewModelModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is ListingViewModel -> injector.inject(this)
            is LoginViewModel -> injector.inject(this)
            is ListingUpdateViewModel -> injector.inject(this)
        }

    }

    fun handlerResult(result: ApiResult, success: ()->Unit) {
        if (result.status.code == 400) {
            Toast.makeText(getApplication(), result.status.message, Toast.LENGTH_LONG).show()
        } else {
            success()
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        compositeDisposable.clear()
    }
}