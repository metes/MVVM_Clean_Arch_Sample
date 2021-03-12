package com.ikinciyenibayiagi

import com.ikinciyenibayiagi.data.APIClient
import com.ikinciyenibayiagi.data.APIClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@InstallIn(ApplicationComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindStaticsUseCase(impl: APIClientImpl): APIClient




}
