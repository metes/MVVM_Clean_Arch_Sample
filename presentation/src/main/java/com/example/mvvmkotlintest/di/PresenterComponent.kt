package com.example.mvvmkotlintest.di

import android.content.Context
import com.example.domain.di.DomainModuleDependencies
import dagger.BindsInstance
import dagger.Component


@Component(dependencies = [DomainModuleDependencies::class])
interface PresenterComponent {

//    fun inject(viewModel: StatisticsVM)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(loginModuleDependencies: DomainModuleDependencies): Builder
        fun build(): PresenterComponent
    }
}