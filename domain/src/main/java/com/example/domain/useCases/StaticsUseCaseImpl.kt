package com.example.domain.useCases

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.domain.entities.statics.StaticsResponseItem
import com.example.domain.repository.StaticsRepository
import com.example.domain.repository.post
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


@InstallIn(ApplicationComponent::class)
@Module
abstract class StaticsUseCaseModule {

    @Binds
    abstract fun bindStaticsUseCase(impl: StaticsUseCaseImpl): StaticsUseCase
}


interface StaticsUseCase {
    fun getContent(
        viewModelScope: CoroutineScope,
        content: LiveData<ArrayList<StaticsResponseItem>>
    )
}

class StaticsUseCaseImpl @Inject constructor(): StaticsUseCase {

    @Inject lateinit var repository: StaticsRepository

    override fun getContent(viewModelScope: CoroutineScope, content: LiveData<ArrayList<StaticsResponseItem>>) {
        Log.d("StaticsUseCase", "StaticsUseCase çalıştı *************")
        repository.getContentData(viewModelScope) { statics ->
            content.post(statics)
        }
    }

}


