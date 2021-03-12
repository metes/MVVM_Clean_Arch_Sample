package com.example.presenter.ui.fragment.statistics

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.statics.StaticsResponseItem
import com.example.domain.useCases.StaticsUseCase
import com.example.presenter.base.BaseViewModel


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