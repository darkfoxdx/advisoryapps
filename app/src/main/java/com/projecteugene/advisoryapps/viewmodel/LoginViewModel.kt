package com.projecteugene.advisoryapps.viewmodel

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Application
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.projecteugene.advisoryapps.api.ApiService
import com.projecteugene.advisoryapps.model.LoginResult
import com.projecteugene.advisoryapps.utils.BaseViewModel
import com.projecteugene.advisoryapps.utils.Const
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Eugene Low
 */
class LoginViewModel(application: Application): BaseViewModel(application) {
    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var accountManager: AccountManager

    val isLoading:MutableLiveData<Boolean> = MutableLiveData()
    val result: MutableLiveData<LoginResult> = MutableLiveData()

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val account: MutableLiveData<Account> = MutableLiveData()

    val accountId: String
        get() = accountManager.getUserData(account.value, "id")
    val accountToken: String
        get() = accountManager.getUserData(account.value, "token")

    init {
        account.value = accountManager.getAccountsByType(Const.ACCOUNT_TYPE).firstOrNull()
    }

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
            val newAccount = Account(email.value, Const.ACCOUNT_TYPE)
            val success = accountManager.addAccountExplicitly(newAccount, null, null)
            if (success) {
                accountManager.setUserData(newAccount, "id", value.id)
                accountManager.setUserData(newAccount, "token", value.token)
                account.value = newAccount
            }
        }
    }

    private fun onError(throwable: Throwable) {
        isLoading.value = false
    }

    fun onClickButton() {
        if (account.value == null) {
            call()
        } else {
            removeAccount(account)
        }
    }

    @Suppress("DEPRECATION")
    fun removeAccount(account: MutableLiveData<Account>, function: (()->Unit)? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val success = accountManager.removeAccountExplicitly(account.value)
            if (success) account.value = null
        } else {
            accountManager.removeAccount(account.value, {
                account.value = null
                function?.invoke()
            }, null)
        }
    }
}