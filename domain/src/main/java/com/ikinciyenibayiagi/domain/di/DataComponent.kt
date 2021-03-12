package com.ikinciyenibayiagi.domain.di

import android.content.Context
import com.ikinciyenibayiagi.data.di.DataDependencies
import dagger.BindsInstance
import dagger.Component


@Component(dependencies = [DataDependencies::class])
interface DataComponent {

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(dataDependencies: DataDependencies): Builder
        fun build(): DataComponent
    }
}