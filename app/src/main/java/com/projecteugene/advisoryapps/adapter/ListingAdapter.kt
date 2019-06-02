package com.projecteugene.advisoryapps.adapter

import android.app.Activity
import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.projecteugene.advisoryapps.R
import com.projecteugene.advisoryapps.databinding.ItemListingBinding
import com.projecteugene.advisoryapps.model.Item
import com.projecteugene.advisoryapps.viewmodel.ListingItemViewModel

/**
 * Created by Eugene Low
 */

class ListingAdapter: RecyclerView.Adapter<ListingAdapter.ViewHolder>() {
    private lateinit var list:List<Item>
    var listener: ListingItemViewModel.OnListingItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_listing, parent, false)
        return ViewHolder(listener, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return if(::list.isInitialized) list.size else 0
    }

    fun updateList(list: List<Item>?){
        if (list == null) return
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(private val listener: ListingItemViewModel.OnListingItemListener?,
                     private val binding: ItemListingBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = ListingItemViewModel()
        fun bind(item :Item){
            viewModel.listener = listener
            viewModel.bind(item)
            binding.viewModel = viewModel
        }
    }
}