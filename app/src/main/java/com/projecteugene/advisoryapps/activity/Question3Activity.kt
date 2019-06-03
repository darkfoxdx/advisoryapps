package com.projecteugene.advisoryapps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.projecteugene.advisoryapps.R
import com.projecteugene.advisoryapps.databinding.ActivityQuestion3Binding
import com.projecteugene.advisoryapps.viewmodel.Question3ViewModel
import kotlinx.android.synthetic.main.activity_question3.*

/**
 * Created by Eugene Low
 */
class Question3Activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityQuestion3Binding = DataBindingUtil.setContentView(this, R.layout.activity_question3)
        setSupportActionBar(toolbar)
        val viewModel = ViewModelProviders.of(this).get(Question3ViewModel::class.java)
        binding.viewModel = viewModel
    }
}