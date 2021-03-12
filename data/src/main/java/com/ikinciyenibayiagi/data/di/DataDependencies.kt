package com.ikinciyenibayiagi.data.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@EntryPoint
@InstallIn(ApplicationComponent::class)
interface DataDependencies {


//    @APIClientAnotation
//    fun apiClient(): APIClient

}
