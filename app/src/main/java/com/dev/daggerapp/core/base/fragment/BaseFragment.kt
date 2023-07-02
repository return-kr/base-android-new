package com.dev.daggerapp.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.dev.daggerapp.BR
import com.dev.daggerapp.core.base.activity.BaseActivity
import com.dev.daggerapp.core.base.viewmodel.BaseViewModel

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel>(
    @LayoutRes private val layoutId: Int,
) : Fragment(), LifecycleOwner {
    private var mActivity: BaseActivity<T, V>? = null
    private lateinit var mViewDataBinding: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            mActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mViewDataBinding.apply {
            setVariable(BR.viewModel, getViewModel())
            lifecycleOwner = viewLifecycleOwner
        }
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.executePendingBindings()
        initObservers()
        onInitDataBinding(mViewDataBinding)
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    private fun initObservers() {
        getViewModel().errorLiveData.observe(
            viewLifecycleOwner
        ) { error ->
            (requireActivity() as BaseActivity<*, *>).showSnackBar(error.peekContent())
        }

        getViewModel().messageLiveData.observe(
            viewLifecycleOwner
        ) { message ->
            (requireActivity() as BaseActivity<*, *>).showSnackBar(message.peekContent())
        }

        getViewModel().loadingLiveData.observe(
            viewLifecycleOwner
        ) { visible ->
            (requireActivity() as BaseActivity<*, *>).showProgress(visible.peekContent())
        }
    }

    open fun getBaseActivity(): BaseActivity<T, V>? {
        return mActivity
    }

    open fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    fun showMessage(message: String) {
        getBaseActivity()?.showSnackBar(message)
    }

    fun onError(appError: String) {
        getViewModel().onError(appError)
    }

    fun showProgress(visible: Boolean) {
        getBaseActivity()?.showProgress(visible)
    }

    fun hideProgressBar() {
        getBaseActivity()?.hideProgress()
    }

    fun showToast(message: String?) {
        getBaseActivity()?.showToast(message)
    }

    abstract fun onInitDataBinding(viewBinding: T)

    abstract fun getViewModel(): V
}