package com.projecteugene.advisoryapps.utils

import android.accounts.Account
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputLayout
import com.projecteugene.advisoryapps.R



/**
 * Created by Eugene Low
 */

@BindingAdapter("mutable_refreshing")
fun setMutableRefreshing(view: SwipeRefreshLayout, refreshing: MutableLiveData<Boolean>?) {
    val parentActivity: AppCompatActivity? = view.context as? AppCompatActivity
    if(parentActivity != null && refreshing != null) {
        refreshing.observe(parentActivity, Observer { value ->
            view.isRefreshing = value
            view.isEnabled = value
        } )
    }
}

@BindingAdapter("mutable_text")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.context as? AppCompatActivity
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value})
    }
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutable_account")
fun setAccount(view: RecyclerView, account: MutableLiveData<Account>?) {
    val parentActivity:AppCompatActivity? = view.context as? AppCompatActivity
    if(parentActivity != null && account != null) {
        account.observe(parentActivity, Observer { value ->
            view.visibility = if (value == null) View.GONE else View.VISIBLE
        })
    }
}

@BindingAdapter("mutable_account")
fun setAccount(view: LinearLayout, account: MutableLiveData<Account>?) {
    val parentActivity:AppCompatActivity? = view.context as? AppCompatActivity
    if(parentActivity != null && account != null) {
        account.observe(parentActivity, Observer { value ->
            view.visibility = if (value == null) View.VISIBLE else View.GONE
        })
    }
}

@BindingAdapter("mutable_account")
fun setAccount(view: Button, account: MutableLiveData<Account>?) {
    val parentActivity:AppCompatActivity? = view.context as? AppCompatActivity
    if(parentActivity != null && account != null) {
        account.observe(parentActivity, Observer { value ->
            if (value == null) {
                view.text = view.context.getText(R.string.login)
            } else {
                view.text = view.context.getText(R.string.logout)
            }
        })
    }
}

@BindingAdapter("question3_inputError")
fun setQuestion3InputError(view: TextInputLayout, text: MutableLiveData<String>) {
    text.observeForever { value ->
        if (Question3.function(value.toIntOrNull()) == null) {
            view.error = view.context.getString(R.string.error_input)
        } else {
            view.isErrorEnabled = false
        }
    }
}


@BindingAdapter("question3_output")
fun setQuestion3Output(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.context as? AppCompatActivity
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value ->
            view.text = Question3.function(value.toIntOrNull())
        })
    }
}