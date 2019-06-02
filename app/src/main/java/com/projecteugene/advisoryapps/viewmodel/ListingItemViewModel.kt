package com.projecteugene.advisoryapps.viewmodel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projecteugene.advisoryapps.model.Item

/**
 * Created by Eugene Low
 */
class ListingItemViewModel: ViewModel() {
    val name:MutableLiveData<String> = MutableLiveData()
    val distance: MutableLiveData<String> = MutableLiveData()
    val itemValue: MutableLiveData<Item> = MutableLiveData()
    var listener: OnListingItemListener? = null

    fun bind(item: Item){
        itemValue.value = item
        name.value = "${item.id}. ${item.list_name}"
        distance.value = "Distance: ${item.distance}"
    }

    fun onClickItem(view: View, viewModel: ListingItemViewModel) {
        Toast.makeText(view.context, "${viewModel.name.value} - ${viewModel.distance.value}", Toast.LENGTH_SHORT).show()
    }

    fun onLongClickItem(view: View, viewModel: ListingItemViewModel): Boolean {
        listener?.onLongClickItem(viewModel.itemValue.value)
        return true
    }
    interface OnListingItemListener {
        fun onLongClickItem(item: Item?)
    }
}