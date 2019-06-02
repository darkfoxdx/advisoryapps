package com.projecteugene.advisoryapps.activity

import android.accounts.Account
import android.accounts.AccountManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.projecteugene.advisoryapps.R
import com.projecteugene.advisoryapps.databinding.ActivityMainBinding
import com.projecteugene.advisoryapps.model.LoginResult
import com.projecteugene.advisoryapps.utils.Const
import com.projecteugene.advisoryapps.viewmodel.ListingViewModel
import com.projecteugene.advisoryapps.viewmodel.LoginViewModel
import com.projecteugene.dialog.DialogBuilder
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.view.LayoutInflater
import android.widget.Toast
import com.projecteugene.advisoryapps.databinding.DialogUpdateBinding
import com.projecteugene.advisoryapps.model.Item
import com.projecteugene.advisoryapps.viewmodel.ListingItemViewModel
import com.projecteugene.advisoryapps.viewmodel.ListingUpdateViewModel


/**
 * Created by Eugene Low
 */
class MainActivity: AppCompatActivity(), ListingItemViewModel.OnListingItemListener {
    @Inject
    lateinit var accountManager: AccountManager

    lateinit var loginViewModel: LoginViewModel
    lateinit var listingViewModel: ListingViewModel
    lateinit var listingUpdateViewModel: ListingUpdateViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        listingViewModel = ViewModelProviders.of(this).get(ListingViewModel::class.java)
        listingUpdateViewModel = ViewModelProviders.of(this).get(ListingUpdateViewModel::class.java)
        listingViewModel.adapter.listener = this

        binding.loginViewModel = loginViewModel
        binding.listingViewModel = listingViewModel

        loginViewModel.account.value = accountManager.getAccountsByType(Const.ACCOUNT_TYPE).firstOrNull()

        loginViewModel.result.observe(this,
            Observer<LoginResult> {
                val account = Account(loginViewModel.email.value, Const.ACCOUNT_TYPE)
                val success = accountManager.addAccountExplicitly(account, null, null)
                if (success) {
                    accountManager.setUserData(account, "id", it.id)
                    accountManager.setUserData(account, "token", it.token)
                    loginViewModel.account.value = account
                }
            })

        loginViewModel.account.observe(this,
            Observer {
                if (it != null) {
                    val id = accountManager.getUserData(it, "id")
                    val token = accountManager.getUserData(it, "token")
                    listingViewModel.call(id, token)
                }
            })
    }

    fun onClickButton(view: View) {
        if (loginViewModel.account.value == null) {
                loginViewModel.call()
        } else {
            removeAccount(loginViewModel.account)
        }
    }

    @Suppress("DEPRECATION")
    fun removeAccount(account: MutableLiveData<Account>, function: (()->Unit)? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            accountManager.removeAccount(account.value, this, {
                account.value = null
                function?.invoke()
            }, null)
        } else {
            accountManager.removeAccount(account.value, {
                account.value = null
                function?.invoke()
            }, null)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onLongClickItem(item: Item?) {
        Toast.makeText(this, "Longpress", Toast.LENGTH_SHORT).show()
        showUpdateDialog(item)
    }

    protected fun showUpdateDialog(item: Item?) {
        val binding: DialogUpdateBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_update,null,false)

        val fragment =
            DialogBuilder(this, supportFragmentManager, "")
            .setTitle(R.string.update_field)
            .setTextNegativeButton(R.string.cancel)
            .setTextPositiveButton(R.string.update)
            .setHasBinding(true)
            .show(binding)

        fragment.binding?.viewModel = listingUpdateViewModel
        listingUpdateViewModel.updateName.postValue(item?.list_name)
        listingUpdateViewModel.updateDistance.postValue(item?.distance)

        fragment.onPositiveClick.subscribe {
            val id = accountManager.getUserData(loginViewModel.account.value, "id")
            val token = accountManager.getUserData(loginViewModel.account.value, "token")
            listingUpdateViewModel.call(id, token, item?.id)
        }.addTo(compositeDisposable)
    }
}
