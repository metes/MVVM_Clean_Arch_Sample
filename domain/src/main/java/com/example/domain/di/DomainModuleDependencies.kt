package com.example.domain.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier


@EntryPoint
@InstallIn(ApplicationComponent::class)
interface DomainModuleDependencies {

//    @AuthInterceptorOkHttpClient
//    fun okHttpClient(): OkHttpClient

//    @Binds
//    abstract fun bindStaticsUseCase(impl: StaticsUseCase): @JvmSuppressWildcards IStaticsUseCase

//    @StaticsRepositoryAnotation
//    fun staticsRepository(): IStaticsUseCase
}
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StaticsRepositoryAnotation