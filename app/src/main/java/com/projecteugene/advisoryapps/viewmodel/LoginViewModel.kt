package com.projecteugene.advisoryapps.viewmodel

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.projecteugene.advisoryapps.api.ApiService
import com.projecteugene.advisoryapps.model.LoginResult
import com.projecteugene.advisoryapps.utils.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Eugene Low
 */
class LoginViewModel(application: Application): BaseViewModel(application) {
    @Inject
    lateinit var apiService: ApiService

    val isLoading:MutableLiveData<Boolean> = MutableLiveData()
    val result: MutableLiveData<LoginResult> = MutableLiveData()

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val account: MutableLiveData<Account> = MutableLiveData()

    fun call(){
        disposable  = apiService.login(email.value, password.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }

    private fun onStart() {
        isLoading.value = true
    }

    private fun onSuccess(value: LoginResult) {
        isLoading.value = false
        handlerResult(value) {
            result.value = value
        }
    }

    private fun onError(throwable: Throwable) {
        isLoading.value = false
    }
}