package com.ikinciyenibayiagi.domain.di

import com.ikinciyenibayiagi.domain.repository.StaticsRepository
import com.ikinciyenibayiagi.domain.repository.StaticsRepositoryImpl
import com.ikinciyenibayiagi.domain.useCases.StaticsUseCase
import com.ikinciyenibayiagi.domain.useCases.StaticsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@InstallIn(ApplicationComponent::class)
@Module
abstract class DomainModule {

    @Binds
    abstract fun bindStaticsUseCase(impl: StaticsUseCaseImpl): StaticsUseCase

    @Binds
    abstract fun bindStaticsRepository(impl: StaticsRepositoryImpl): StaticsRepository





}
