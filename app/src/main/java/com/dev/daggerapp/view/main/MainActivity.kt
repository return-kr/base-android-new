package com.dev.daggerapp.view.main

import androidx.activity.viewModels
import com.dev.daggerapp.R
import com.dev.daggerapp.core.base.activity.BaseActivity
import com.dev.daggerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    private val mViewModel: MainViewModel by viewModels()
    override fun getViewModel(): MainViewModel {
        return mViewModel
    }

    override fun onInitBinding(viewBinding: ActivityMainBinding) {
        with(viewBinding) {
            mainText.text = "Welcome Bhaskar"
        }

        showSnackBar("Welcome to Base")
    }
}