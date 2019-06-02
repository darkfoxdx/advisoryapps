package com.projecteugene.advisoryapps.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.projecteugene.advisoryapps.adapter.ListingAdapter
import com.projecteugene.advisoryapps.api.ApiService
import com.projecteugene.advisoryapps.model.ListingResult
import com.projecteugene.advisoryapps.model.ListingUpdateResult
import com.projecteugene.advisoryapps.utils.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Eugene Low
 */
class ListingUpdateViewModel(application: Application): BaseViewModel(application) {
    @Inject
    lateinit var apiService: ApiService

    val adapter = ListingAdapter()
    val isLoading:MutableLiveData<Boolean> = MutableLiveData()
    val result: MutableLiveData<ListingUpdateResult> = MutableLiveData()

    val updateName: MutableLiveData<String> = MutableLiveData()
    val updateDistance: MutableLiveData<String> = MutableLiveData()

    fun call(id: String?, token: String?, listingId: String?){
        disposable  = apiService.listingUpdate(id, token, listingId, updateName.value, updateDistance.value)
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

    private fun onSuccess(value: ListingUpdateResult) {
        isLoading.value = false
        handlerResult(value) {
            result.value = value
        }
    }

    private fun onError(throwable: Throwable) {
        isLoading.value = false
    }
}