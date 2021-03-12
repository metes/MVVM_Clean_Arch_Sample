package com.example.domain.di

import com.example.domain.repository.StaticsRepository
import com.example.domain.repository.StaticsRepositoryImpl
import com.example.domain.useCases.StaticsUseCase
import com.example.domain.useCases.StaticsUseCaseImpl
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
