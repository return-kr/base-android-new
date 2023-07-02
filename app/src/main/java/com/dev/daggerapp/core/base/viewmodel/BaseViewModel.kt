package com.dev.daggerapp.core.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dev.daggerapp.core.util.livedata.LiveDataEvent

open class BaseViewModel(application: Application): AndroidViewModel(application) {
    var errorLiveData: MutableLiveData<LiveDataEvent<String>> = MutableLiveData()
    var messageLiveData: MutableLiveData<LiveDataEvent<String>> = MutableLiveData()
    var loadingLiveData: MutableLiveData<LiveDataEvent<Boolean>> = MutableLiveData()

    fun onError(error: String) {
        errorLiveData.postValue(LiveDataEvent(error))
    }

    fun showMessage(message: String) {
        messageLiveData.postValue(LiveDataEvent(message))
    }

    fun showLoading(visible: Boolean) {
        loadingLiveData.postValue(LiveDataEvent(visible))
    }
}