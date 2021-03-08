package com.example.mvvmkotlintest.ui.fragment.statistics

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.StaticsUseCase
import com.example.mvvmkotlintest.base.BaseViewModel

class StatisticsVM(app: Application): BaseViewModel(app) {

    val staticsUseCase: StaticsUseCase by lazy { StaticsUseCase() } // todo get with hilt


    fun getStatics() {
        staticsUseCase.getContent(viewModelScope)
    }




}