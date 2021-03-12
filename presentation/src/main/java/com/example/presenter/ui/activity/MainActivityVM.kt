package com.example.presenter.ui.activity

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.example.presenter.base.BaseViewModel

class MainActivityVM @ViewModelInject constructor(val app: Application): BaseViewModel(app)