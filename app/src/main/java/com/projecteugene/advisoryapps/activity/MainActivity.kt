package com.projecteugene.advisoryapps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.projecteugene.advisoryapps.R
import com.projecteugene.advisoryapps.databinding.ActivityMainBinding
import com.projecteugene.advisoryapps.viewmodel.ListingViewModel
import com.projecteugene.advisoryapps.viewmodel.LoginViewModel
import com.projecteugene.dialog.DialogBuilder
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
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
    lateinit var loginViewModel: LoginViewModel
    lateinit var listingViewModel: ListingViewModel
    lateinit var listingUpdateViewModel: ListingUpdateViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
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

        loginViewModel.account.observe(this,
            Observer {
                if (it != null) {
                    listingViewModel.call(loginViewModel.accountId, loginViewModel.accountToken)
                }
            })

        listingUpdateViewModel.result.observe(this,
            Observer {
                listingViewModel.call(loginViewModel.accountId, loginViewModel.accountToken)
            })
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
            listingUpdateViewModel.call(loginViewModel.accountId, loginViewModel.accountToken, item?.id)
        }.addTo(compositeDisposable)
    }
}
