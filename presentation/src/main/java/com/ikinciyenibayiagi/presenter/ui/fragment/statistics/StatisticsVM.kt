package com.ikinciyenibayiagi.presenter.ui.fragment.statistics

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ikinciyenibayiagi.domain.entities.statics.StaticsResponseItem
import com.ikinciyenibayiagi.domain.useCases.StaticsUseCase
import com.ikinciyenibayiagi.presenter.base.BaseViewModel


class StatisticsVM @ViewModelInject constructor(
    app: Application,
    var staticsUseCase: StaticsUseCase
) : BaseViewModel(app) {

    val staticsListLD: LiveData<List<StaticsResponseItem>> = MutableLiveData()

    init {
//        getStatics()
    }

    fun getStatics() {
        staticsUseCase.getContent(viewModelScope, staticsListLD, errorMessageSLE)
    }




}