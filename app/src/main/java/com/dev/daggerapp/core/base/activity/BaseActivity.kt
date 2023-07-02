package com.dev.daggerapp.core.base.activity

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dev.daggerapp.BR
import com.dev.daggerapp.core.base.viewmodel.BaseViewModel
import com.dev.daggerapp.core.util.function.DialogUtils
import com.dev.daggerapp.core.util.function.KeyboardUtils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel>(
     private val layoutId: Int,
): AppCompatActivity(){

    private lateinit var viewDataBinding: T
    private var progressDialog: Dialog? = null

    abstract fun getViewModel(): V

    abstract fun onInitBinding(viewBinding: T)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = Dialog(this)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding.apply {
            setVariable(BR.viewModel, getViewModel())
            lifecycleOwner = this@BaseActivity
        }
        initObservers()
        onInitBinding(viewDataBinding)
    }

    private fun initObservers() {
        getViewModel().errorLiveData.observe(
            this
        ) { error ->
            showSnackBar(error.peekContent())
        }

        getViewModel().messageLiveData.observe(
            this
        ) { message ->
            showSnackBar(message.peekContent())
        }

        getViewModel().loadingLiveData.observe(
            this
        ) { visible ->
            showProgress(visible.peekContent())
        }
    }

    fun getBinding(): T {
        return viewDataBinding
    }

    fun showSnackBar(message: String) {
        progressDialog?.let { DialogUtils.dismissDialog(it) }
        hideKeyboard(this)
        Snackbar.make(
            getBinding().root,
            message,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    fun showProgress(visible: Boolean) {
        if (visible) {
            hideKeyboard(this)
            hideProgress()
            progressDialog?.let { DialogUtils.showCommonProgressDialog(this, it) }
        } else {
            hideProgress()
        }
    }

    fun hideProgress() {
        progressDialog?.let { DialogUtils.dismissDialog(it) }
    }

    fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}