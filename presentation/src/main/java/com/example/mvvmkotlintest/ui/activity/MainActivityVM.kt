package com.example.mvvmkotlintest.ui.activity

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.example.mvvmkotlintest.base.BaseViewModel

class MainActivityVM @ViewModelInject constructor(val app: Application): BaseViewModel(app)