package com.dev.daggerapp.view.main

import android.app.Application
import com.dev.daggerapp.core.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
): BaseViewModel(application) {
}