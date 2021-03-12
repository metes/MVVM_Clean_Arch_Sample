package com.example.mvvmkotlintest.ui.fragment.statistics

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.statics.StaticsResponseItem
import com.example.domain.useCases.StaticsUseCase
import com.example.mvvmkotlintest.base.BaseViewModel


class StatisticsVM @ViewModelInject constructor(
    app: Application,
    var staticsUseCase: StaticsUseCase
) : BaseViewModel(app) {

    val staticsListLD: LiveData<ArrayList<StaticsResponseItem>> = MutableLiveData()

    fun getStatics() {
        staticsUseCase.getContent(viewModelScope, staticsListLD)
    }




}